/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.TipoCuota;


/**
 *
 * @author maxi
 */
@Local
public interface TipoCuotaFacadeLocal {

    void create(TipoCuota tipocuota);
    
    void edit(TipoCuota tipocuota);

    void remove(TipoCuota tipocuota);
    //Categoria find(int id);

    List<TipoCuota> findAll();

    List<TipoCuota> findRange(int[] range);
    
    TipoCuota findByNombre(String nombre);

    int count();
    
    
    
}
