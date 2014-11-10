/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joshua
 */
@Entity
@Table(name = "regrascomissao")
@NamedQueries({
    @NamedQuery(name = "Regrascomissao.findAll", query = "SELECT r FROM Regrascomissao r"),
    @NamedQuery(name = "Regrascomissao.findByCodigoRegra", query = "SELECT r FROM Regrascomissao r WHERE r.codigoRegra = :codigoRegra"),
    @NamedQuery(name = "Regrascomissao.findByPercentual", query = "SELECT r FROM Regrascomissao r WHERE r.percentual = :percentual"),
    @NamedQuery(name = "Regrascomissao.findByDtIniVigenciaComissao", query = "SELECT r FROM Regrascomissao r WHERE r.dtIniVigenciaComissao = :dtIniVigenciaComissao"),
    @NamedQuery(name = "Regrascomissao.findByDtFimVigenciaComissao", query = "SELECT r FROM Regrascomissao r WHERE r.dtFimVigenciaComissao = :dtFimVigenciaComissao"),
    @NamedQuery(name = "Regrascomissao.findByParcelamentoComissao", query = "SELECT r FROM Regrascomissao r WHERE r.parcelamentoComissao = :parcelamentoComissao"),
    @NamedQuery(name = "Regrascomissao.findByFormaEstorno", query = "SELECT r FROM Regrascomissao r WHERE r.formaEstorno = :formaEstorno")})
public class Regrascomissao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoRegra")
    private Integer codigoRegra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentual")
    private Double percentual;
    @Column(name = "dtIniVigenciaComissao")
    @Temporal(TemporalType.DATE)
    private Date dtIniVigenciaComissao;
    @Column(name = "dtFimVigenciaComissao")
    @Temporal(TemporalType.DATE)
    private Date dtFimVigenciaComissao;
    @Column(name = "parcelamentoComissao")
    private Integer parcelamentoComissao;
    @Column(name = "formaEstorno")
    private String formaEstorno;
    @JoinColumn(name = "codigoAdm", referencedColumnName = "codigoAdm")
    @ManyToOne
    private Administradora codigoAdm;
    @JoinColumn(name = "codigoComissao", referencedColumnName = "codigoComissao")
    @ManyToOne
    private Comissao codigoComissao;

    public Regrascomissao() {
    }

    public Regrascomissao(Integer codigoRegra) {
        this.codigoRegra = codigoRegra;
    }

    public Integer getCodigoRegra() {
        return codigoRegra;
    }

    public void setCodigoRegra(Integer codigoRegra) {
        this.codigoRegra = codigoRegra;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public Date getDtIniVigenciaComissao() {
        return dtIniVigenciaComissao;
    }

    public void setDtIniVigenciaComissao(Date dtIniVigenciaComissao) {
        this.dtIniVigenciaComissao = dtIniVigenciaComissao;
    }

    public Date getDtFimVigenciaComissao() {
        return dtFimVigenciaComissao;
    }

    public void setDtFimVigenciaComissao(Date dtFimVigenciaComissao) {
        this.dtFimVigenciaComissao = dtFimVigenciaComissao;
    }

    public Integer getParcelamentoComissao() {
        return parcelamentoComissao;
    }

    public void setParcelamentoComissao(Integer parcelamentoComissao) {
        this.parcelamentoComissao = parcelamentoComissao;
    }

    public String getFormaEstorno() {
        return formaEstorno;
    }

    public void setFormaEstorno(String formaEstorno) {
        this.formaEstorno = formaEstorno;
    }

    public Administradora getCodigoAdm() {
        return codigoAdm;
    }

    public void setCodigoAdm(Administradora codigoAdm) {
        this.codigoAdm = codigoAdm;
    }

    public Comissao getCodigoComissao() {
        return codigoComissao;
    }

    public void setCodigoComissao(Comissao codigoComissao) {
        this.codigoComissao = codigoComissao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRegra != null ? codigoRegra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regrascomissao)) {
            return false;
        }
        Regrascomissao other = (Regrascomissao) object;
        if ((this.codigoRegra == null && other.codigoRegra != null) || (this.codigoRegra != null && !this.codigoRegra.equals(other.codigoRegra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Regrascomissao[ codigoRegra=" + codigoRegra + " ]";
    }
    
}
