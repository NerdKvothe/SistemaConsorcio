/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author joshua
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Administradora.findAll", query = "SELECT a FROM Administradora a"),
    @NamedQuery(name = "Administradora.findByCodigoAdm", query = "SELECT a FROM Administradora a WHERE a.codigoAdm = :codigoAdm"),
    @NamedQuery(name = "Administradora.findByNome", query = "SELECT a FROM Administradora a WHERE a.nome = :nome")})
public class Administradora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer codigoAdm;
    private String nome;
    @OneToMany(mappedBy = "codigoAdm")
    private List<Regrascomissao> regrascomissaoList;
    @OneToMany(mappedBy = "codigoAdm")
    private List<Venda> vendaList;
    @OneToMany(mappedBy = "codigoAdm")
    private List<Taxa> taxaList;

    public Administradora() {
    }

    public Administradora(Integer codigoAdm) {
        this.codigoAdm = codigoAdm;
    }

    public Integer getCodigoAdm() {
        return codigoAdm;
    }

    public void setCodigoAdm(Integer codigoAdm) {
        this.codigoAdm = codigoAdm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Regrascomissao> getRegrascomissaoList() {
        return regrascomissaoList;
    }

    public void setRegrascomissaoList(List<Regrascomissao> regrascomissaoList) {
        this.regrascomissaoList = regrascomissaoList;
    }

    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    public List<Taxa> getTaxaList() {
        return taxaList;
    }

    public void setTaxaList(List<Taxa> taxaList) {
        this.taxaList = taxaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAdm != null ? codigoAdm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administradora)) {
            return false;
        }
        Administradora other = (Administradora) object;
        if ((this.codigoAdm == null && other.codigoAdm != null) || (this.codigoAdm != null && !this.codigoAdm.equals(other.codigoAdm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Administradora[ codigoAdm=" + codigoAdm + " ]";
    }
    
}
