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
import ufms.facom.apsoo.sysconsorcio.model.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Vendedor;

/**
 *
 * @author joshua
 */
public class VendedorJpaController implements Serializable {

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) {
        if (vendedor.getVendaList() == null) {
            vendedor.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subpontovenda codigoSbPtoVenda = vendedor.getCodigoSbPtoVenda();
            if (codigoSbPtoVenda != null) {
                codigoSbPtoVenda = em.getReference(codigoSbPtoVenda.getClass(), codigoSbPtoVenda.getCodigoSbPtoVenda());
                vendedor.setCodigoSbPtoVenda(codigoSbPtoVenda);
            }
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : vendedor.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getCodigoVenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            vendedor.setVendaList(attachedVendaList);
            em.persist(vendedor);
            if (codigoSbPtoVenda != null) {
                codigoSbPtoVenda.getVendedorList().add(vendedor);
                codigoSbPtoVenda = em.merge(codigoSbPtoVenda);
            }
            for (Venda vendaListVenda : vendedor.getVendaList()) {
                Vendedor oldCodigoVendedorOfVendaListVenda = vendaListVenda.getCodigoVendedor();
                vendaListVenda.setCodigoVendedor(vendedor);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldCodigoVendedorOfVendaListVenda != null) {
                    oldCodigoVendedorOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldCodigoVendedorOfVendaListVenda = em.merge(oldCodigoVendedorOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getCodigoVendedor());
            Subpontovenda codigoSbPtoVendaOld = persistentVendedor.getCodigoSbPtoVenda();
            Subpontovenda codigoSbPtoVendaNew = vendedor.getCodigoSbPtoVenda();
            List<Venda> vendaListOld = persistentVendedor.getVendaList();
            List<Venda> vendaListNew = vendedor.getVendaList();
            if (codigoSbPtoVendaNew != null) {
                codigoSbPtoVendaNew = em.getReference(codigoSbPtoVendaNew.getClass(), codigoSbPtoVendaNew.getCodigoSbPtoVenda());
                vendedor.setCodigoSbPtoVenda(codigoSbPtoVendaNew);
            }
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getCodigoVenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            vendedor.setVendaList(vendaListNew);
            vendedor = em.merge(vendedor);
            if (codigoSbPtoVendaOld != null && !codigoSbPtoVendaOld.equals(codigoSbPtoVendaNew)) {
                codigoSbPtoVendaOld.getVendedorList().remove(vendedor);
                codigoSbPtoVendaOld = em.merge(codigoSbPtoVendaOld);
            }
            if (codigoSbPtoVendaNew != null && !codigoSbPtoVendaNew.equals(codigoSbPtoVendaOld)) {
                codigoSbPtoVendaNew.getVendedorList().add(vendedor);
                codigoSbPtoVendaNew = em.merge(codigoSbPtoVendaNew);
            }
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    vendaListOldVenda.setCodigoVendedor(null);
                    vendaListOldVenda = em.merge(vendaListOldVenda);
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Vendedor oldCodigoVendedorOfVendaListNewVenda = vendaListNewVenda.getCodigoVendedor();
                    vendaListNewVenda.setCodigoVendedor(vendedor);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldCodigoVendedorOfVendaListNewVenda != null && !oldCodigoVendedorOfVendaListNewVenda.equals(vendedor)) {
                        oldCodigoVendedorOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldCodigoVendedorOfVendaListNewVenda = em.merge(oldCodigoVendedorOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendedor.getCodigoVendedor();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
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
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getCodigoVendedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
            }
            Subpontovenda codigoSbPtoVenda = vendedor.getCodigoSbPtoVenda();
            if (codigoSbPtoVenda != null) {
                codigoSbPtoVenda.getVendedorList().remove(vendedor);
                codigoSbPtoVenda = em.merge(codigoSbPtoVenda);
            }
            List<Venda> vendaList = vendedor.getVendaList();
            for (Venda vendaListVenda : vendaList) {
                vendaListVenda.setCodigoVendedor(null);
                vendaListVenda = em.merge(vendaListVenda);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
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

    public Vendedor findVendedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
