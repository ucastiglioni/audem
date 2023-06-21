/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.domain.cdi.TipoCuota;
import uy.com.cmd.cdi.domain.cdi.Usuario;


/**
 *
 * @author maxi
 */
@Local
public interface PagoFacadeLocal {
    
    void create(Pago pago);
    void edit(Pago pago);
    void remove(Pago pago);
    List<Pago> findAll();
    Pago find(Object id);
    List<Pago> findRange(int[] range);
    
    

    int count();
        
    public List<Pago> findBySocio(Socio socio);
    public Pago findUltimoPago(Socio socio);
    
    //buscar los socios deudores
    public List<PagoAdeudos> getDeudores();
    
    //public List<Pago> listaPagos(PagoFilter filtroSocio);
    //public int quantidadeFiltrados(PagoFilter filtro);
    
    //socios que pagaron en un mes
    public List<Socio> getPagaronMes(Date fecha);
    
    //socios que pagaron adelantado meses
    public String guardarPagoMatricula(Socio selectedSocio, Usuario usu);

    public boolean yaPagoMesAnio(Pago selectedPago);
    
    List<Pago> listar(int i, int i1, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
    
    
}
