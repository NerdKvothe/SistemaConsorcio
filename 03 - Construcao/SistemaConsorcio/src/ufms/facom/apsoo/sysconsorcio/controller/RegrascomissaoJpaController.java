/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Administradora;
import ufms.facom.apsoo.sysconsorcio.model.Comissao;
import ufms.facom.apsoo.sysconsorcio.model.Regrascomissao;

/**
 *
 * @author joshua
 */
public class RegrascomissaoJpaController implements Serializable {

    public RegrascomissaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regrascomissao regrascomissao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administradora codigoAdm = regrascomissao.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm = em.getReference(codigoAdm.getClass(), codigoAdm.getCodigoAdm());
                regrascomissao.setCodigoAdm(codigoAdm);
            }
            Comissao codigoComissao = regrascomissao.getCodigoComissao();
            if (codigoComissao != null) {
                codigoComissao = em.getReference(codigoComissao.getClass(), codigoComissao.getCodigoComissao());
                regrascomissao.setCodigoComissao(codigoComissao);
            }
            em.persist(regrascomissao);
            if (codigoAdm != null) {
                codigoAdm.getRegrascomissaoList().add(regrascomissao);
                codigoAdm = em.merge(codigoAdm);
            }
            if (codigoComissao != null) {
                codigoComissao.getRegrascomissaoList().add(regrascomissao);
                codigoComissao = em.merge(codigoComissao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regrascomissao regrascomissao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regrascomissao persistentRegrascomissao = em.find(Regrascomissao.class, regrascomissao.getCodigoRegra());
            Administradora codigoAdmOld = persistentRegrascomissao.getCodigoAdm();
            Administradora codigoAdmNew = regrascomissao.getCodigoAdm();
            Comissao codigoComissaoOld = persistentRegrascomissao.getCodigoComissao();
            Comissao codigoComissaoNew = regrascomissao.getCodigoComissao();
            if (codigoAdmNew != null) {
                codigoAdmNew = em.getReference(codigoAdmNew.getClass(), codigoAdmNew.getCodigoAdm());
                regrascomissao.setCodigoAdm(codigoAdmNew);
            }
            if (codigoComissaoNew != null) {
                codigoComissaoNew = em.getReference(codigoComissaoNew.getClass(), codigoComissaoNew.getCodigoComissao());
                regrascomissao.setCodigoComissao(codigoComissaoNew);
            }
            regrascomissao = em.merge(regrascomissao);
            if (codigoAdmOld != null && !codigoAdmOld.equals(codigoAdmNew)) {
                codigoAdmOld.getRegrascomissaoList().remove(regrascomissao);
                codigoAdmOld = em.merge(codigoAdmOld);
            }
            if (codigoAdmNew != null && !codigoAdmNew.equals(codigoAdmOld)) {
                codigoAdmNew.getRegrascomissaoList().add(regrascomissao);
                codigoAdmNew = em.merge(codigoAdmNew);
            }
            if (codigoComissaoOld != null && !codigoComissaoOld.equals(codigoComissaoNew)) {
                codigoComissaoOld.getRegrascomissaoList().remove(regrascomissao);
                codigoComissaoOld = em.merge(codigoComissaoOld);
            }
            if (codigoComissaoNew != null && !codigoComissaoNew.equals(codigoComissaoOld)) {
                codigoComissaoNew.getRegrascomissaoList().add(regrascomissao);
                codigoComissaoNew = em.merge(codigoComissaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regrascomissao.getCodigoRegra();
                if (findRegrascomissao(id) == null) {
                    throw new NonexistentEntityException("The regrascomissao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regrascomissao regrascomissao;
            try {
                regrascomissao = em.getReference(Regrascomissao.class, id);
                regrascomissao.getCodigoRegra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regrascomissao with id " + id + " no longer exists.", enfe);
            }
            Administradora codigoAdm = regrascomissao.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm.getRegrascomissaoList().remove(regrascomissao);
                codigoAdm = em.merge(codigoAdm);
            }
            Comissao codigoComissao = regrascomissao.getCodigoComissao();
            if (codigoComissao != null) {
                codigoComissao.getRegrascomissaoList().remove(regrascomissao);
                codigoComissao = em.merge(codigoComissao);
            }
            em.remove(regrascomissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regrascomissao> findRegrascomissaoEntities() {
        return findRegrascomissaoEntities(true, -1, -1);
    }

    public List<Regrascomissao> findRegrascomissaoEntities(int maxResults, int firstResult) {
        return findRegrascomissaoEntities(false, maxResults, firstResult);
    }

    private List<Regrascomissao> findRegrascomissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regrascomissao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Regrascomissao findRegrascomissao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regrascomissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegrascomissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regrascomissao> rt = cq.from(Regrascomissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
