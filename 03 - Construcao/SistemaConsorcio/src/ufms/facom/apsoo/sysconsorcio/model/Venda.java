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
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findByCodigoVenda", query = "SELECT v FROM Venda v WHERE v.codigoVenda = :codigoVenda"),
    @NamedQuery(name = "Venda.findByDataCadastro", query = "SELECT v FROM Venda v WHERE v.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "Venda.findByNroContrato", query = "SELECT v FROM Venda v WHERE v.nroContrato = :nroContrato"),
    @NamedQuery(name = "Venda.findByDtIniVigencia", query = "SELECT v FROM Venda v WHERE v.dtIniVigencia = :dtIniVigencia"),
    @NamedQuery(name = "Venda.findByQtdParcelas", query = "SELECT v FROM Venda v WHERE v.qtdParcelas = :qtdParcelas"),
    @NamedQuery(name = "Venda.findByDtParcEntrada", query = "SELECT v FROM Venda v WHERE v.dtParcEntrada = :dtParcEntrada"),
    @NamedQuery(name = "Venda.findByVlrParcEntrada", query = "SELECT v FROM Venda v WHERE v.vlrParcEntrada = :vlrParcEntrada"),
    @NamedQuery(name = "Venda.findByVlrBem", query = "SELECT v FROM Venda v WHERE v.vlrBem = :vlrBem"),
    @NamedQuery(name = "Venda.findByObservacao", query = "SELECT v FROM Venda v WHERE v.observacao = :observacao"),
    @NamedQuery(name = "Venda.findByGrupoConsorcio", query = "SELECT v FROM Venda v WHERE v.grupoConsorcio = :grupoConsorcio"),
    @NamedQuery(name = "Venda.findByCotaConsorcio", query = "SELECT v FROM Venda v WHERE v.cotaConsorcio = :cotaConsorcio")})
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer codigoVenda;
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    private String nroContrato;
    @Temporal(TemporalType.DATE)
    private Date dtIniVigencia;
    private Integer qtdParcelas;
    @Temporal(TemporalType.DATE)
    private Date dtParcEntrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double vlrParcEntrada;
    private Double vlrBem;
    private String observacao;
    private Integer grupoConsorcio;
    private Integer cotaConsorcio;
    @JoinColumn(name = "codigoModelo", referencedColumnName = "codigoModelo")
    @ManyToOne
    private Modeloveiculo codigoModelo;
    @JoinColumn(name = "codigoVendedor", referencedColumnName = "codigoVendedor")
    @ManyToOne
    private Vendedor codigoVendedor;
    @JoinColumn(name = "codigoAdm", referencedColumnName = "codigoAdm")
    @ManyToOne
    private Administradora codigoAdm;
    @JoinColumn(name = "codigoCliente", referencedColumnName = "codigoCliente")
    @ManyToOne
    private Cliente codigoCliente;
    @OneToMany(mappedBy = "codigoVenda")
    private List<Comissao> comissaoList;

    public Venda() {
    }

    public Venda(Integer codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public Integer getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(Integer codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    public Date getDtIniVigencia() {
        return dtIniVigencia;
    }

    public void setDtIniVigencia(Date dtIniVigencia) {
        this.dtIniVigencia = dtIniVigencia;
    }

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Date getDtParcEntrada() {
        return dtParcEntrada;
    }

    public void setDtParcEntrada(Date dtParcEntrada) {
        this.dtParcEntrada = dtParcEntrada;
    }

    public Double getVlrParcEntrada() {
        return vlrParcEntrada;
    }

    public void setVlrParcEntrada(Double vlrParcEntrada) {
        this.vlrParcEntrada = vlrParcEntrada;
    }

    public Double getVlrBem() {
        return vlrBem;
    }

    public void setVlrBem(Double vlrBem) {
        this.vlrBem = vlrBem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getGrupoConsorcio() {
        return grupoConsorcio;
    }

    public void setGrupoConsorcio(Integer grupoConsorcio) {
        this.grupoConsorcio = grupoConsorcio;
    }

    public Integer getCotaConsorcio() {
        return cotaConsorcio;
    }

    public void setCotaConsorcio(Integer cotaConsorcio) {
        this.cotaConsorcio = cotaConsorcio;
    }

    public Modeloveiculo getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(Modeloveiculo codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public Vendedor getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(Vendedor codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public Administradora getCodigoAdm() {
        return codigoAdm;
    }

    public void setCodigoAdm(Administradora codigoAdm) {
        this.codigoAdm = codigoAdm;
    }

    public Cliente getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Cliente codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<Comissao> getComissaoList() {
        return comissaoList;
    }

    public void setComissaoList(List<Comissao> comissaoList) {
        this.comissaoList = comissaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVenda != null ? codigoVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.codigoVenda == null && other.codigoVenda != null) || (this.codigoVenda != null && !this.codigoVenda.equals(other.codigoVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Venda[ codigoVenda=" + codigoVenda + " ]";
    }
    
}
