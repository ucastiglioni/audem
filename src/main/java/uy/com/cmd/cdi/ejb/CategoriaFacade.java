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
import uy.com.cmd.cdi.domain.cdi.Categoria;



/**
 *
 * @author maxi
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public Categoria findByNombre(String nombre) {

        Categoria s = null;
        try {
            String consulta= "from Categoria u where u.detalle=?1";
            
            Query query = em.createQuery(consulta);
            query.setParameter(1, nombre);
            List<Categoria> lista = query.getResultList();
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

//    @Override
//    public Categoria find(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

}
