/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.Categoria;


/**
 *
 * @author maxi
 */
@Local
public interface CategoriaFacadeLocal {

    void create(Categoria categoria);

    void edit(Categoria categoria);

    void remove(Categoria categoria);

    //Categoria find(int id);

    List<Categoria> findAll();

    List<Categoria> findRange(int[] range);
    
    Categoria findByNombre(String nombre);

    int count();
    
    
    
}
