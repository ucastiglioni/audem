/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import uy.com.cmd.cdi.domain.cdi.Balance;
import uy.com.cmd.cdi.domain.cdi.Categoria;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;

/**
 *
 * @author maxi
 */
@Stateless
public class BalanceFacade extends AbstractFacade<Balance> implements BalanceFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @EJB
    PagoFacadeLocal ejbPago;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BalanceFacade() {
        super(Balance.class);
    }

    @Override
    public Balance getSaldoPendiente(Date filtro) {
        Balance balance = new Balance();
        List<PagoAdeudos> listado = new ArrayList();

        Long saldoPendiente = Long.valueOf("0");

        String sql = " SELECT p.id, "
                + // "        timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),curdate())," + 
                "        timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),'"
                + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()) + "'),"
                + //"        sum(timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),curdate()) * tc.importe) NoCobrado " + 
                "        sum(timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),'"
                + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString())
                + "') * tc.importe) NoCobrado "
                + " FROM   pagos p, socio s, tipocuota tc "
                + " WHERE     ( p.id, "
                + "             p.anio, "
                + "             p.mes, "
                + "             p.id) = (SELECT p1.id, "
                + "                                 max(p1.anio), "
                + "                                 max(p1.mes), "
                + "                                 max(id)  "
                + "                         FROM  pagos p1 "
                + "                         WHERE p1.id = p.id "
                + "                           AND anio = (SELECT max(p2.anio) "
                + "                                       FROM pagos p2 "
                + "                                       WHERE p2.id = p.id"
                + "                                         and p2.fechagrabado <= ' " + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()) + "' ) "
                + "                                and p1.fechagrabado <= ' " + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()) + "' )"
                + " AND p.id = s.id "
                + " AND s.idtipocuota = tc.id "
                + " AND s.idcestado = 1 "
                + " AND p.fechagrabado <= ' " + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()) + "'"
                + " AND timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),'" + arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()) + "') > 0"
                + " GROUP BY s.id,p.id  ";

        System.out.println(sql);

        List lista = em.createNativeQuery(sql).getResultList();
        //  List lista = em.createNamedQuery(sql).getResultList();

        for (Iterator it = lista.iterator(); it.hasNext();) {
            Object[] array = (Object[]) it.next();
            PagoAdeudos aux = new PagoAdeudos();

            aux.setId(this.getRandomId());

            aux.setPago(ejbPago.find((Integer) array[0]));

            aux.setNombre(aux.getPago().getSocio().getNombre());
            aux.setNroSocio(aux.getPago().getSocio().getNumeroSocio());

//            aux.setDescMedioPagoPago(aux.getPago().getMedioPago().getDescripcion());
//            aux.setDescMedioPagoSocio(aux.getPago().getSocio().getMedioPago().getDescripcion());
            aux.setFechaPago(aux.getPago().getFechaPago());
            aux.setAnio(aux.getPago().getAnio());
            aux.setMes(aux.getPago().getMes());

            aux.setMesesAdeudados(Integer.valueOf(array[1].toString()));

            saldoPendiente = saldoPendiente + Integer.valueOf(array[2].toString());

            //saldoPendiente = null;
            listado.add(aux);
        }

        //balance.setCuenta("Saldo anterior pendiente a Cobrar al: "+ arregloFecha(doyUltimoDiaMesAnterior(filtro.toString()).toString()));
        balance.setCuenta("Saldo anterior pendiente a Cobrar al:");
        balance.setImporte(saldoPendiente * -1);
        balance.setPagoAdeudos(listado);
        return balance;
    }

    @Override
    public Balance getEmision(Date filtro) {
        Balance balance = new Balance();
        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date fecha;
        int idSocio = 0;
        try {
            fecha = parserSDF.parse(filtro.toString());
            //String strFecha=new SimpleDateFormat("yyyy-MM-dd").format(fecha);
            String strAnio = new SimpleDateFormat("yyyy").format(fecha);
            String strMes = new SimpleDateFormat("MM").format(fecha);

            List<PagoAdeudos> listado = new ArrayList<PagoAdeudos>();

            Long saldoEmision = Long.valueOf("0");
            String sql = "select  s.idsocio ,  s.numerosocio,  s.nombre, s.idtipocuota, tc.nombretipo, vtc.importe "
                    + "from sociosView as s,      tipocuota tc,      valortipocuota vtc "
                    + "where s.idtipocuota = vtc.idtipocuota   "
                    + "and s.idtipocuota = tc.id   and tc.id = vtc.idtipocuota and s.idcestado  = 1  "
                    + " and vtc.vigencia = (select   max(vigencia)                       "
                    + "from     valortipocuota v                       "
                    + "where    date_format(v.vigencia,'%Y-%m') <= date_format('" + arregloFecha(filtro.toString()) + "','%Y-%m')"
                    + "                        and   v.idtipocuota = s.idtipocuota)   and s.fechaGrabado = ("
                    + "							select   max(fechaGrabado) "
                    + "                       from     sociosView v                        "
                    + "			where   date_format(v.fechaGrabado,'%Y-%m') <= date_format('" + arregloFecha(filtro.toString()) + "','%Y-%m')"
                    + "			and     v.idsocio = s.idsocio"
                    + "                       );";

            System.out.println("query: " + sql);

            List lista = em.createNativeQuery(sql).getResultList();

            for (Iterator it = lista.iterator(); it.hasNext();) {
                Object[] array = (Object[]) it.next();
                PagoAdeudos aux = new PagoAdeudos();

                aux.setId(this.getRandomId());

                aux.setNombre(array[2].toString());
                aux.setNroSocio(Integer.valueOf(array[1].toString()));
                idSocio = aux.getNroSocio();
                //aux.setAnio(2018);
                aux.setAnio(Integer.parseInt(strAnio));
                //aux.setMes(02);
                aux.setMes(Integer.parseInt(strMes));
                aux.setIdTipoCuota(Integer.parseInt(array[3].toString()));
                aux.setNombreTipo(array[4].toString());
                aux.setImporteCuota(Integer.parseInt(array[5].toString()));

                saldoEmision = saldoEmision + aux.getImporteCuota();
                listado.add(aux);
            }

            //balance.setCuenta("Emision : " + strMes + strAnio );
            balance.setCuenta("Emision");
            balance.setImporte(saldoEmision);
            balance.setPagoAdeudos(listado);
        } catch (ParseException ex) {
            System.out.println("query: " + idSocio + " " + ex.getMessage());

        }
        return balance;
    }

    @Override
    public Balance getCobradoEfectivo(Date filtro) {
        Balance balance = new Balance();
        List<PagoAdeudos> listado = new ArrayList<PagoAdeudos>();

        Long saldoCobrado = new Long("0");

        String sql = "SELECT p.id,"
                + "         'Cobrado Efectivo' "
                + "   FROM   pagos as p,"
                + "          socio as s"
                + "   WHERE  p.idSocio = s.id "
                + "   AND    date_format(p.fechaGrabado,'%Y-%m') = date_format('"
                + arregloFecha(filtro.toString()) + "','%Y-%m')";
System.out.println(sql);
        List lista = em.createNativeQuery(sql).getResultList();
        //List lista = sesion.createSQLQuery(sql).list();

        for (Iterator it = lista.iterator(); it.hasNext();) {
            Object[] array = (Object[]) it.next();
            //Integer[] array = (Integer[]) it.next();
            PagoAdeudos aux = new PagoAdeudos();

            aux.setId(this.getRandomId());

            aux.setPago(ejbPago.find((Integer) array[0]));

            aux.setNombre(aux.getPago().getSocio().getNombre()+" "+aux.getPago().getSocio().getApellido());
            aux.setNroSocio(aux.getPago().getSocio().getId());
            aux.setFechaPago(aux.getPago().getFechaPago());
            aux.setAnio(aux.getPago().getAnio());
            aux.setMes(aux.getPago().getMes());
            aux.setNombreTipo(aux.getPago().getTipoCuota().getNombreTipo());

            aux.setImporteCuota(aux.getPago().getImporte());
            saldoCobrado = saldoCobrado + aux.getImporteCuota();

            listado.add(aux);
        }

        balance.setCuenta("Cobrado Efectivo");
        balance.setImporte(saldoCobrado);
        balance.setPagoAdeudos(listado);
        return balance;
    }

    @Override
    public Balance getCobradoOca(Date filtro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Balance getCobradoMatricula(Date filtro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Balance getCobradoRemido(Date filtro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Date doyUltimoDiaMesAnterior(String fechaParametro) {

        Date filtro_aux = arregloDFecha(fechaParametro.toString());
        Calendar calFin = Calendar.getInstance();
        int mes = filtro_aux.getMonth();

        int anio = filtro_aux.getYear() + 1900;

        if (mes == 0) {
            mes = 11;
            anio = anio - 1;
        } else {
            mes = mes - 1;
        }

        calFin.set(anio, mes, 1);
        calFin.set(anio, mes, calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fechaFin = calFin.getTime();

        return fechaFin;

    }

    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String arregloFecha(String fechaParametro) {

        Date date = null;
        String idate = null;
        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH.ENGLISH);
        try {
            date = parserSDF.parse(fechaParametro);
            idate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        } catch (ParseException ex) {
            System.out.println("error: de parseo:" + ex.getMessage());
        }

        return idate;
    }

    private Date arregloDFecha(String fechaParametro) {

        Date date = null;
        String idate = null;
        SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            date = parserSDF.parse(fechaParametro);
            idate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        } catch (ParseException ex) {
            System.out.println("error: de parseo:" + ex.getMessage());
        }

        return date;
    }
}
