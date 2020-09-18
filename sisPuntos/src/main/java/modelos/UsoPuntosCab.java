/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián
 */
@Entity
@Table(name = "uso_puntos_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoPuntosCab.findAll", query = "SELECT u FROM UsoPuntosCab u")
    , @NamedQuery(name = "UsoPuntosCab.findById", query = "SELECT u FROM UsoPuntosCab u WHERE u.id = :id")
    , @NamedQuery(name = "UsoPuntosCab.findByPuntajeUtilizado", query = "SELECT u FROM UsoPuntosCab u WHERE u.puntajeUtilizado = :puntajeUtilizado")
    , @NamedQuery(name = "UsoPuntosCab.findByFecha", query = "SELECT u FROM UsoPuntosCab u WHERE u.fecha = :fecha")})
public class UsoPuntosCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @JoinColumn(name = "id_concepto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Concepto idConcepto;

    public UsoPuntosCab() {
    }

    public UsoPuntosCab(Integer id) {
        this.id = id;
    }

    public UsoPuntosCab(Integer id, int puntajeUtilizado) {
        this.id = id;
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Concepto getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Concepto idConcepto) {
        this.idConcepto = idConcepto;
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
        if (!(object instanceof UsoPuntosCab)) {
            return false;
        }
        UsoPuntosCab other = (UsoPuntosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.UsoPuntosCab[ id=" + id + " ]";
    }
    
}
