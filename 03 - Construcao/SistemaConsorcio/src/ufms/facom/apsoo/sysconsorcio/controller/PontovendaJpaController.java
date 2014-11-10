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
import ufms.facom.apsoo.sysconsorcio.model.Subpontovenda;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.PreexistingEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Pontovenda;

/**
 *
 * @author joshua
 */
public class PontovendaJpaController implements Serializable {

    public PontovendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pontovenda pontovenda) throws PreexistingEntityException, Exception {
        if (pontovenda.getSubpontovendaList() == null) {
            pontovenda.setSubpontovendaList(new ArrayList<Subpontovenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Subpontovenda> attachedSubpontovendaList = new ArrayList<Subpontovenda>();
            for (Subpontovenda subpontovendaListSubpontovendaToAttach : pontovenda.getSubpontovendaList()) {
                subpontovendaListSubpontovendaToAttach = em.getReference(subpontovendaListSubpontovendaToAttach.getClass(), subpontovendaListSubpontovendaToAttach.getCodigoSbPtoVenda());
                attachedSubpontovendaList.add(subpontovendaListSubpontovendaToAttach);
            }
            pontovenda.setSubpontovendaList(attachedSubpontovendaList);
            em.persist(pontovenda);
            for (Subpontovenda subpontovendaListSubpontovenda : pontovenda.getSubpontovendaList()) {
                Pontovenda oldCodigoPtoVendaOfSubpontovendaListSubpontovenda = subpontovendaListSubpontovenda.getCodigoPtoVenda();
                subpontovendaListSubpontovenda.setCodigoPtoVenda(pontovenda);
                subpontovendaListSubpontovenda = em.merge(subpontovendaListSubpontovenda);
                if (oldCodigoPtoVendaOfSubpontovendaListSubpontovenda != null) {
                    oldCodigoPtoVendaOfSubpontovendaListSubpontovenda.getSubpontovendaList().remove(subpontovendaListSubpontovenda);
                    oldCodigoPtoVendaOfSubpontovendaListSubpontovenda = em.merge(oldCodigoPtoVendaOfSubpontovendaListSubpontovenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPontovenda(pontovenda.getCodigoPtoVenda()) != null) {
                throw new PreexistingEntityException("Pontovenda " + pontovenda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pontovenda pontovenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontovenda persistentPontovenda = em.find(Pontovenda.class, pontovenda.getCodigoPtoVenda());
            List<Subpontovenda> subpontovendaListOld = persistentPontovenda.getSubpontovendaList();
            List<Subpontovenda> subpontovendaListNew = pontovenda.getSubpontovendaList();
            List<Subpontovenda> attachedSubpontovendaListNew = new ArrayList<Subpontovenda>();
            for (Subpontovenda subpontovendaListNewSubpontovendaToAttach : subpontovendaListNew) {
                subpontovendaListNewSubpontovendaToAttach = em.getReference(subpontovendaListNewSubpontovendaToAttach.getClass(), subpontovendaListNewSubpontovendaToAttach.getCodigoSbPtoVenda());
                attachedSubpontovendaListNew.add(subpontovendaListNewSubpontovendaToAttach);
            }
            subpontovendaListNew = attachedSubpontovendaListNew;
            pontovenda.setSubpontovendaList(subpontovendaListNew);
            pontovenda = em.merge(pontovenda);
            for (Subpontovenda subpontovendaListOldSubpontovenda : subpontovendaListOld) {
                if (!subpontovendaListNew.contains(subpontovendaListOldSubpontovenda)) {
                    subpontovendaListOldSubpontovenda.setCodigoPtoVenda(null);
                    subpontovendaListOldSubpontovenda = em.merge(subpontovendaListOldSubpontovenda);
                }
            }
            for (Subpontovenda subpontovendaListNewSubpontovenda : subpontovendaListNew) {
                if (!subpontovendaListOld.contains(subpontovendaListNewSubpontovenda)) {
                    Pontovenda oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda = subpontovendaListNewSubpontovenda.getCodigoPtoVenda();
                    subpontovendaListNewSubpontovenda.setCodigoPtoVenda(pontovenda);
                    subpontovendaListNewSubpontovenda = em.merge(subpontovendaListNewSubpontovenda);
                    if (oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda != null && !oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda.equals(pontovenda)) {
                        oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda.getSubpontovendaList().remove(subpontovendaListNewSubpontovenda);
                        oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda = em.merge(oldCodigoPtoVendaOfSubpontovendaListNewSubpontovenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pontovenda.getCodigoPtoVenda();
                if (findPontovenda(id) == null) {
                    throw new NonexistentEntityException("The pontovenda with id " + id + " no longer exists.");
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
            Pontovenda pontovenda;
            try {
                pontovenda = em.getReference(Pontovenda.class, id);
                pontovenda.getCodigoPtoVenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pontovenda with id " + id + " no longer exists.", enfe);
            }
            List<Subpontovenda> subpontovendaList = pontovenda.getSubpontovendaList();
            for (Subpontovenda subpontovendaListSubpontovenda : subpontovendaList) {
                subpontovendaListSubpontovenda.setCodigoPtoVenda(null);
                subpontovendaListSubpontovenda = em.merge(subpontovendaListSubpontovenda);
            }
            em.remove(pontovenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pontovenda> findPontovendaEntities() {
        return findPontovendaEntities(true, -1, -1);
    }

    public List<Pontovenda> findPontovendaEntities(int maxResults, int firstResult) {
        return findPontovendaEntities(false, maxResults, firstResult);
    }

    private List<Pontovenda> findPontovendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pontovenda.class));
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

    public Pontovenda findPontovenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pontovenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getPontovendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pontovenda> rt = cq.from(Pontovenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
