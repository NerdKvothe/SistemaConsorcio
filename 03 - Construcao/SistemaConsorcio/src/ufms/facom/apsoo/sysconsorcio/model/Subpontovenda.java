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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author joshua
 */
@Entity
@Table(name = "subpontovenda")
@NamedQueries({
    @NamedQuery(name = "Subpontovenda.findAll", query = "SELECT s FROM Subpontovenda s"),
    @NamedQuery(name = "Subpontovenda.findByCodigoSbPtoVenda", query = "SELECT s FROM Subpontovenda s WHERE s.codigoSbPtoVenda = :codigoSbPtoVenda"),
    @NamedQuery(name = "Subpontovenda.findByNome", query = "SELECT s FROM Subpontovenda s WHERE s.nome = :nome")})
public class Subpontovenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoSbPtoVenda")
    private Integer codigoSbPtoVenda;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "codigoSbPtoVenda")
    private List<Vendedor> vendedorList;
    @JoinColumn(name = "codigoPtoVenda", referencedColumnName = "codigoPtoVenda")
    @ManyToOne
    private Pontovenda codigoPtoVenda;

    public Subpontovenda() {
    }

    public Subpontovenda(Integer codigoSbPtoVenda) {
        this.codigoSbPtoVenda = codigoSbPtoVenda;
    }

    public Integer getCodigoSbPtoVenda() {
        return codigoSbPtoVenda;
    }

    public void setCodigoSbPtoVenda(Integer codigoSbPtoVenda) {
        this.codigoSbPtoVenda = codigoSbPtoVenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Vendedor> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(List<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public Pontovenda getCodigoPtoVenda() {
        return codigoPtoVenda;
    }

    public void setCodigoPtoVenda(Pontovenda codigoPtoVenda) {
        this.codigoPtoVenda = codigoPtoVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSbPtoVenda != null ? codigoSbPtoVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subpontovenda)) {
            return false;
        }
        Subpontovenda other = (Subpontovenda) object;
        if ((this.codigoSbPtoVenda == null && other.codigoSbPtoVenda != null) || (this.codigoSbPtoVenda != null && !this.codigoSbPtoVenda.equals(other.codigoSbPtoVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Subpontovenda[ codigoSbPtoVenda=" + codigoSbPtoVenda + " ]";
    }
    
}
