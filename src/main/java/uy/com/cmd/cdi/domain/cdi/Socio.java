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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author max
 */
@Entity
@Table(name = "socio")
public class Socio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private int numeroSocio;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private Date fechaInscripcion;

    @Column
    private int ci;

    @Column
    private Date fechaNacimiento;

    @Column
    private String domicilio;

    @Column
    private String celular;

    @Column
    private String celularEmergencia;

    @Column
    private String emergenciaMovil;

    @Column
    private String mutualista;

    @Column
    private Date vencimientoCSalud;

    @Column
    String email;

    @Column(name = "foto")
    byte[] foto;

    @Column
    String observaciones;

    @ManyToOne
    @JoinColumn(name = "idTipoCuota")
    TipoCuota tipoCuota;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idcestado")
    Estado estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelularEmergencia() {
        return celularEmergencia;
    }

    public void setCelularEmergencia(String celularEmergencia) {
        this.celularEmergencia = celularEmergencia;
    }

    public String getEmergenciaMovil() {
        return emergenciaMovil;
    }

    public void setEmergenciaMovil(String emergenciaMovil) {
        this.emergenciaMovil = emergenciaMovil;
    }

    public String getMutualista() {
        return mutualista;
    }

    public void setMutualista(String mutualista) {
        this.mutualista = mutualista;
    }

    public Date getVencimientoCSalud() {
        return vencimientoCSalud;
    }

    public void setVencimientoCSalud(Date vencimientoCSalud) {
        this.vencimientoCSalud = vencimientoCSalud;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TipoCuota getTipoCuota() {
        return tipoCuota;
    }

    public void setTipoCuota(TipoCuota tipoCuota) {
        this.tipoCuota = tipoCuota;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Socio other = (Socio) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "Socio{" + "id=" + id + '}';
//    }

}
