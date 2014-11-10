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
import ufms.facom.apsoo.sysconsorcio.model.Taxa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Tipotaxa;

/**
 *
 * @author joshua
 */
public class TipotaxaJpaController implements Serializable {

    public TipotaxaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipotaxa tipotaxa) {
        if (tipotaxa.getTaxaList() == null) {
            tipotaxa.setTaxaList(new ArrayList<Taxa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Taxa> attachedTaxaList = new ArrayList<Taxa>();
            for (Taxa taxaListTaxaToAttach : tipotaxa.getTaxaList()) {
                taxaListTaxaToAttach = em.getReference(taxaListTaxaToAttach.getClass(), taxaListTaxaToAttach.getCodigoTaxa());
                attachedTaxaList.add(taxaListTaxaToAttach);
            }
            tipotaxa.setTaxaList(attachedTaxaList);
            em.persist(tipotaxa);
            for (Taxa taxaListTaxa : tipotaxa.getTaxaList()) {
                Tipotaxa oldCodigoTpTaxaOfTaxaListTaxa = taxaListTaxa.getCodigoTpTaxa();
                taxaListTaxa.setCodigoTpTaxa(tipotaxa);
                taxaListTaxa = em.merge(taxaListTaxa);
                if (oldCodigoTpTaxaOfTaxaListTaxa != null) {
                    oldCodigoTpTaxaOfTaxaListTaxa.getTaxaList().remove(taxaListTaxa);
                    oldCodigoTpTaxaOfTaxaListTaxa = em.merge(oldCodigoTpTaxaOfTaxaListTaxa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipotaxa tipotaxa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipotaxa persistentTipotaxa = em.find(Tipotaxa.class, tipotaxa.getCodigoTpTaxa());
            List<Taxa> taxaListOld = persistentTipotaxa.getTaxaList();
            List<Taxa> taxaListNew = tipotaxa.getTaxaList();
            List<Taxa> attachedTaxaListNew = new ArrayList<Taxa>();
            for (Taxa taxaListNewTaxaToAttach : taxaListNew) {
                taxaListNewTaxaToAttach = em.getReference(taxaListNewTaxaToAttach.getClass(), taxaListNewTaxaToAttach.getCodigoTaxa());
                attachedTaxaListNew.add(taxaListNewTaxaToAttach);
            }
            taxaListNew = attachedTaxaListNew;
            tipotaxa.setTaxaList(taxaListNew);
            tipotaxa = em.merge(tipotaxa);
            for (Taxa taxaListOldTaxa : taxaListOld) {
                if (!taxaListNew.contains(taxaListOldTaxa)) {
                    taxaListOldTaxa.setCodigoTpTaxa(null);
                    taxaListOldTaxa = em.merge(taxaListOldTaxa);
                }
            }
            for (Taxa taxaListNewTaxa : taxaListNew) {
                if (!taxaListOld.contains(taxaListNewTaxa)) {
                    Tipotaxa oldCodigoTpTaxaOfTaxaListNewTaxa = taxaListNewTaxa.getCodigoTpTaxa();
                    taxaListNewTaxa.setCodigoTpTaxa(tipotaxa);
                    taxaListNewTaxa = em.merge(taxaListNewTaxa);
                    if (oldCodigoTpTaxaOfTaxaListNewTaxa != null && !oldCodigoTpTaxaOfTaxaListNewTaxa.equals(tipotaxa)) {
                        oldCodigoTpTaxaOfTaxaListNewTaxa.getTaxaList().remove(taxaListNewTaxa);
                        oldCodigoTpTaxaOfTaxaListNewTaxa = em.merge(oldCodigoTpTaxaOfTaxaListNewTaxa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipotaxa.getCodigoTpTaxa();
                if (findTipotaxa(id) == null) {
                    throw new NonexistentEntityException("The tipotaxa with id " + id + " no longer exists.");
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
            Tipotaxa tipotaxa;
            try {
                tipotaxa = em.getReference(Tipotaxa.class, id);
                tipotaxa.getCodigoTpTaxa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipotaxa with id " + id + " no longer exists.", enfe);
            }
            List<Taxa> taxaList = tipotaxa.getTaxaList();
            for (Taxa taxaListTaxa : taxaList) {
                taxaListTaxa.setCodigoTpTaxa(null);
                taxaListTaxa = em.merge(taxaListTaxa);
            }
            em.remove(tipotaxa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipotaxa> findTipotaxaEntities() {
        return findTipotaxaEntities(true, -1, -1);
    }

    public List<Tipotaxa> findTipotaxaEntities(int maxResults, int firstResult) {
        return findTipotaxaEntities(false, maxResults, firstResult);
    }

    private List<Tipotaxa> findTipotaxaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipotaxa.class));
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

    public Tipotaxa findTipotaxa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipotaxa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipotaxaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipotaxa> rt = cq.from(Tipotaxa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
