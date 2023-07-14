/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.cmd.cdi.ejb.DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import java.lang.reflect.Field;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;

/**
 *
 * @author max
 */
public class LazyAdeudosDataModel extends LazyDataModel<PagoAdeudos> {

    private List<PagoAdeudos> datasource;

    public LazyAdeudosDataModel(List<PagoAdeudos> datasource) {
        this.datasource = datasource;
    }

    public PagoAdeudos getRowData(int rowKey) {
        for (PagoAdeudos pagoAdeudo : datasource) {
            if (pagoAdeudo.getId().equals(rowKey)) {
                return pagoAdeudo;
            }
        }
        return null;
    }

    @Override
    public List<PagoAdeudos> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {

        List<PagoAdeudos> data = new ArrayList<>();

        for (PagoAdeudos pagoAdeudo : datasource) {

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
                                if (!(pagoAdeudo.getNombre() == null)) {
                                    fieldValue = pagoAdeudo.getNombre();
                                } else {
                                    fieldValue = "&_&_&_&_&_&_";
                                }
                                equals = true;
                                break;

                            default:
                                filterValue = meta.getFilterValue();
                                field = pagoAdeudo.getClass().getDeclaredField(filterField);
                                field.setAccessible(true);
                                fieldValue = String.valueOf(field.get(pagoAdeudo));
                                field.setAccessible(false);
                                equals = false;
                        }
                        if (equals) {
                            if (filterValue == null || fieldValue.equals(filterValue.toString())) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        } else {
                            if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }
            if (match) {
                data.add(pagoAdeudo);
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
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

    @Override
    public int count(Map<String, FilterMeta> map) {

        return datasource.size();
    }

    @Override
    public PagoAdeudos getRowData(String rowKey) {
        for (PagoAdeudos mandatory : datasource) {
            if (mandatory.getId().toString().equals(rowKey)) {
                return mandatory;
            }
        }
        return null;
    }

    @Override
    public String getRowKey(PagoAdeudos item) {
        System.out.println(item.getId());
        return item.getId();
    }
}
