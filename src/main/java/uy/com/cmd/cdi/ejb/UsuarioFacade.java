/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uy.com.cmd.cdi.ejb;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import uy.com.cmd.cdi.domain.cdi.Usuario;

/**
 *
 * @author maxi
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario iniciarSesion(Usuario us) {
        Usuario u = null;
        try {
            String consulta = "from Usuario u where u.nombreUsuario=?1 and u.clave=?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, us.getNombre());
            query.setParameter(2, us.getClave());
            List<Usuario> lista = query.getResultList();
            if (!lista.isEmpty()) {
                u = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return u;
    }

    @Override
    public Usuario findByEmail(String email) {

        Usuario u = null;
        try {
            String consulta= "from Usuario u where u.email=?1";
            
            Query query = em.createQuery(consulta);
            query.setParameter(1, email);
            List<Usuario> lista = query.getResultList();
            if (!lista.isEmpty()) {
                u = lista.get(0);
            }
            if (u != null) {
                System.out.println(u.getNombre());
                System.out.println(u.getEmail());
            }
        } catch (Exception e) {
            throw e;
        }
        return u;
    }

}
