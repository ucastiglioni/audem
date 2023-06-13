/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.domain.cdi;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author max
 */
@Entity
@Table(name = "valortipocuota")
public class  ValorTipoCuota implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name="idTipoCuota")
    private Integer idTipoCuota;
    
    @Column(name="importe")
    private Integer importe;

    @Column(name="vigencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vigencia;
        
    public ValorTipoCuota() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTipoCuota() {
        return idTipoCuota;
    }

    public void setIdTipoCuota(Integer idTipoCuota) {
        this.idTipoCuota = idTipoCuota;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValorTipoCuota other = (ValorTipoCuota) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    
}