/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;
import uy.com.cmd.cdi.ejb.DataModel.LazyAdeudosDataModel;

/**
 *
 * @author max
 */
@Named
@ViewScoped
public class PagosAtrasadosController implements Serializable {
    private LazyDataModel<PagoAdeudos> lazyModel;
     
    private PagoAdeudos selectedAdeudos;
    
    @Inject
    private PagoController service;
    
    
    @PostConstruct
    public void init(){
        lazyModel=new LazyAdeudosDataModel(service.getDeudores());
    }

    public LazyDataModel<PagoAdeudos> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<PagoAdeudos> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public PagoAdeudos getSelectedAdeudos() {
        return selectedAdeudos;
    }

    public void setSelectedAdeudos(PagoAdeudos selectedAdeudos) {
        this.selectedAdeudos = selectedAdeudos;
    }
    
    
    
}
