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
    @NamedQuery(name = "Pontovenda.findAll", query = "SELECT p FROM Pontovenda p"),
    @NamedQuery(name = "Pontovenda.findByCodigoPtoVenda", query = "SELECT p FROM Pontovenda p WHERE p.codigoPtoVenda = :codigoPtoVenda"),
    @NamedQuery(name = "Pontovenda.findByNome", query = "SELECT p FROM Pontovenda p WHERE p.nome = :nome")})
public class Pontovenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer codigoPtoVenda;
    private String nome;
    @OneToMany(mappedBy = "codigoPtoVenda")
    private List<Subpontovenda> subpontovendaList;

    public Pontovenda() {
    }

    public Pontovenda(Integer codigoPtoVenda) {
        this.codigoPtoVenda = codigoPtoVenda;
    }

    public Integer getCodigoPtoVenda() {
        return codigoPtoVenda;
    }

    public void setCodigoPtoVenda(Integer codigoPtoVenda) {
        this.codigoPtoVenda = codigoPtoVenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Subpontovenda> getSubpontovendaList() {
        return subpontovendaList;
    }

    public void setSubpontovendaList(List<Subpontovenda> subpontovendaList) {
        this.subpontovendaList = subpontovendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPtoVenda != null ? codigoPtoVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pontovenda)) {
            return false;
        }
        Pontovenda other = (Pontovenda) object;
        if ((this.codigoPtoVenda == null && other.codigoPtoVenda != null) || (this.codigoPtoVenda != null && !this.codigoPtoVenda.equals(other.codigoPtoVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ufms.facom.apsoo.sysconsorcio.model.Pontovenda[ codigoPtoVenda=" + codigoPtoVenda + " ]";
    }
    
}
