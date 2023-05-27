/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Local;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.domain.cdi.Usuario;

/**
 *
 * @author maxi
 */
@Local
public interface SocioFacadeLocal {

    void create(Socio socio);

    void edit(Socio socio);

    void remove(Socio socio);

    Socio find(int id);

    List<Socio> findAll();

    List<Socio> findRange(int[] range);
    
    Socio findByEmail(String email);

    int count();
    
    
    
}
