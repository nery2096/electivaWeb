/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián
 */
@Entity
@Table(name = "reglas_conceptos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReglasConceptos.findAll", query = "SELECT r FROM ReglasConceptos r")
    , @NamedQuery(name = "ReglasConceptos.findById", query = "SELECT r FROM ReglasConceptos r WHERE r.id = :id")
    , @NamedQuery(name = "ReglasConceptos.findByLimiteInferior", query = "SELECT r FROM ReglasConceptos r WHERE r.limiteInferior = :limiteInferior")
    , @NamedQuery(name = "ReglasConceptos.findByLimiteSuperior", query = "SELECT r FROM ReglasConceptos r WHERE r.limiteSuperior = :limiteSuperior")
    , @NamedQuery(name = "ReglasConceptos.findByMontoEquivalencia", query = "SELECT r FROM ReglasConceptos r WHERE r.montoEquivalencia = :montoEquivalencia")})
public class ReglasConceptos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "limite_inferior")
    private int limiteInferior;
    @Basic(optional = false)
    @NotNull
    @Column(name = "limite_superior")
    private int limiteSuperior;
    @Column(name = "monto_equivalencia")
    private BigInteger montoEquivalencia;

    public ReglasConceptos() {
    }

    public ReglasConceptos(Integer id) {
        this.id = id;
    }

    public ReglasConceptos(Integer id, int limiteInferior, int limiteSuperior) {
        this.id = id;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(int limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(int limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public BigInteger getMontoEquivalencia() {
        return montoEquivalencia;
    }

    public void setMontoEquivalencia(BigInteger montoEquivalencia) {
        this.montoEquivalencia = montoEquivalencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReglasConceptos)) {
            return false;
        }
        ReglasConceptos other = (ReglasConceptos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.ReglasConceptos[ id=" + id + " ]";
    }
    
}
