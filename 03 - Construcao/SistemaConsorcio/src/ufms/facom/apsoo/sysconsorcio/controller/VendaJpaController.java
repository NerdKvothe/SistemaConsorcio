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
import ufms.facom.apsoo.sysconsorcio.model.Modeloveiculo;
import ufms.facom.apsoo.sysconsorcio.model.Vendedor;
import ufms.facom.apsoo.sysconsorcio.model.Administradora;
import ufms.facom.apsoo.sysconsorcio.model.Cliente;
import ufms.facom.apsoo.sysconsorcio.model.Comissao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Venda;

/**
 *
 * @author joshua
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        if (venda.getComissaoList() == null) {
            venda.setComissaoList(new ArrayList<Comissao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modeloveiculo codigoModelo = venda.getCodigoModelo();
            if (codigoModelo != null) {
                codigoModelo = em.getReference(codigoModelo.getClass(), codigoModelo.getCodigoModelo());
                venda.setCodigoModelo(codigoModelo);
            }
            Vendedor codigoVendedor = venda.getCodigoVendedor();
            if (codigoVendedor != null) {
                codigoVendedor = em.getReference(codigoVendedor.getClass(), codigoVendedor.getCodigoVendedor());
                venda.setCodigoVendedor(codigoVendedor);
            }
            Administradora codigoAdm = venda.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm = em.getReference(codigoAdm.getClass(), codigoAdm.getCodigoAdm());
                venda.setCodigoAdm(codigoAdm);
            }
            Cliente codigoCliente = venda.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente = em.getReference(codigoCliente.getClass(), codigoCliente.getCodigoCliente());
                venda.setCodigoCliente(codigoCliente);
            }
            List<Comissao> attachedComissaoList = new ArrayList<Comissao>();
            for (Comissao comissaoListComissaoToAttach : venda.getComissaoList()) {
                comissaoListComissaoToAttach = em.getReference(comissaoListComissaoToAttach.getClass(), comissaoListComissaoToAttach.getCodigoComissao());
                attachedComissaoList.add(comissaoListComissaoToAttach);
            }
            venda.setComissaoList(attachedComissaoList);
            em.persist(venda);
            if (codigoModelo != null) {
                codigoModelo.getVendaList().add(venda);
                codigoModelo = em.merge(codigoModelo);
            }
            if (codigoVendedor != null) {
                codigoVendedor.getVendaList().add(venda);
                codigoVendedor = em.merge(codigoVendedor);
            }
            if (codigoAdm != null) {
                codigoAdm.getVendaList().add(venda);
                codigoAdm = em.merge(codigoAdm);
            }
            if (codigoCliente != null) {
                codigoCliente.getVendaList().add(venda);
                codigoCliente = em.merge(codigoCliente);
            }
            for (Comissao comissaoListComissao : venda.getComissaoList()) {
                Venda oldCodigoVendaOfComissaoListComissao = comissaoListComissao.getCodigoVenda();
                comissaoListComissao.setCodigoVenda(venda);
                comissaoListComissao = em.merge(comissaoListComissao);
                if (oldCodigoVendaOfComissaoListComissao != null) {
                    oldCodigoVendaOfComissaoListComissao.getComissaoList().remove(comissaoListComissao);
                    oldCodigoVendaOfComissaoListComissao = em.merge(oldCodigoVendaOfComissaoListComissao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getCodigoVenda());
            Modeloveiculo codigoModeloOld = persistentVenda.getCodigoModelo();
            Modeloveiculo codigoModeloNew = venda.getCodigoModelo();
            Vendedor codigoVendedorOld = persistentVenda.getCodigoVendedor();
            Vendedor codigoVendedorNew = venda.getCodigoVendedor();
            Administradora codigoAdmOld = persistentVenda.getCodigoAdm();
            Administradora codigoAdmNew = venda.getCodigoAdm();
            Cliente codigoClienteOld = persistentVenda.getCodigoCliente();
            Cliente codigoClienteNew = venda.getCodigoCliente();
            List<Comissao> comissaoListOld = persistentVenda.getComissaoList();
            List<Comissao> comissaoListNew = venda.getComissaoList();
            if (codigoModeloNew != null) {
                codigoModeloNew = em.getReference(codigoModeloNew.getClass(), codigoModeloNew.getCodigoModelo());
                venda.setCodigoModelo(codigoModeloNew);
            }
            if (codigoVendedorNew != null) {
                codigoVendedorNew = em.getReference(codigoVendedorNew.getClass(), codigoVendedorNew.getCodigoVendedor());
                venda.setCodigoVendedor(codigoVendedorNew);
            }
            if (codigoAdmNew != null) {
                codigoAdmNew = em.getReference(codigoAdmNew.getClass(), codigoAdmNew.getCodigoAdm());
                venda.setCodigoAdm(codigoAdmNew);
            }
            if (codigoClienteNew != null) {
                codigoClienteNew = em.getReference(codigoClienteNew.getClass(), codigoClienteNew.getCodigoCliente());
                venda.setCodigoCliente(codigoClienteNew);
            }
            List<Comissao> attachedComissaoListNew = new ArrayList<Comissao>();
            for (Comissao comissaoListNewComissaoToAttach : comissaoListNew) {
                comissaoListNewComissaoToAttach = em.getReference(comissaoListNewComissaoToAttach.getClass(), comissaoListNewComissaoToAttach.getCodigoComissao());
                attachedComissaoListNew.add(comissaoListNewComissaoToAttach);
            }
            comissaoListNew = attachedComissaoListNew;
            venda.setComissaoList(comissaoListNew);
            venda = em.merge(venda);
            if (codigoModeloOld != null && !codigoModeloOld.equals(codigoModeloNew)) {
                codigoModeloOld.getVendaList().remove(venda);
                codigoModeloOld = em.merge(codigoModeloOld);
            }
            if (codigoModeloNew != null && !codigoModeloNew.equals(codigoModeloOld)) {
                codigoModeloNew.getVendaList().add(venda);
                codigoModeloNew = em.merge(codigoModeloNew);
            }
            if (codigoVendedorOld != null && !codigoVendedorOld.equals(codigoVendedorNew)) {
                codigoVendedorOld.getVendaList().remove(venda);
                codigoVendedorOld = em.merge(codigoVendedorOld);
            }
            if (codigoVendedorNew != null && !codigoVendedorNew.equals(codigoVendedorOld)) {
                codigoVendedorNew.getVendaList().add(venda);
                codigoVendedorNew = em.merge(codigoVendedorNew);
            }
            if (codigoAdmOld != null && !codigoAdmOld.equals(codigoAdmNew)) {
                codigoAdmOld.getVendaList().remove(venda);
                codigoAdmOld = em.merge(codigoAdmOld);
            }
            if (codigoAdmNew != null && !codigoAdmNew.equals(codigoAdmOld)) {
                codigoAdmNew.getVendaList().add(venda);
                codigoAdmNew = em.merge(codigoAdmNew);
            }
            if (codigoClienteOld != null && !codigoClienteOld.equals(codigoClienteNew)) {
                codigoClienteOld.getVendaList().remove(venda);
                codigoClienteOld = em.merge(codigoClienteOld);
            }
            if (codigoClienteNew != null && !codigoClienteNew.equals(codigoClienteOld)) {
                codigoClienteNew.getVendaList().add(venda);
                codigoClienteNew = em.merge(codigoClienteNew);
            }
            for (Comissao comissaoListOldComissao : comissaoListOld) {
                if (!comissaoListNew.contains(comissaoListOldComissao)) {
                    comissaoListOldComissao.setCodigoVenda(null);
                    comissaoListOldComissao = em.merge(comissaoListOldComissao);
                }
            }
            for (Comissao comissaoListNewComissao : comissaoListNew) {
                if (!comissaoListOld.contains(comissaoListNewComissao)) {
                    Venda oldCodigoVendaOfComissaoListNewComissao = comissaoListNewComissao.getCodigoVenda();
                    comissaoListNewComissao.setCodigoVenda(venda);
                    comissaoListNewComissao = em.merge(comissaoListNewComissao);
                    if (oldCodigoVendaOfComissaoListNewComissao != null && !oldCodigoVendaOfComissaoListNewComissao.equals(venda)) {
                        oldCodigoVendaOfComissaoListNewComissao.getComissaoList().remove(comissaoListNewComissao);
                        oldCodigoVendaOfComissaoListNewComissao = em.merge(oldCodigoVendaOfComissaoListNewComissao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getCodigoVenda();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
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
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getCodigoVenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            Modeloveiculo codigoModelo = venda.getCodigoModelo();
            if (codigoModelo != null) {
                codigoModelo.getVendaList().remove(venda);
                codigoModelo = em.merge(codigoModelo);
            }
            Vendedor codigoVendedor = venda.getCodigoVendedor();
            if (codigoVendedor != null) {
                codigoVendedor.getVendaList().remove(venda);
                codigoVendedor = em.merge(codigoVendedor);
            }
            Administradora codigoAdm = venda.getCodigoAdm();
            if (codigoAdm != null) {
                codigoAdm.getVendaList().remove(venda);
                codigoAdm = em.merge(codigoAdm);
            }
            Cliente codigoCliente = venda.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente.getVendaList().remove(venda);
                codigoCliente = em.merge(codigoCliente);
            }
            List<Comissao> comissaoList = venda.getComissaoList();
            for (Comissao comissaoListComissao : comissaoList) {
                comissaoListComissao.setCodigoVenda(null);
                comissaoListComissao = em.merge(comissaoListComissao);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
