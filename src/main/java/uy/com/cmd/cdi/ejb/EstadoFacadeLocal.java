/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.Estado;



/**
 *
 * @author maxi
 */
@Local
public interface EstadoFacadeLocal {

    void create(Estado estado);
    
    void edit(Estado estado);

    void remove(Estado estado);
    //Categoria find(int id);

    List<Estado> findAll();

    List<Estado> findRange(int[] range);
    
    Estado findByNombre(String nombre);

    int count();
    
    
    
}
