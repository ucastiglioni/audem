/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.Usuario;

/**
 *
 * @author maxi
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);
    
    Usuario findByEmail(String email);

    int count();
    
    Usuario iniciarSesion(Usuario u);
    
}
