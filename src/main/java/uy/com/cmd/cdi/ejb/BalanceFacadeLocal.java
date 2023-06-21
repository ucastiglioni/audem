/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.Balance;
import uy.com.cmd.cdi.domain.cdi.Categoria;


/**
 *
 * @author maxi
 */
@Local
public interface BalanceFacadeLocal {

     public Balance getSaldoPendiente(Date fecha);
    public Balance getEmision(Date filtro);
    public Balance getCobradoEfectivo(Date filtro);
    public Balance getCobradoOca(Date filtro);
    public Balance getCobradoMatricula(Date filtro);
    public Balance getCobradoRemido(Date filtro);

    
}
