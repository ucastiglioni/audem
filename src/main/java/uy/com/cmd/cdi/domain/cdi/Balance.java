/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.domain.cdi;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author max
 */
public class Balance implements Serializable{
    
    private String cuenta;  
    private Long importe;
    private List<PagoAdeudos> pagoAdeudos;  
    
    
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Long getImporte() {
        return importe;
    }

    public void setImporte(Long importe) {
        this.importe = importe;
    }

    public List<PagoAdeudos> getPagoAdeudos() {
        return pagoAdeudos;
    }

    public void setPagoAdeudos(List<PagoAdeudos> pagoAdeudos) {
        this.pagoAdeudos = pagoAdeudos;
    }
  
}
