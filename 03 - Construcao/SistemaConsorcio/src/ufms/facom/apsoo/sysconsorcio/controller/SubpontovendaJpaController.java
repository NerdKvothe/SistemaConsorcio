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
import ufms.facom.apsoo.sysconsorcio.model.Pontovenda;
import ufms.facom.apsoo.sysconsorcio.model.Vendedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Subpontovenda;

/**
 *
 * @author joshua
 */
public class SubpontovendaJpaController implements Serializable {

    public SubpontovendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subpontovenda subpontovenda) {
        if (subpontovenda.getVendedorList() == null) {
            subpontovenda.setVendedorList(new ArrayList<Vendedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pontovenda codigoPtoVenda = subpontovenda.getCodigoPtoVenda();
            if (codigoPtoVenda != null) {
                codigoPtoVenda = em.getReference(codigoPtoVenda.getClass(), codigoPtoVenda.getCodigoPtoVenda());
                subpontovenda.setCodigoPtoVenda(codigoPtoVenda);
            }
            List<Vendedor> attachedVendedorList = new ArrayList<Vendedor>();
            for (Vendedor vendedorListVendedorToAttach : subpontovenda.getVendedorList()) {
                vendedorListVendedorToAttach = em.getReference(vendedorListVendedorToAttach.getClass(), vendedorListVendedorToAttach.getCodigoVendedor());
                attachedVendedorList.add(vendedorListVendedorToAttach);
            }
            subpontovenda.setVendedorList(attachedVendedorList);
            em.persist(subpontovenda);
            if (codigoPtoVenda != null) {
                codigoPtoVenda.getSubpontovendaList().add(subpontovenda);
                codigoPtoVenda = em.merge(codigoPtoVenda);
            }
            for (Vendedor vendedorListVendedor : subpontovenda.getVendedorList()) {
                Subpontovenda oldCodigoSbPtoVendaOfVendedorListVendedor = vendedorListVendedor.getCodigoSbPtoVenda();
                vendedorListVendedor.setCodigoSbPtoVenda(subpontovenda);
                vendedorListVendedor = em.merge(vendedorListVendedor);
                if (oldCodigoSbPtoVendaOfVendedorListVendedor != null) {
                    oldCodigoSbPtoVendaOfVendedorListVendedor.getVendedorList().remove(vendedorListVendedor);
                    oldCodigoSbPtoVendaOfVendedorListVendedor = em.merge(oldCodigoSbPtoVendaOfVendedorListVendedor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subpontovenda subpontovenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subpontovenda persistentSubpontovenda = em.find(Subpontovenda.class, subpontovenda.getCodigoSbPtoVenda());
            Pontovenda codigoPtoVendaOld = persistentSubpontovenda.getCodigoPtoVenda();
            Pontovenda codigoPtoVendaNew = subpontovenda.getCodigoPtoVenda();
            List<Vendedor> vendedorListOld = persistentSubpontovenda.getVendedorList();
            List<Vendedor> vendedorListNew = subpontovenda.getVendedorList();
            if (codigoPtoVendaNew != null) {
                codigoPtoVendaNew = em.getReference(codigoPtoVendaNew.getClass(), codigoPtoVendaNew.getCodigoPtoVenda());
                subpontovenda.setCodigoPtoVenda(codigoPtoVendaNew);
            }
            List<Vendedor> attachedVendedorListNew = new ArrayList<Vendedor>();
            for (Vendedor vendedorListNewVendedorToAttach : vendedorListNew) {
                vendedorListNewVendedorToAttach = em.getReference(vendedorListNewVendedorToAttach.getClass(), vendedorListNewVendedorToAttach.getCodigoVendedor());
                attachedVendedorListNew.add(vendedorListNewVendedorToAttach);
            }
            vendedorListNew = attachedVendedorListNew;
            subpontovenda.setVendedorList(vendedorListNew);
            subpontovenda = em.merge(subpontovenda);
            if (codigoPtoVendaOld != null && !codigoPtoVendaOld.equals(codigoPtoVendaNew)) {
                codigoPtoVendaOld.getSubpontovendaList().remove(subpontovenda);
                codigoPtoVendaOld = em.merge(codigoPtoVendaOld);
            }
            if (codigoPtoVendaNew != null && !codigoPtoVendaNew.equals(codigoPtoVendaOld)) {
                codigoPtoVendaNew.getSubpontovendaList().add(subpontovenda);
                codigoPtoVendaNew = em.merge(codigoPtoVendaNew);
            }
            for (Vendedor vendedorListOldVendedor : vendedorListOld) {
                if (!vendedorListNew.contains(vendedorListOldVendedor)) {
                    vendedorListOldVendedor.setCodigoSbPtoVenda(null);
                    vendedorListOldVendedor = em.merge(vendedorListOldVendedor);
                }
            }
            for (Vendedor vendedorListNewVendedor : vendedorListNew) {
                if (!vendedorListOld.contains(vendedorListNewVendedor)) {
                    Subpontovenda oldCodigoSbPtoVendaOfVendedorListNewVendedor = vendedorListNewVendedor.getCodigoSbPtoVenda();
                    vendedorListNewVendedor.setCodigoSbPtoVenda(subpontovenda);
                    vendedorListNewVendedor = em.merge(vendedorListNewVendedor);
                    if (oldCodigoSbPtoVendaOfVendedorListNewVendedor != null && !oldCodigoSbPtoVendaOfVendedorListNewVendedor.equals(subpontovenda)) {
                        oldCodigoSbPtoVendaOfVendedorListNewVendedor.getVendedorList().remove(vendedorListNewVendedor);
                        oldCodigoSbPtoVendaOfVendedorListNewVendedor = em.merge(oldCodigoSbPtoVendaOfVendedorListNewVendedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subpontovenda.getCodigoSbPtoVenda();
                if (findSubpontovenda(id) == null) {
                    throw new NonexistentEntityException("The subpontovenda with id " + id + " no longer exists.");
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
            Subpontovenda subpontovenda;
            try {
                subpontovenda = em.getReference(Subpontovenda.class, id);
                subpontovenda.getCodigoSbPtoVenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subpontovenda with id " + id + " no longer exists.", enfe);
            }
            Pontovenda codigoPtoVenda = subpontovenda.getCodigoPtoVenda();
            if (codigoPtoVenda != null) {
                codigoPtoVenda.getSubpontovendaList().remove(subpontovenda);
                codigoPtoVenda = em.merge(codigoPtoVenda);
            }
            List<Vendedor> vendedorList = subpontovenda.getVendedorList();
            for (Vendedor vendedorListVendedor : vendedorList) {
                vendedorListVendedor.setCodigoSbPtoVenda(null);
                vendedorListVendedor = em.merge(vendedorListVendedor);
            }
            em.remove(subpontovenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subpontovenda> findSubpontovendaEntities() {
        return findSubpontovendaEntities(true, -1, -1);
    }

    public List<Subpontovenda> findSubpontovendaEntities(int maxResults, int firstResult) {
        return findSubpontovendaEntities(false, maxResults, firstResult);
    }

    private List<Subpontovenda> findSubpontovendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subpontovenda.class));
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

    public Subpontovenda findSubpontovenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subpontovenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubpontovendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subpontovenda> rt = cq.from(Subpontovenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
