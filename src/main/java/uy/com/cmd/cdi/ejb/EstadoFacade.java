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
import uy.com.cmd.cdi.domain.cdi.Estado;





/**
 *
 * @author maxi
 */
@Stateless
public class EstadoFacade extends AbstractFacade<Estado> implements EstadoFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoFacade() {
        super(Estado.class);
    }

    @Override
    public Estado findByNombre(String nombre) {

        Estado s = null;
        try {
            String consulta= "from Estado u where u.descripcion=?1";
            
            Query query = em.createQuery(consulta);
            query.setParameter(1, nombre);
            List<Estado> lista = query.getResultList();
            if (!lista.isEmpty()) {
                s = lista.get(0);
            }
            if (s != null) {
                System.out.println(s.getDescripcion());
                
            }
        } catch (Exception e) {
            throw e;
        }
        return s;
    }


}
