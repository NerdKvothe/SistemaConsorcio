/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author joshua
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Taxa.findAll", query = "SELECT t FROM Taxa t"),
    @NamedQuery(name = "Taxa.findByCodigoTaxa", query = "SELECT t FROM Taxa t WHERE t.codigoTaxa = :codigoTaxa"),
    @NamedQuery(name = "Taxa.findByPercentualTaxa", query = "SELECT t FROM Taxa t WHERE t.percentualTaxa = :percentualTaxa")})
public class Taxa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer codigoTaxa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double percentualTaxa;
    @JoinColumn(name = "codigoAdm", referencedColumnName = "codigoAdm")
    @ManyToOne
    private Administradora codigoAdm;
    @JoinColumn(name = "codigoTpTaxa", referencedColumnName = "codigoTpTaxa")
    @ManyToOne
    private Tipotaxa codigoTpTaxa;

    public Taxa() {
    }

    public Taxa(Integer codigoTaxa) {
        this.codigoTaxa = codigoTaxa;
    }

    public Integer getCodigoTaxa() {
        return codigoTaxa;
    }

    public void setCodigoTaxa(Integer codigoTaxa) {
        this.codigoTaxa = codigoTaxa;
    }

    public Double getPercentualTaxa() {
        return percentualTaxa;
    }

    public void setPercentualTaxa(Double percentualTaxa) {
        this.percentualTaxa = percentualTaxa;
    }

    public Administradora getCodigoAdm() {
        return codigoAdm;
    }

    public void setCodigoAdm(Administradora codigoAdm) {
        this.codigoAdm = codigoAdm;
    }

    public Tipotaxa getCodigoTpTaxa() {
        return codigoTpTaxa;
    }

    public void setCodigoTpTaxa(Tipotaxa codigoTpTaxa) {
        this.codigoTpTaxa = codigoTpTaxa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTaxa != null ? codigoTaxa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxa)) {
            return false;
        }
        Taxa other = (Taxa) object;
        if ((this.codigoTaxa == null && other.codigoTaxa != null) || (this.codigoTaxa != null && !this.codigoTaxa.equals(other.codigoTaxa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Taxa[ codigoTaxa=" + codigoTaxa + " ]";
    }
    
}
