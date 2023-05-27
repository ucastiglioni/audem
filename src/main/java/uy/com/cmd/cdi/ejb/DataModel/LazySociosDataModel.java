/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uy.com.cmd.cdi.ejb.DataModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import java.lang.reflect.Field;
import org.primefaces.model.SortOrder;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.ejb.SocioFacadeLocal;
/**
 *
 * @author max
 */
public class LazySociosDataModel extends LazyDataModel<Socio>{
    
    @EJB
    SocioFacadeLocal EJBSocio;
    private List<Socio> datasource;
 
    public LazySociosDataModel(List<Socio> datasource) {
        this.datasource = datasource;
    }
 
    public Socio getRowData(int rowKey) {
        for (Socio socio : datasource) {
            if (socio.getId()==rowKey) {
                return socio;
            }
        }
        return null;
    }
 
    @Override
    public List<Socio> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        
        List<Socio> data = new ArrayList<>();

        for (Socio socio : datasource) {
            
            boolean match = true;
            boolean equals = true;
            
            if (filterMeta != null) {
                for (FilterMeta meta : filterMeta.values()) {
                    try {
                        String filterField = meta.getField();
                        Object filterValue;
                        Field field;
                        String fieldValue = null;

                        switch (filterField) {
                            case "nombre":
                                filterValue = meta.getFilterValue();
                                if  (!(socio.getNombre()== null)) {
                                    fieldValue = socio.getNombre();
                                }else{
                                    fieldValue="&_&_&_&_&_&_";
                                }
                                equals = true;
                                break;
//                            case "canal.nombre" :
//                                filterValue = meta.getFilterValue();
//                                if  (!(socio.getCanal() == null)) {
//                                    fieldValue = socio.getCanal().getNombre();
//                                }else{
//                                    fieldValue="&_&_&_&_&_&_";
//                                }
//                                equals = true;
//                                break;
//                            case "operacion.nombre":
//                                filterValue = meta.getFilterValue();
//                                if  (!(socio.getOperacion() == null)) {
//                                    fieldValue = socio.getOperacion().getNombre();
//                                }else{
//                                    fieldValue="&_&_&_&_&_&_";
//                                }
//                                equals = true;
//                                break;
//                            case "estadoQuerol.nombre" :
//                                filterValue = meta.getFilterValue();
//                                if  (!(socio.getEstadoQuerol() == null)) {
//                                    fieldValue = socio.getEstadoQuerol().getNombre();
//                                }else{
//                                    fieldValue="&_&_&_&_&_&_";
//                                }
//                                equals = true;
//                                break;
//                            case "estado.nombre" :
//                                filterValue = meta.getFilterValue();
//                                if  (!(socio.getEstado() == null)) {
//                                    fieldValue = socio.getEstado().getNombre();
//                                }else{
//                                    fieldValue="error";
//                                }
//                                equals = true;
//                                break;
                            default:
                                filterValue = meta.getFilterValue();
                                field =  socio.getClass().getDeclaredField(filterField);
                                field.setAccessible(true);
                                fieldValue = String.valueOf(field.get(socio));
                                field.setAccessible(false);
                                equals = false;
                        }
                        if (equals) {
                            if (filterValue == null || fieldValue.equals(filterValue.toString()) ) {
                                match = true;
                            }
                            else {
                                match = false;
                                break;
                            }
                        } else {
                            if (filterValue == null || fieldValue.startsWith(filterValue.toString()) ) {
                                match = true;
                            }
                            else {
                                match = false;
                                break;
                            }
                        }
                    }
                    catch (Exception e) {
                         match = false;
                    }
                }
            }
            if (match) {
                data.add(socio);
                //System.out.println("folio " + folio.getCliente().getNombre()) ;
            }
        }
 
        //sort
        if (sortMeta != null && !sortMeta.isEmpty()) {
            for (SortMeta meta : sortMeta.values()) {
            
                
                
            }
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
    
   


