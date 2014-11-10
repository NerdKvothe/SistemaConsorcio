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
import ufms.facom.apsoo.sysconsorcio.model.Cliente;

/**
 *
 * @author joshua
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getVendaList() == null) {
            cliente.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : cliente.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getCodigoVenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            cliente.setVendaList(attachedVendaList);
            em.persist(cliente);
            for (Venda vendaListVenda : cliente.getVendaList()) {
                Cliente oldCodigoClienteOfVendaListVenda = vendaListVenda.getCodigoCliente();
                vendaListVenda.setCodigoCliente(cliente);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldCodigoClienteOfVendaListVenda != null) {
                    oldCodigoClienteOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldCodigoClienteOfVendaListVenda = em.merge(oldCodigoClienteOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getCodigoCliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCodigoCliente());
            List<Venda> vendaListOld = persistentCliente.getVendaList();
            List<Venda> vendaListNew = cliente.getVendaList();
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getCodigoVenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            cliente.setVendaList(vendaListNew);
            cliente = em.merge(cliente);
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    vendaListOldVenda.setCodigoCliente(null);
                    vendaListOldVenda = em.merge(vendaListOldVenda);
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Cliente oldCodigoClienteOfVendaListNewVenda = vendaListNewVenda.getCodigoCliente();
                    vendaListNewVenda.setCodigoCliente(cliente);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldCodigoClienteOfVendaListNewVenda != null && !oldCodigoClienteOfVendaListNewVenda.equals(cliente)) {
                        oldCodigoClienteOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldCodigoClienteOfVendaListNewVenda = em.merge(oldCodigoClienteOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCodigoCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCodigoCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Venda> vendaList = cliente.getVendaList();
            for (Venda vendaListVenda : vendaList) {
                vendaListVenda.setCodigoCliente(null);
                vendaListVenda = em.merge(vendaListVenda);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
