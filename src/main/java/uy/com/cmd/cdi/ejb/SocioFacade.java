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
import uy.com.cmd.cdi.domain.cdi.Socio;


/**
 *
 * @author maxi
 */
@Stateless
public class SocioFacade extends AbstractFacade<Socio> implements SocioFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SocioFacade() {
        super(Socio.class);
    }

    @Override
    public Socio findByEmail(String email) {

        Socio s = null;
        try {
            String consulta= "from Socio u where u.email=?1";
            
            Query query = em.createQuery(consulta);
            query.setParameter(1, email);
            List<Socio> lista = query.getResultList();
            if (!lista.isEmpty()) {
                s = lista.get(0);
            }
            if (s != null) {
                System.out.println(s.getNombre());
                
            }
        } catch (Exception e) {
            throw e;
        }
        return s;
    }

    @Override
    public Socio find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
