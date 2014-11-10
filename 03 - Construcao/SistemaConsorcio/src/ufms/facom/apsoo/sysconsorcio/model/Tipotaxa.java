/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author joshua
 */
@Entity
@Table(name = "tipotaxa")
@NamedQueries({
    @NamedQuery(name = "Tipotaxa.findAll", query = "SELECT t FROM Tipotaxa t"),
    @NamedQuery(name = "Tipotaxa.findByCodigoTpTaxa", query = "SELECT t FROM Tipotaxa t WHERE t.codigoTpTaxa = :codigoTpTaxa"),
    @NamedQuery(name = "Tipotaxa.findByNome", query = "SELECT t FROM Tipotaxa t WHERE t.nome = :nome")})
public class Tipotaxa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTpTaxa")
    private Integer codigoTpTaxa;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "codigoTpTaxa")
    private List<Taxa> taxaList;

    public Tipotaxa() {
    }

    public Tipotaxa(Integer codigoTpTaxa) {
        this.codigoTpTaxa = codigoTpTaxa;
    }

    public Integer getCodigoTpTaxa() {
        return codigoTpTaxa;
    }

    public void setCodigoTpTaxa(Integer codigoTpTaxa) {
        this.codigoTpTaxa = codigoTpTaxa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        hash += (codigoTpTaxa != null ? codigoTpTaxa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotaxa)) {
            return false;
        }
        Tipotaxa other = (Tipotaxa) object;
        if ((this.codigoTpTaxa == null && other.codigoTpTaxa != null) || (this.codigoTpTaxa != null && !this.codigoTpTaxa.equals(other.codigoTpTaxa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Tipotaxa[ codigoTpTaxa=" + codigoTpTaxa + " ]";
    }
    
}
