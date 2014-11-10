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
import ufms.facom.apsoo.sysconsorcio.model.Regrascomissao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Administradora;
import ufms.facom.apsoo.sysconsorcio.model.Venda;
import ufms.facom.apsoo.sysconsorcio.model.Taxa;

/**
 *
 * @author joshua
 */
public class AdministradoraJpaController implements Serializable {

    public AdministradoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administradora administradora) {
        if (administradora.getRegrascomissaoList() == null) {
            administradora.setRegrascomissaoList(new ArrayList<Regrascomissao>());
        }
        if (administradora.getVendaList() == null) {
            administradora.setVendaList(new ArrayList<Venda>());
        }
        if (administradora.getTaxaList() == null) {
            administradora.setTaxaList(new ArrayList<Taxa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Regrascomissao> attachedRegrascomissaoList = new ArrayList<Regrascomissao>();
            for (Regrascomissao regrascomissaoListRegrascomissaoToAttach : administradora.getRegrascomissaoList()) {
                regrascomissaoListRegrascomissaoToAttach = em.getReference(regrascomissaoListRegrascomissaoToAttach.getClass(), regrascomissaoListRegrascomissaoToAttach.getCodigoRegra());
                attachedRegrascomissaoList.add(regrascomissaoListRegrascomissaoToAttach);
            }
            administradora.setRegrascomissaoList(attachedRegrascomissaoList);
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : administradora.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getCodigoVenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            administradora.setVendaList(attachedVendaList);
            List<Taxa> attachedTaxaList = new ArrayList<Taxa>();
            for (Taxa taxaListTaxaToAttach : administradora.getTaxaList()) {
                taxaListTaxaToAttach = em.getReference(taxaListTaxaToAttach.getClass(), taxaListTaxaToAttach.getCodigoTaxa());
                attachedTaxaList.add(taxaListTaxaToAttach);
            }
            administradora.setTaxaList(attachedTaxaList);
            em.persist(administradora);
            for (Regrascomissao regrascomissaoListRegrascomissao : administradora.getRegrascomissaoList()) {
                Administradora oldCodigoAdmOfRegrascomissaoListRegrascomissao = regrascomissaoListRegrascomissao.getCodigoAdm();
                regrascomissaoListRegrascomissao.setCodigoAdm(administradora);
                regrascomissaoListRegrascomissao = em.merge(regrascomissaoListRegrascomissao);
                if (oldCodigoAdmOfRegrascomissaoListRegrascomissao != null) {
                    oldCodigoAdmOfRegrascomissaoListRegrascomissao.getRegrascomissaoList().remove(regrascomissaoListRegrascomissao);
                    oldCodigoAdmOfRegrascomissaoListRegrascomissao = em.merge(oldCodigoAdmOfRegrascomissaoListRegrascomissao);
                }
            }
            for (Venda vendaListVenda : administradora.getVendaList()) {
                Administradora oldCodigoAdmOfVendaListVenda = vendaListVenda.getCodigoAdm();
                vendaListVenda.setCodigoAdm(administradora);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldCodigoAdmOfVendaListVenda != null) {
                    oldCodigoAdmOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldCodigoAdmOfVendaListVenda = em.merge(oldCodigoAdmOfVendaListVenda);
                }
            }
            for (Taxa taxaListTaxa : administradora.getTaxaList()) {
                Administradora oldCodigoAdmOfTaxaListTaxa = taxaListTaxa.getCodigoAdm();
                taxaListTaxa.setCodigoAdm(administradora);
                taxaListTaxa = em.merge(taxaListTaxa);
                if (oldCodigoAdmOfTaxaListTaxa != null) {
                    oldCodigoAdmOfTaxaListTaxa.getTaxaList().remove(taxaListTaxa);
                    oldCodigoAdmOfTaxaListTaxa = em.merge(oldCodigoAdmOfTaxaListTaxa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administradora administradora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administradora persistentAdministradora = em.find(Administradora.class, administradora.getCodigoAdm());
            List<Regrascomissao> regrascomissaoListOld = persistentAdministradora.getRegrascomissaoList();
            List<Regrascomissao> regrascomissaoListNew = administradora.getRegrascomissaoList();
            List<Venda> vendaListOld = persistentAdministradora.getVendaList();
            List<Venda> vendaListNew = administradora.getVendaList();
            List<Taxa> taxaListOld = persistentAdministradora.getTaxaList();
            List<Taxa> taxaListNew = administradora.getTaxaList();
            List<Regrascomissao> attachedRegrascomissaoListNew = new ArrayList<Regrascomissao>();
            for (Regrascomissao regrascomissaoListNewRegrascomissaoToAttach : regrascomissaoListNew) {
                regrascomissaoListNewRegrascomissaoToAttach = em.getReference(regrascomissaoListNewRegrascomissaoToAttach.getClass(), regrascomissaoListNewRegrascomissaoToAttach.getCodigoRegra());
                attachedRegrascomissaoListNew.add(regrascomissaoListNewRegrascomissaoToAttach);
            }
            regrascomissaoListNew = attachedRegrascomissaoListNew;
            administradora.setRegrascomissaoList(regrascomissaoListNew);
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getCodigoVenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            administradora.setVendaList(vendaListNew);
            List<Taxa> attachedTaxaListNew = new ArrayList<Taxa>();
            for (Taxa taxaListNewTaxaToAttach : taxaListNew) {
                taxaListNewTaxaToAttach = em.getReference(taxaListNewTaxaToAttach.getClass(), taxaListNewTaxaToAttach.getCodigoTaxa());
                attachedTaxaListNew.add(taxaListNewTaxaToAttach);
            }
            taxaListNew = attachedTaxaListNew;
            administradora.setTaxaList(taxaListNew);
            administradora = em.merge(administradora);
            for (Regrascomissao regrascomissaoListOldRegrascomissao : regrascomissaoListOld) {
                if (!regrascomissaoListNew.contains(regrascomissaoListOldRegrascomissao)) {
                    regrascomissaoListOldRegrascomissao.setCodigoAdm(null);
                    regrascomissaoListOldRegrascomissao = em.merge(regrascomissaoListOldRegrascomissao);
                }
            }
            for (Regrascomissao regrascomissaoListNewRegrascomissao : regrascomissaoListNew) {
                if (!regrascomissaoListOld.contains(regrascomissaoListNewRegrascomissao)) {
                    Administradora oldCodigoAdmOfRegrascomissaoListNewRegrascomissao = regrascomissaoListNewRegrascomissao.getCodigoAdm();
                    regrascomissaoListNewRegrascomissao.setCodigoAdm(administradora);
                    regrascomissaoListNewRegrascomissao = em.merge(regrascomissaoListNewRegrascomissao);
                    if (oldCodigoAdmOfRegrascomissaoListNewRegrascomissao != null && !oldCodigoAdmOfRegrascomissaoListNewRegrascomissao.equals(administradora)) {
                        oldCodigoAdmOfRegrascomissaoListNewRegrascomissao.getRegrascomissaoList().remove(regrascomissaoListNewRegrascomissao);
                        oldCodigoAdmOfRegrascomissaoListNewRegrascomissao = em.merge(oldCodigoAdmOfRegrascomissaoListNewRegrascomissao);
                    }
                }
            }
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    vendaListOldVenda.setCodigoAdm(null);
                    vendaListOldVenda = em.merge(vendaListOldVenda);
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Administradora oldCodigoAdmOfVendaListNewVenda = vendaListNewVenda.getCodigoAdm();
                    vendaListNewVenda.setCodigoAdm(administradora);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldCodigoAdmOfVendaListNewVenda != null && !oldCodigoAdmOfVendaListNewVenda.equals(administradora)) {
                        oldCodigoAdmOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldCodigoAdmOfVendaListNewVenda = em.merge(oldCodigoAdmOfVendaListNewVenda);
                    }
                }
            }
            for (Taxa taxaListOldTaxa : taxaListOld) {
                if (!taxaListNew.contains(taxaListOldTaxa)) {
                    taxaListOldTaxa.setCodigoAdm(null);
                    taxaListOldTaxa = em.merge(taxaListOldTaxa);
                }
            }
            for (Taxa taxaListNewTaxa : taxaListNew) {
                if (!taxaListOld.contains(taxaListNewTaxa)) {
                    Administradora oldCodigoAdmOfTaxaListNewTaxa = taxaListNewTaxa.getCodigoAdm();
                    taxaListNewTaxa.setCodigoAdm(administradora);
                    taxaListNewTaxa = em.merge(taxaListNewTaxa);
                    if (oldCodigoAdmOfTaxaListNewTaxa != null && !oldCodigoAdmOfTaxaListNewTaxa.equals(administradora)) {
                        oldCodigoAdmOfTaxaListNewTaxa.getTaxaList().remove(taxaListNewTaxa);
                        oldCodigoAdmOfTaxaListNewTaxa = em.merge(oldCodigoAdmOfTaxaListNewTaxa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = administradora.getCodigoAdm();
                if (findAdministradora(id) == null) {
                    throw new NonexistentEntityException("The administradora with id " + id + " no longer exists.");
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
            Administradora administradora;
            try {
                administradora = em.getReference(Administradora.class, id);
                administradora.getCodigoAdm();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administradora with id " + id + " no longer exists.", enfe);
            }
            List<Regrascomissao> regrascomissaoList = administradora.getRegrascomissaoList();
            for (Regrascomissao regrascomissaoListRegrascomissao : regrascomissaoList) {
                regrascomissaoListRegrascomissao.setCodigoAdm(null);
                regrascomissaoListRegrascomissao = em.merge(regrascomissaoListRegrascomissao);
            }
            List<Venda> vendaList = administradora.getVendaList();
            for (Venda vendaListVenda : vendaList) {
                vendaListVenda.setCodigoAdm(null);
                vendaListVenda = em.merge(vendaListVenda);
            }
            List<Taxa> taxaList = administradora.getTaxaList();
            for (Taxa taxaListTaxa : taxaList) {
                taxaListTaxa.setCodigoAdm(null);
                taxaListTaxa = em.merge(taxaListTaxa);
            }
            em.remove(administradora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administradora> findAdministradoraEntities() {
        return findAdministradoraEntities(true, -1, -1);
    }

    public List<Administradora> findAdministradoraEntities(int maxResults, int firstResult) {
        return findAdministradoraEntities(false, maxResults, firstResult);
    }

    private List<Administradora> findAdministradoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administradora.class));
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

    public Administradora findAdministradora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administradora.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administradora> rt = cq.from(Administradora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
