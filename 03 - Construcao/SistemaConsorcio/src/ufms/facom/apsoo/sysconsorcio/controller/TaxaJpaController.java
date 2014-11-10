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
import ufms.facom.apsoo.sysconsorcio.model.Taxa;
import ufms.facom.apsoo.sysconsorcio.model.Tipotaxa;

/**
 *
 * @author joshua
 */
public class TaxaJpaController implements Serializable {

    public TaxaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Taxa taxa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administradora codigoAdm = taxa.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm = em.getReference(codigoAdm.getClass(), codigoAdm.getCodigoAdm());
                taxa.setCodigoAdm(codigoAdm);
            }
            Tipotaxa codigoTpTaxa = taxa.getCodigoTpTaxa();
            if (codigoTpTaxa != null) {
                codigoTpTaxa = em.getReference(codigoTpTaxa.getClass(), codigoTpTaxa.getCodigoTpTaxa());
                taxa.setCodigoTpTaxa(codigoTpTaxa);
            }
            em.persist(taxa);
            if (codigoAdm != null) {
                codigoAdm.getTaxaList().add(taxa);
                codigoAdm = em.merge(codigoAdm);
            }
            if (codigoTpTaxa != null) {
                codigoTpTaxa.getTaxaList().add(taxa);
                codigoTpTaxa = em.merge(codigoTpTaxa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Taxa taxa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taxa persistentTaxa = em.find(Taxa.class, taxa.getCodigoTaxa());
            Administradora codigoAdmOld = persistentTaxa.getCodigoAdm();
            Administradora codigoAdmNew = taxa.getCodigoAdm();
            Tipotaxa codigoTpTaxaOld = persistentTaxa.getCodigoTpTaxa();
            Tipotaxa codigoTpTaxaNew = taxa.getCodigoTpTaxa();
            if (codigoAdmNew != null) {
                codigoAdmNew = em.getReference(codigoAdmNew.getClass(), codigoAdmNew.getCodigoAdm());
                taxa.setCodigoAdm(codigoAdmNew);
            }
            if (codigoTpTaxaNew != null) {
                codigoTpTaxaNew = em.getReference(codigoTpTaxaNew.getClass(), codigoTpTaxaNew.getCodigoTpTaxa());
                taxa.setCodigoTpTaxa(codigoTpTaxaNew);
            }
            taxa = em.merge(taxa);
            if (codigoAdmOld != null && !codigoAdmOld.equals(codigoAdmNew)) {
                codigoAdmOld.getTaxaList().remove(taxa);
                codigoAdmOld = em.merge(codigoAdmOld);
            }
            if (codigoAdmNew != null && !codigoAdmNew.equals(codigoAdmOld)) {
                codigoAdmNew.getTaxaList().add(taxa);
                codigoAdmNew = em.merge(codigoAdmNew);
            }
            if (codigoTpTaxaOld != null && !codigoTpTaxaOld.equals(codigoTpTaxaNew)) {
                codigoTpTaxaOld.getTaxaList().remove(taxa);
                codigoTpTaxaOld = em.merge(codigoTpTaxaOld);
            }
            if (codigoTpTaxaNew != null && !codigoTpTaxaNew.equals(codigoTpTaxaOld)) {
                codigoTpTaxaNew.getTaxaList().add(taxa);
                codigoTpTaxaNew = em.merge(codigoTpTaxaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = taxa.getCodigoTaxa();
                if (findTaxa(id) == null) {
                    throw new NonexistentEntityException("The taxa with id " + id + " no longer exists.");
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
            Taxa taxa;
            try {
                taxa = em.getReference(Taxa.class, id);
                taxa.getCodigoTaxa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taxa with id " + id + " no longer exists.", enfe);
            }
            Administradora codigoAdm = taxa.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm.getTaxaList().remove(taxa);
                codigoAdm = em.merge(codigoAdm);
            }
            Tipotaxa codigoTpTaxa = taxa.getCodigoTpTaxa();
            if (codigoTpTaxa != null) {
                codigoTpTaxa.getTaxaList().remove(taxa);
                codigoTpTaxa = em.merge(codigoTpTaxa);
            }
            em.remove(taxa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Taxa> findTaxaEntities() {
        return findTaxaEntities(true, -1, -1);
    }

    public List<Taxa> findTaxaEntities(int maxResults, int firstResult) {
        return findTaxaEntities(false, maxResults, firstResult);
    }

    private List<Taxa> findTaxaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taxa.class));
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

    public Taxa findTaxa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Taxa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaxaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taxa> rt = cq.from(Taxa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
