/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uy.com.cmd.cdi.ejb.DataModel;


import java.lang.reflect.Field;
import org.primefaces.model.SortOrder;
import java.util.Comparator;

/**
 *
 * @author dtorr
 */
public class LazySorter implements Comparator<Object> {


    private String sortField;

    private SortOrder sortOrder;

    /**
     * initializing sorting field and sorting order
     * @param sortField
     * @param sortOrder
     */
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    /**
     * Comparing object1 and object2 with reflection
     * @param object1
     * @param object2
     * @return
     */
    @Override
    public int compare(Object object1, Object object2) {
        try {
            Field field1 = object1.getClass().getDeclaredField(this.sortField);
            Field field2 = object2.getClass().getDeclaredField(this.sortField);
            field1.setAccessible(true);
            field2.setAccessible(true);
            Object value1 = field1.get(object1);
            Object value2 = field2.get(object2);

            int value = ((Comparable)value1).compareTo(value2);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
