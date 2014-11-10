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
@Table(name = "modeloveiculo")
@NamedQueries({
    @NamedQuery(name = "Modeloveiculo.findAll", query = "SELECT m FROM Modeloveiculo m"),
    @NamedQuery(name = "Modeloveiculo.findByCodigoModelo", query = "SELECT m FROM Modeloveiculo m WHERE m.codigoModelo = :codigoModelo"),
    @NamedQuery(name = "Modeloveiculo.findByDescricao", query = "SELECT m FROM Modeloveiculo m WHERE m.descricao = :descricao"),
    @NamedQuery(name = "Modeloveiculo.findByMarca", query = "SELECT m FROM Modeloveiculo m WHERE m.marca = :marca")})
public class Modeloveiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoModelo")
    private Integer codigoModelo;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "marca")
    private String marca;
    @OneToMany(mappedBy = "codigoModelo")
    private List<Venda> vendaList;

    public Modeloveiculo() {
    }

    public Modeloveiculo(Integer codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public Integer getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(Integer codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
        hash += (codigoModelo != null ? codigoModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modeloveiculo)) {
            return false;
        }
        Modeloveiculo other = (Modeloveiculo) object;
        if ((this.codigoModelo == null && other.codigoModelo != null) || (this.codigoModelo != null && !this.codigoModelo.equals(other.codigoModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Modeloveiculo[ codigoModelo=" + codigoModelo + " ]";
    }
    
}
