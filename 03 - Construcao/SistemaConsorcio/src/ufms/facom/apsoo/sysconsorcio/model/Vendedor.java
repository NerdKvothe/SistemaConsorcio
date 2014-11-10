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
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findByCodigoVendedor", query = "SELECT v FROM Vendedor v WHERE v.codigoVendedor = :codigoVendedor"),
    @NamedQuery(name = "Vendedor.findByNome", query = "SELECT v FROM Vendedor v WHERE v.nome = :nome"),
    @NamedQuery(name = "Vendedor.findByDtInicio", query = "SELECT v FROM Vendedor v WHERE v.dtInicio = :dtInicio"),
    @NamedQuery(name = "Vendedor.findByDtFim", query = "SELECT v FROM Vendedor v WHERE v.dtFim = :dtFim")})
public class Vendedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer codigoVendedor;
    private String nome;
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    @Temporal(TemporalType.DATE)
    private Date dtFim;
    @JoinColumn(name = "codigoSbPtoVenda", referencedColumnName = "codigoSbPtoVenda")
    @ManyToOne
    private Subpontovenda codigoSbPtoVenda;
    @OneToMany(mappedBy = "codigoVendedor")
    private List<Venda> vendaList;

    public Vendedor() {
    }

    public Vendedor(Integer codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public Integer getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(Integer codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public Subpontovenda getCodigoSbPtoVenda() {
        return codigoSbPtoVenda;
    }

    public void setCodigoSbPtoVenda(Subpontovenda codigoSbPtoVenda) {
        this.codigoSbPtoVenda = codigoSbPtoVenda;
    }

    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVendedor != null ? codigoVendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.codigoVendedor == null && other.codigoVendedor != null) || (this.codigoVendedor != null && !this.codigoVendedor.equals(other.codigoVendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Vendedor[ codigoVendedor=" + codigoVendedor + " ]";
    }
    
}
