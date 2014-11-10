/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ufms.facom.apsoo.sysconsorcio.model.Venda;
import ufms.facom.apsoo.sysconsorcio.model.Regrascomissao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Comissao;

/**
 *
 * @author joshua
 */
public class ComissaoJpaController implements Serializable {

    public ComissaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comissao comissao) {
        if (comissao.getRegrascomissaoList() == null) {
            comissao.setRegrascomissaoList(new ArrayList<Regrascomissao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda codigoVenda = comissao.getCodigoVenda();
            if (codigoVenda != null) {
                codigoVenda = em.getReference(codigoVenda.getClass(), codigoVenda.getCodigoVenda());
                comissao.setCodigoVenda(codigoVenda);
            }
            List<Regrascomissao> attachedRegrascomissaoList = new ArrayList<Regrascomissao>();
            for (Regrascomissao regrascomissaoListRegrascomissaoToAttach : comissao.getRegrascomissaoList()) {
                regrascomissaoListRegrascomissaoToAttach = em.getReference(regrascomissaoListRegrascomissaoToAttach.getClass(), regrascomissaoListRegrascomissaoToAttach.getCodigoRegra());
                attachedRegrascomissaoList.add(regrascomissaoListRegrascomissaoToAttach);
            }
            comissao.setRegrascomissaoList(attachedRegrascomissaoList);
            em.persist(comissao);
            if (codigoVenda != null) {
                codigoVenda.getComissaoList().add(comissao);
                codigoVenda = em.merge(codigoVenda);
            }
            for (Regrascomissao regrascomissaoListRegrascomissao : comissao.getRegrascomissaoList()) {
                Comissao oldCodigoComissaoOfRegrascomissaoListRegrascomissao = regrascomissaoListRegrascomissao.getCodigoComissao();
                regrascomissaoListRegrascomissao.setCodigoComissao(comissao);
                regrascomissaoListRegrascomissao = em.merge(regrascomissaoListRegrascomissao);
                if (oldCodigoComissaoOfRegrascomissaoListRegrascomissao != null) {
                    oldCodigoComissaoOfRegrascomissaoListRegrascomissao.getRegrascomissaoList().remove(regrascomissaoListRegrascomissao);
                    oldCodigoComissaoOfRegrascomissaoListRegrascomissao = em.merge(oldCodigoComissaoOfRegrascomissaoListRegrascomissao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comissao comissao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comissao persistentComissao = em.find(Comissao.class, comissao.getCodigoComissao());
            Venda codigoVendaOld = persistentComissao.getCodigoVenda();
            Venda codigoVendaNew = comissao.getCodigoVenda();
            List<Regrascomissao> regrascomissaoListOld = persistentComissao.getRegrascomissaoList();
            List<Regrascomissao> regrascomissaoListNew = comissao.getRegrascomissaoList();
            if (codigoVendaNew != null) {
                codigoVendaNew = em.getReference(codigoVendaNew.getClass(), codigoVendaNew.getCodigoVenda());
                comissao.setCodigoVenda(codigoVendaNew);
            }
            List<Regrascomissao> attachedRegrascomissaoListNew = new ArrayList<Regrascomissao>();
            for (Regrascomissao regrascomissaoListNewRegrascomissaoToAttach : regrascomissaoListNew) {
                regrascomissaoListNewRegrascomissaoToAttach = em.getReference(regrascomissaoListNewRegrascomissaoToAttach.getClass(), regrascomissaoListNewRegrascomissaoToAttach.getCodigoRegra());
                attachedRegrascomissaoListNew.add(regrascomissaoListNewRegrascomissaoToAttach);
            }
            regrascomissaoListNew = attachedRegrascomissaoListNew;
            comissao.setRegrascomissaoList(regrascomissaoListNew);
            comissao = em.merge(comissao);
            if (codigoVendaOld != null && !codigoVendaOld.equals(codigoVendaNew)) {
                codigoVendaOld.getComissaoList().remove(comissao);
                codigoVendaOld = em.merge(codigoVendaOld);
            }
            if (codigoVendaNew != null && !codigoVendaNew.equals(codigoVendaOld)) {
                codigoVendaNew.getComissaoList().add(comissao);
                codigoVendaNew = em.merge(codigoVendaNew);
            }
            for (Regrascomissao regrascomissaoListOldRegrascomissao : regrascomissaoListOld) {
                if (!regrascomissaoListNew.contains(regrascomissaoListOldRegrascomissao)) {
                    regrascomissaoListOldRegrascomissao.setCodigoComissao(null);
                    regrascomissaoListOldRegrascomissao = em.merge(regrascomissaoListOldRegrascomissao);
                }
            }
            for (Regrascomissao regrascomissaoListNewRegrascomissao : regrascomissaoListNew) {
                if (!regrascomissaoListOld.contains(regrascomissaoListNewRegrascomissao)) {
                    Comissao oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao = regrascomissaoListNewRegrascomissao.getCodigoComissao();
                    regrascomissaoListNewRegrascomissao.setCodigoComissao(comissao);
                    regrascomissaoListNewRegrascomissao = em.merge(regrascomissaoListNewRegrascomissao);
                    if (oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao != null && !oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao.equals(comissao)) {
                        oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao.getRegrascomissaoList().remove(regrascomissaoListNewRegrascomissao);
                        oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao = em.merge(oldCodigoComissaoOfRegrascomissaoListNewRegrascomissao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comissao.getCodigoComissao();
                if (findComissao(id) == null) {
                    throw new NonexistentEntityException("The comissao with id " + id + " no longer exists.");
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
            Comissao comissao;
            try {
                comissao = em.getReference(Comissao.class, id);
                comissao.getCodigoComissao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comissao with id " + id + " no longer exists.", enfe);
            }
            Venda codigoVenda = comissao.getCodigoVenda();
            if (codigoVenda != null) {
                codigoVenda.getComissaoList().remove(comissao);
                codigoVenda = em.merge(codigoVenda);
            }
            List<Regrascomissao> regrascomissaoList = comissao.getRegrascomissaoList();
            for (Regrascomissao regrascomissaoListRegrascomissao : regrascomissaoList) {
                regrascomissaoListRegrascomissao.setCodigoComissao(null);
                regrascomissaoListRegrascomissao = em.merge(regrascomissaoListRegrascomissao);
            }
            em.remove(comissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comissao> findComissaoEntities() {
        return findComissaoEntities(true, -1, -1);
    }

    public List<Comissao> findComissaoEntities(int maxResults, int firstResult) {
        return findComissaoEntities(false, maxResults, firstResult);
    }

    private List<Comissao> findComissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comissao.class));
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

    public Comissao findComissao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getComissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comissao> rt = cq.from(Comissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
