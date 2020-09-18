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
@Table(name = "vencimientos_puntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VencimientosPuntos.findAll", query = "SELECT v FROM VencimientosPuntos v")
    , @NamedQuery(name = "VencimientosPuntos.findById", query = "SELECT v FROM VencimientosPuntos v WHERE v.id = :id")
    , @NamedQuery(name = "VencimientosPuntos.findByFechaInicio", query = "SELECT v FROM VencimientosPuntos v WHERE v.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "VencimientosPuntos.findByDiasDuracionPuntos", query = "SELECT v FROM VencimientosPuntos v WHERE v.diasDuracionPuntos = :diasDuracionPuntos")
    , @NamedQuery(name = "VencimientosPuntos.findByFechaFin", query = "SELECT v FROM VencimientosPuntos v WHERE v.fechaFin = :fechaFin")})
public class VencimientosPuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias_duracion_puntos")
    private int diasDuracionPuntos;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaFin;

    public VencimientosPuntos() {
    }

    public VencimientosPuntos(Integer id) {
        this.id = id;
    }

    public VencimientosPuntos(Integer id, Date fechaInicio, int diasDuracionPuntos) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.diasDuracionPuntos = diasDuracionPuntos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDiasDuracionPuntos() {
        return diasDuracionPuntos;
    }

    public void setDiasDuracionPuntos(int diasDuracionPuntos) {
        this.diasDuracionPuntos = diasDuracionPuntos;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
        if (!(object instanceof VencimientosPuntos)) {
            return false;
        }
        VencimientosPuntos other = (VencimientosPuntos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.VencimientosPuntos[ id=" + id + " ]";
    }
    
}
