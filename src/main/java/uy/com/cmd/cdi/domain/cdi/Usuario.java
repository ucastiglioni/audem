/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.domain.cdi;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "usuario")
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
    
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    
    @Column(name = "Clave", nullable = false)
    private String clave;
    
    @Column(name = "eMail", nullable = false)
    private String email;
    
    @Column(name = "Estado", nullable = false)
    private Boolean estado;
    

    public Usuario() {
        this.id = 0;
        this.rol = new Rol();
    }

    public Usuario(Rol rol, String nombre, String clave, String email) {
        this.rol = rol;
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
        
    }

    public Usuario(Rol rol, String nombre, String clave, String email, Boolean estado, String usuariocreacion) {
        this.rol = rol;
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
        this.estado = estado;
        
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
