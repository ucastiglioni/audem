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
import uy.com.cmd.cdi.domain.cdi.TipoCuota;




/**
 *
 * @author maxi
 */
@Stateless
public class TipoCuotaFacade extends AbstractFacade<TipoCuota> implements TipoCuotaFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoCuotaFacade() {
        super(TipoCuota.class);
    }

    @Override
    public TipoCuota findByNombre(String nombre) {

        TipoCuota s = null;
        try {
            String consulta= "from TipoCuota u where u.nombreTipo=?1";
            
            Query query = em.createQuery(consulta);
            query.setParameter(1, nombre);
            List<TipoCuota> lista = query.getResultList();
            if (!lista.isEmpty()) {
                s = lista.get(0);
            }
            if (s != null) {
                System.out.println(s.getNombreTipo());
                
            }
        } catch (Exception e) {
            throw e;
        }
        return s;
    }


}
