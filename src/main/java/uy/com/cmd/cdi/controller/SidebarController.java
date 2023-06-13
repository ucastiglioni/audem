/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import uy.com.cmd.cdi.domain.cdi.Usuario;

/**
 *
 * @author max
 */
@Named
@ViewScoped
public class SidebarController implements Serializable {

    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            if (usuario == null) {
                System.out.println(context.getExternalContext().toString());
                context.getExternalContext().redirect("/cdi/access.cdi");
            }
        } catch (Exception ex) {

        }
    }
    
    public void cerrarSesion(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            context.getExternalContext().redirect("/cdi/login.cdi");
        } catch (IOException ex) {
            Logger.getLogger(SidebarController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
