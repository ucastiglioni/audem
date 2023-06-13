/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import uy.com.cmd.cdi.domain.cdi.Usuario;
import uy.com.cmd.cdi.ejb.UsuarioFacade;
import uy.com.cmd.cdi.ejb.UsuarioFacadeLocal;

/**
 *
 * @author max
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    /**
     * Creates a new instance of LognBean
     */
    private Usuario usuario;
    private boolean loggedIn = false;
    @EJB
    UsuarioFacadeLocal ejbUsuario;

    @PostConstruct
    public void init() {
        usuario=new Usuario();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String login() {
        
        String redireccion = null;
        try {
            usuario = ejbUsuario.iniciarSesion(usuario);
            if (usuario != null) {
                //Almacenamos en la sesion de JSF

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                redireccion = "/protegido/socios?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Credenciales incorrectas"));
            }

        } catch (Exception ex) {
            //
        }
        return redireccion;

    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        //logeado = false;
    }

}
