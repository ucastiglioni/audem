/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.domain.cdi.Usuario;

/**
 *
 * @author maxi
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> implements PagoFacadeLocal {

    @PersistenceContext(unitName = "cdiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }

    @Override
    public List<Pago> findBySocio(Socio socio) {
        List<Pago> pagosSocio;
        try {
            String consulta = "from Pago p where p.socio=?1";

            Query query = em.createQuery(consulta);
            query.setParameter(1, socio);
            pagosSocio = query.getResultList();

        } catch (Exception e) {
            throw e;
        }
        return pagosSocio;
    }

    @Override
    public Pago findUltimoPago(Socio socio) {
        List<Pago> lista;

        try {

            String consulta = "select * from pagos p where (p.idsocio,p.anio,p.mes,p.id)=(SELECT p1.idsocio,max(p1.anio)m,max(p1.mes),max(id) "
                    + "from pagos p1 "
                    + "where p1.idsocio = " + socio.getId()
                    + " and anio=(select max(p2.anio) from pagos p2 where p2.idsocio = " + socio.getId() + "))";

            Query query = em.createNativeQuery(consulta, Pago.class);

            lista = (List<Pago>) query.getResultList();
            if (lista.isEmpty()) {
                return null;
            } else if (lista.size() > 0) {
                return (Pago) lista.get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return null;
    }

    @Override
    public List<PagoAdeudos> getDeudores() {
        
       
        List<PagoAdeudos> listado = new ArrayList<PagoAdeudos>();
        
//        String sql ="select    p.id, " +
//                    "          p.idsocio, " +
//                    "          timestampdiff(month,DATE(CONCAT(p.anio,'-',p.mes,'-01')),curdate()) MesesAtrasos " +
//                    "FROM pagos p, " +
//                    "     socio s "  +
//                    "WHERE (p.idsocio,p.anio, p.mes,p.id ) =                           " +
//                    "			 (SELECT p1.idsocio,max(p1.anio), max(p1.mes),max(id) " +
//                    "              FROM   pagos p1" +
//                    "			  WHERE  p1.idsocio =  p.idsocio " +
//                    "             	AND  anio = (SELECT max(p2.anio) " +
//                    "                			 FROM   pagos p2  " +
//                    "          				     WHERE  p2.idsocio =  p.idsocio)" +
//                    "	 )" +
//                    "  and p.idsocio = s.id	 " +
//                    "  and s.idcestado = 1";
                    //"  and s.idcategoria in (1,2) " +
                    //"  and s.idtipocuota in (1,2,3,4,5) ";
                    
    String sql="SELECT "
            + "p.id,"
            + "s.id,"
            + "concat(s.nombre,' ', s.apellido) nombre,"
            + "s.FECHAINSCRIPCION,"
            + "Day(s.FECHAINSCRIPCION) diadepago,"
            + "p.mes ultimoMesPago,"
            + "p.fechagrabado fechaultimopago,"
            + "Month(curdate()) mesactual,"
            + "Month(curdate())-p.mes mesesadeudados "
            + "FROM "
            + "cdi.socio s , cdi.pagos p "
            + "where s.id=p.idSocio "
            + "and Month(curdate()) > (select max(p2.mes) from cdi.pagos p2 where idSocio=p.idSocio) "
            + "and Day(curdate()) > Day(s.FECHAINSCRIPCION);";
            
        System.out.println(sql);
//        List lista = sesion.createSQLQuery(sql).list();
        List lista = em.createNativeQuery(sql).getResultList();
       
        
        for ( Iterator it = lista.iterator(); it.hasNext();) {
                Object[] array = (Object[]) it.next();
                PagoAdeudos aux = new PagoAdeudos();
                aux.setId(this.getRandomId());
                aux.setPago(find((Integer) array[0]));
                //System.out.println("Id: "+aux.getPago().getId());
                aux.setNombre(aux.getPago().getSocio().getNombre()+" "+aux.getPago().getSocio().getApellido());
                aux.setNroSocio(aux.getPago().getSocio().getNumeroSocio());
                aux.setFechaPago(aux.getPago().getFechaPago());
                aux.setAnio(aux.getPago().getAnio());
                aux.setMes(aux.getPago().getMes());
                aux.setImporteCuota(aux.getPago().getTipoCuota().getImporte()*Integer.valueOf(array[8].toString()));//meses adeudados * importe cuota 
                aux.setMesesAdeudados(Integer.valueOf(array[8].toString())); //meses adeudados
                aux.setDiaDePago(Integer.valueOf(array[4].toString()));
                listado.add(aux);
         }
         return listado; 
    }

    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
        
    

    @Override
    public List<Socio> getPagaronMes(Date fecha) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String guardarPagoMatricula(Socio selectedSocio, Usuario usu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean yaPagoMesAnio(Pago selectedPago) {
        boolean yaPagoEsteMes = false;
        List<Pago> pagosSocio;
        try {
            String consulta = "from Pago p where p.socio=?1 and p.mes=?2 and p.anio=?3 and p.importe=?4" ;

            Query query = em.createQuery(consulta);
            query.setParameter(1, selectedPago.getSocio());
            query.setParameter(2, selectedPago.getMes());
            query.setParameter(3, selectedPago.getAnio());
            query.setParameter(4, selectedPago.getImporte());
            pagosSocio = query.getResultList();
            if (!pagosSocio.isEmpty()) {
                yaPagoEsteMes = true;

            }
        } catch (Exception e) {
            throw e;
        }
        return yaPagoEsteMes;

    }

    @Override
    public List<Pago> listar(int i, int i1, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        //https://www.linkedin.com/pulse/simple-jpa-query-builder-primefaces-lazydatamodel-svetozar-radoj%C4%8Din/?trk=pulse-article_more-articles_related-content-card

        List<Pago> result = new ArrayList<>();
        String sql = buildWhereParams("from Pago ent where 1=1", filterBy);
        sql = buildOrderBy(sql, sortBy);
        TypedQuery query = em.createQuery(sql, Pago.class)
                .setFirstResult(i)
                .setMaxResults(i1);
        query = setWhereParams(query, filterBy);
        result = query.getResultList();
        return result;

    }

    protected String buildWhereParams(String sql, Map<String, FilterMeta> filters) {
         if ((filters != null) && !filters.isEmpty()) {
            Iterator<String> fColumns = filters.keySet().iterator();
            String column;
            FilterMeta fMeta;
            MatchMode mMode;
            Object fValue;
            
            while (fColumns.hasNext()) {

                column = fColumns.next();
                fMeta = filters.get(column);
                fValue=fMeta.getFilterValue();
                mMode = fMeta.getMatchMode();
               if(column.equals("socio.id")){
                   sql += " and ent." + column + mapMatchModeToJPAOperator(mMode) + fValue;
               }else{
                   sql += " and ent." + column + mapMatchModeToJPAOperator(mMode) + ":" + column + " ";
               }
                
                
            }
        }
         
        return sql;
    }// of buildWhereParams()

    protected String buildOrderBy(String sql, Map<String, SortMeta> sortBy) {
        if ((sortBy != null) && !sortBy.isEmpty()) {
            SortMeta sortM = sortBy.entrySet().stream().findFirst().get().getValue();
            String sortField = sortM.getField().trim();
            String sortOrder = sortM.getOrder().toString();
            sql += " order by ent." + sortField + " " + ((sortOrder.equals("ASCENDING") ? "ASC" : "DESC"));
        }
        return sql;
    }

    protected String mapMatchModeToJPAOperator(MatchMode mMode) {
        System.out.println(mMode);
        switch (mMode) {
            case CONTAINS:
                return " like ";
            case ENDS_WITH:
                return " like ";
            case STARTS_WITH:
                return " like ";
            case EQUALS:
                return " = "; // Checks if column value equals the filter value
            case EXACT:
                return " = "; // Checks if string representations of column value and filter value are same
            case GREATER_THAN:
                return " > ";
            case GREATER_THAN_EQUALS:
                return " >= ";
            case LESS_THAN:
                return " < ";
            case LESS_THAN_EQUALS:
                return " <= ";
        }
        return " = ";
    }


    protected TypedQuery setWhereParams(TypedQuery query, Map<String, FilterMeta> filters) {
            if ((filters != null) && !filters.isEmpty()) {
            Iterator<String> fColumns = filters.keySet().iterator();
            String column;
            FilterMeta fMeta;
            MatchMode mMode;
            Object fValue;
            while (fColumns.hasNext()) {
                column = fColumns.next();
                fMeta = filters.get(column);
                mMode = fMeta.getMatchMode();
                fValue = fMeta.getFilterValue();
                /*  case LESS_THAN:
                return " < ";
            case LESS_THAN_EQUALS:
                return " <= ";
        }
        return " = "; 
    }*/
                switch (column) {
                    case "id":
                        query.setParameter(column, buildParamValue(Integer.valueOf(fValue.toString()), mMode));
                        break;
//                    case "socio.id": 
//                        query.setParameter(column, buildParamValue(fValue, mMode));
//                        System.out.println("***************");
//                        System.out.println(query.toString());
//                        System.out.println("***************");
//                        break;
                    case "mes":
                        query.setParameter(column, buildParamValue(Integer.valueOf(fValue.toString()), mMode));
                        break;
                    case "anio":
                        query.setParameter(column, buildParamValue(Integer.valueOf(fValue.toString()), mMode));
                        break;
                    case "importe":
                        query.setParameter(column, buildParamValue(Integer.valueOf(fValue.toString()), mMode));
                        break;
                }

                //query.setParameter(column, buildParamValue(fValue,mMode));
                //query.setParameter(column, column.equalsIgnoreCase("socio.nombre")?
                //      buildParamValue(fValue.toString(),mMode):buildParamValue(Integer.valueOf(fValue.toString()), mMode));
                //query.setParameter(column, buildParamValue(fValue,mMode));
            }
        }
        return query;
    }

    protected Object buildParamValue(Object fValue, MatchMode mMode) {
        if ((fValue != null) && (fValue instanceof String)) {
            //fValue = addPrefixWildcardIfNeed(mMode) + fValue + addSufixWildcardIfNeed(mMode);
            fValue = addPrefixWildcardIfNeed(mMode) + fValue + addPrefixWildcardIfNeed(mMode);
        }
        return fValue;
    }

    private String addPrefixWildcardIfNeed(MatchMode mMode) {
        switch (mMode) {
            case CONTAINS:
                return " like ";
            case ENDS_WITH:
                return " like ";
            case STARTS_WITH:
                return " like ";
            case EQUALS:
                return " = "; // Checks if column value equals the filter value
            case EXACT:
                return " = "; // Checks if string representations of column value and filter value are same
            case GREATER_THAN:
                return " > ";
            case GREATER_THAN_EQUALS:
                return " >= ";
            case LESS_THAN:
                return " < ";
            case LESS_THAN_EQUALS:
                return " <= ";
        }
        return " = ";
    }

    /*
  The main EJB Service method you should use in you overriden LazyDataModel.load() method
     */
    public List<?> findEntites(Class<?> c, int first, int pageSize,
            Map<String, SortMeta> sortBy,
            Map<String, FilterMeta> filterBy) {
        List<?> result = new ArrayList<>();
        String sql = buildWhereParams("from " + c.getName() + " ent where 1=1 ", filterBy);
        sql = buildOrderBy(sql, sortBy);
        TypedQuery query = em.createQuery(sql, c)
                .setFirstResult(first)
                .setMaxResults(pageSize);
        query = setWhereParams(query, filterBy);
        result = query.getResultList();
        return result;
    }

    
}
