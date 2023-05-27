/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.domain.cdi;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author max
 */
class PagoAdeudos implements Serializable{
     private String  id;
    private Integer idPago;
    private Date    fechaPago;
    
    private Integer idTipoCuota;
    private String  nombreTipo;
    
    private Integer anio;
    private Integer mes;
    
    private Integer idmedioPago;
    private String  descMedioPagoSocio;
    private String  descMedioPagoPago;

    private Integer idusuario;
    private String  nombreusuario;
    
    private Integer idSocio;
    private Integer  nroSocio;
    private String  nombre;
    
    private Pago pago;
    private Integer importeCuota;
    private Integer mesesAdeudados;

    public PagoAdeudos() {
    }

    
    public PagoAdeudos(String id,Pago pago, Integer mesesAdeudados) {
        this.id = id;
        this.pago = pago;
        this.mesesAdeudados = mesesAdeudados;
    }

   
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
            
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Integer getMesesAdeudados() {
        return mesesAdeudados;
    }

    public void setMesesAdeudados(Integer mesesAdeudados) {
        this.mesesAdeudados = mesesAdeudados;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdTipoCuota() {
        return idTipoCuota;
    }

    public void setIdTipoCuota(Integer idTipoCuota) {
        this.idTipoCuota = idTipoCuota;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getIdmedioPago() {
        return idmedioPago;
    }

    public void setIdmedioPago(Integer idmedioPago) {
        this.idmedioPago = idmedioPago;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public Integer getNroSocio() {
        return nroSocio;
    }

    public void setNroSocio(Integer nroSocio) {
        this.nroSocio = nroSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescMedioPagoSocio() {
        return descMedioPagoSocio;
    }

    public void setDescMedioPagoSocio(String descMedioPagoSocio) {
        this.descMedioPagoSocio = descMedioPagoSocio;
    }

    public String getDescMedioPagoPago() {
        return descMedioPagoPago;
    }

    public void setDescMedioPagoPago(String descMedioPagoPago) {
        this.descMedioPagoPago = descMedioPagoPago;
    }

    public Integer getImporteCuota() {
        return importeCuota;
    }

    public void setImporteCuota(Integer importeCuota) {
        this.importeCuota = importeCuota;
    }
    
    
    
    
    
    @Override
    public boolean equals(Object obj){
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final PagoAdeudos other = (PagoAdeudos) obj;
        if ((this.id == null)? (other.id != null) : !this.id.equals( other.id)) {
            return false;
        }
        return true;
    }
    
}
