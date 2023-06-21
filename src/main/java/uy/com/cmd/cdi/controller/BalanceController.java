/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.com.cmd.cdi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.ToggleEvent;
import uy.com.cmd.cdi.domain.cdi.Balance;
import uy.com.cmd.cdi.ejb.BalanceFacadeLocal;

/**
 *
 * @author max
 */
@Named
@SessionScoped
public class BalanceController implements Serializable {
 

    private static final long serialVersionUID = 20111120L;

    private List<Balance> balances;
    private Balance selectedBalance;
    private List<Boolean> colVisibles;
    private int currentLevel = 1;
    private Date filtro;
    @EJB
    BalanceFacadeLocal ejbBalance;

    @PostConstruct
    public void init() {

        
        //filtro = new Date("01/03/2018");
        filtro = new Date();
        if (filtro != null) {
            balances = new ArrayList<>();

            //Saldo pendiente
            balances.add(ejbBalance.getSaldoPendiente(filtro));

            //Emision
            balances.add(ejbBalance.getEmision(filtro));

            //Cobrado
            balances.add(ejbBalance.getCobradoEfectivo(filtro));
            
            //Cobrado
            //balances.add(ejbBalance.getCobradoOca(filtro));
            //Cobrado
            //balances.add(ejbBalance.getCobradoMatricula(filtro));
            //Cobrado
            //balances.add(ejbBalance.getCobradoRemido(filtro));

        }

        colVisibles = Arrays.asList(true, true, true, true, true, true, true);
        selectedBalance = new Balance();

    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public Balance getSelectedBalance() {
        return selectedBalance;
    }

    public void setSelectedBalance(Balance selectedBalance) {
        this.selectedBalance = selectedBalance;
    }

    public void onRowToggle(ToggleEvent event) {

//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//                "Filtro: "+filtro.toString()+"<br/>"+
//                "Row State " + event.getVisibility(),
//                "Model:" + ((Balance) event.getData()).getCuenta());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);

        String var = ((Balance) event.getData()).getCuenta();

        if (var.equals("Emision")) {
            colVisibles = Arrays.asList(true, true, true, true, false, false, false, true, true);
        } else {
            if (var.equals("Saldo Pendiente Anterior")) {
                colVisibles = Arrays.asList(true, true, true, true, true, true, true, false, false);
            } else {
                if (var.equals("Cobrado Efectivo")) {
                    colVisibles = Arrays.asList(true, true, true, true, true, false, false, true, true);
                }
                if (var.equals("Cobrado Oca")) {
                    colVisibles = Arrays.asList(true, true, true, true, true, false, false, true, true);
                }
                if (var.equals("Cobrado Matricula")) {
                    colVisibles = Arrays.asList(true, true, true, true, true, false, false, true, true);
                }
                if (var.equals("Cobrado Remido")) {
                    colVisibles = Arrays.asList(true, true, true, true, true, false, false, true, true);
                }
            }
        };

    }

    public List<Boolean> getColVisibles() {
        return colVisibles;
    }

    public void setColVisibles(List<Boolean> colVisibles) {
        this.colVisibles = colVisibles;
    }

    public Date getFiltro() {
        return filtro;
    }

    public void setFiltro(Date filtro) {
        this.filtro = filtro;
    }

    public void refreshBalance() {

        System.out.println(filtro.toString());

        

        if (filtro != null) {
            balances = new ArrayList<Balance>();

            //Saldo pendiente
            balances.add(ejbBalance.getSaldoPendiente(filtro));

            //Emision
            balances.add(ejbBalance.getEmision(filtro));

            //Cobrado efectivo
            balances.add(ejbBalance.getCobradoEfectivo(filtro));
            
            //Cobrado Oca
            //balances.add(ejbBalance.getCobradoOca(filtro));
            
            //Cobrado Matriculas
            //balances.add(ejbBalance.getCobradoMatricula(filtro));
            
            //Cobrado Remido
            //balances.add(ejbBalance.getCobradoRemido(filtro));

        }

    }

}

    

