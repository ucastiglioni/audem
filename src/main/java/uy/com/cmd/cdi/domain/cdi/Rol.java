/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.domain.cdi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author max
 */
@Entity
@Table(name = "rol")
class Rol implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
     private int id;
    
    @Column(name="nombre", nullable=false, length=30)
     private String nombre;
    
    @Column(name="descripcion", nullable=false, length=100)
     private String descripcion;
    
    @Column(name="estado", length=100)
     private Boolean estado;
    
    
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="rol")
    private Set<Usuario> usuarios = new HashSet(0);
}
