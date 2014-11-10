/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joshua
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Comissao.findAll", query = "SELECT c FROM Comissao c"),
    @NamedQuery(name = "Comissao.findByCodigoComissao", query = "SELECT c FROM Comissao c WHERE c.codigoComissao = :codigoComissao"),
    @NamedQuery(name = "Comissao.findByNroParcela", query = "SELECT c FROM Comissao c WHERE c.nroParcela = :nroParcela"),
    @NamedQuery(name = "Comissao.findByDtEfetivaRecebimento", query = "SELECT c FROM Comissao c WHERE c.dtEfetivaRecebimento = :dtEfetivaRecebimento"),
    @NamedQuery(name = "Comissao.findByVlrEfetivoRecebimento", query = "SELECT c FROM Comissao c WHERE c.vlrEfetivoRecebimento = :vlrEfetivoRecebimento"),
    @NamedQuery(name = "Comissao.findByDtPrevista", query = "SELECT c FROM Comissao c WHERE c.dtPrevista = :dtPrevista"),
    @NamedQuery(name = "Comissao.findByVlrPrevisto", query = "SELECT c FROM Comissao c WHERE c.vlrPrevisto = :vlrPrevisto"),
    @NamedQuery(name = "Comissao.findByTipoComissao", query = "SELECT c FROM Comissao c WHERE c.tipoComissao = :tipoComissao"),
    @NamedQuery(name = "Comissao.findByDtCancelamentoEstorno", query = "SELECT c FROM Comissao c WHERE c.dtCancelamentoEstorno = :dtCancelamentoEstorno")})
public class Comissao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer codigoComissao;
    private Integer nroParcela;
    @Temporal(TemporalType.DATE)
    private Date dtEfetivaRecebimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double vlrEfetivoRecebimento;
    @Temporal(TemporalType.DATE)
    private Date dtPrevista;
    private Double vlrPrevisto;
    private Integer tipoComissao;
    @Temporal(TemporalType.DATE)
    private Date dtCancelamentoEstorno;
    @OneToMany(mappedBy = "codigoComissao")
    private List<Regrascomissao> regrascomissaoList;
    @JoinColumn(name = "codigoVenda", referencedColumnName = "codigoVenda")
    @ManyToOne
    private Venda codigoVenda;

    public Comissao() {
    }

    public Comissao(Integer codigoComissao) {
        this.codigoComissao = codigoComissao;
    }

    public Integer getCodigoComissao() {
        return codigoComissao;
    }

    public void setCodigoComissao(Integer codigoComissao) {
        this.codigoComissao = codigoComissao;
    }

    public Integer getNroParcela() {
        return nroParcela;
    }

    public void setNroParcela(Integer nroParcela) {
        this.nroParcela = nroParcela;
    }

    public Date getDtEfetivaRecebimento() {
        return dtEfetivaRecebimento;
    }

    public void setDtEfetivaRecebimento(Date dtEfetivaRecebimento) {
        this.dtEfetivaRecebimento = dtEfetivaRecebimento;
    }

    public Double getVlrEfetivoRecebimento() {
        return vlrEfetivoRecebimento;
    }

    public void setVlrEfetivoRecebimento(Double vlrEfetivoRecebimento) {
        this.vlrEfetivoRecebimento = vlrEfetivoRecebimento;
    }

    public Date getDtPrevista() {
        return dtPrevista;
    }

    public void setDtPrevista(Date dtPrevista) {
        this.dtPrevista = dtPrevista;
    }

    public Double getVlrPrevisto() {
        return vlrPrevisto;
    }

    public void setVlrPrevisto(Double vlrPrevisto) {
        this.vlrPrevisto = vlrPrevisto;
    }

    public Integer getTipoComissao() {
        return tipoComissao;
    }

    public void setTipoComissao(Integer tipoComissao) {
        this.tipoComissao = tipoComissao;
    }

    public Date getDtCancelamentoEstorno() {
        return dtCancelamentoEstorno;
    }

    public void setDtCancelamentoEstorno(Date dtCancelamentoEstorno) {
        this.dtCancelamentoEstorno = dtCancelamentoEstorno;
    }

    public List<Regrascomissao> getRegrascomissaoList() {
        return regrascomissaoList;
    }

    public void setRegrascomissaoList(List<Regrascomissao> regrascomissaoList) {
        this.regrascomissaoList = regrascomissaoList;
    }

    public Venda getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(Venda codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoComissao != null ? codigoComissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comissao)) {
            return false;
        }
        Comissao other = (Comissao) object;
        if ((this.codigoComissao == null && other.codigoComissao != null) || (this.codigoComissao != null && !this.codigoComissao.equals(other.codigoComissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Comissao[ codigoComissao=" + codigoComissao + " ]";
    }
    
}
