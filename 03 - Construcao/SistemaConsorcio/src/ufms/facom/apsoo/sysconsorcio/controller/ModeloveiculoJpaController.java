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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.PreexistingEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Modeloveiculo;

/**
 *
 * @author joshua
 */
public class ModeloveiculoJpaController implements Serializable {

    public ModeloveiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modeloveiculo modeloveiculo) throws PreexistingEntityException, Exception {
        if (modeloveiculo.getVendaList() == null) {
            modeloveiculo.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : modeloveiculo.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getCodigoVenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            modeloveiculo.setVendaList(attachedVendaList);
            em.persist(modeloveiculo);
            for (Venda vendaListVenda : modeloveiculo.getVendaList()) {
                Modeloveiculo oldCodigoModeloOfVendaListVenda = vendaListVenda.getCodigoModelo();
                vendaListVenda.setCodigoModelo(modeloveiculo);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldCodigoModeloOfVendaListVenda != null) {
                    oldCodigoModeloOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldCodigoModeloOfVendaListVenda = em.merge(oldCodigoModeloOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModeloveiculo(modeloveiculo.getCodigoModelo()) != null) {
                throw new PreexistingEntityException("Modeloveiculo " + modeloveiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modeloveiculo modeloveiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modeloveiculo persistentModeloveiculo = em.find(Modeloveiculo.class, modeloveiculo.getCodigoModelo());
            List<Venda> vendaListOld = persistentModeloveiculo.getVendaList();
            List<Venda> vendaListNew = modeloveiculo.getVendaList();
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getCodigoVenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            modeloveiculo.setVendaList(vendaListNew);
            modeloveiculo = em.merge(modeloveiculo);
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    vendaListOldVenda.setCodigoModelo(null);
                    vendaListOldVenda = em.merge(vendaListOldVenda);
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Modeloveiculo oldCodigoModeloOfVendaListNewVenda = vendaListNewVenda.getCodigoModelo();
                    vendaListNewVenda.setCodigoModelo(modeloveiculo);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldCodigoModeloOfVendaListNewVenda != null && !oldCodigoModeloOfVendaListNewVenda.equals(modeloveiculo)) {
                        oldCodigoModeloOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldCodigoModeloOfVendaListNewVenda = em.merge(oldCodigoModeloOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modeloveiculo.getCodigoModelo();
                if (findModeloveiculo(id) == null) {
                    throw new NonexistentEntityException("The modeloveiculo with id " + id + " no longer exists.");
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
            Modeloveiculo modeloveiculo;
            try {
                modeloveiculo = em.getReference(Modeloveiculo.class, id);
                modeloveiculo.getCodigoModelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modeloveiculo with id " + id + " no longer exists.", enfe);
            }
            List<Venda> vendaList = modeloveiculo.getVendaList();
            for (Venda vendaListVenda : vendaList) {
                vendaListVenda.setCodigoModelo(null);
                vendaListVenda = em.merge(vendaListVenda);
            }
            em.remove(modeloveiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modeloveiculo> findModeloveiculoEntities() {
        return findModeloveiculoEntities(true, -1, -1);
    }

    public List<Modeloveiculo> findModeloveiculoEntities(int maxResults, int firstResult) {
        return findModeloveiculoEntities(false, maxResults, firstResult);
    }

    private List<Modeloveiculo> findModeloveiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modeloveiculo.class));
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

    public Modeloveiculo findModeloveiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modeloveiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloveiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modeloveiculo> rt = cq.from(Modeloveiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
