/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián
 */
@Entity
@Table(name = "uso_puntos_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoPuntosDet.findAll", query = "SELECT u FROM UsoPuntosDet u")
    , @NamedQuery(name = "UsoPuntosDet.findByIdUsoPuntosCab", query = "SELECT u FROM UsoPuntosDet u WHERE u.idUsoPuntosCab = :idUsoPuntosCab")
    , @NamedQuery(name = "UsoPuntosDet.findByPuntajeUtilizado", query = "SELECT u FROM UsoPuntosDet u WHERE u.puntajeUtilizado = :puntajeUtilizado")})
public class UsoPuntosDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_uso_puntos_cab")
    private Integer idUsoPuntosCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;
    @JoinColumn(name = "id_bolsa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BolsaPuntos idBolsa;
    @JoinColumn(name = "id_uso_puntos_cab", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private UsoPuntosCab usoPuntosCab;

    public UsoPuntosDet() {
    }

    public UsoPuntosDet(Integer idUsoPuntosCab) {
        this.idUsoPuntosCab = idUsoPuntosCab;
    }

    public UsoPuntosDet(Integer idUsoPuntosCab, int puntajeUtilizado) {
        this.idUsoPuntosCab = idUsoPuntosCab;
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getIdUsoPuntosCab() {
        return idUsoPuntosCab;
    }

    public void setIdUsoPuntosCab(Integer idUsoPuntosCab) {
        this.idUsoPuntosCab = idUsoPuntosCab;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public BolsaPuntos getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(BolsaPuntos idBolsa) {
        this.idBolsa = idBolsa;
    }

    public UsoPuntosCab getUsoPuntosCab() {
        return usoPuntosCab;
    }

    public void setUsoPuntosCab(UsoPuntosCab usoPuntosCab) {
        this.usoPuntosCab = usoPuntosCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsoPuntosCab != null ? idUsoPuntosCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsoPuntosDet)) {
            return false;
        }
        UsoPuntosDet other = (UsoPuntosDet) object;
        if ((this.idUsoPuntosCab == null && other.idUsoPuntosCab != null) || (this.idUsoPuntosCab != null && !this.idUsoPuntosCab.equals(other.idUsoPuntosCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.UsoPuntosDet[ idUsoPuntosCab=" + idUsoPuntosCab + " ]";
    }
    
}
