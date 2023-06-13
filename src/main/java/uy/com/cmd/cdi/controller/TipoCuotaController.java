/*
   Copyright 2009-2022 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package uy.com.cmd.cdi.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import uy.com.cmd.cdi.domain.cdi.TipoCuota;
import uy.com.cmd.cdi.ejb.TipoCuotaFacadeLocal;


@Named
@ViewScoped
public class TipoCuotaController implements Serializable {

    private TipoCuota tipoCuota;
    @EJB
    TipoCuotaFacadeLocal ejbTipoCuota;

    List<TipoCuota> tipoCuotas;
    private List<TipoCuota> selectedTipoCuotas;

    @PostConstruct
    public void init() {
        tipoCuota = new TipoCuota();

        tipoCuotas = ejbTipoCuota.findAll();

    }

    public void onRowToggle(ToggleEvent event) {
        //to tipoCuotado ver cosas del tipoCuota, pagos, actividades, ficha etc. 
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
        FacesContext.getCurrentInstance().addMessage("Hola:", new FacesMessage("este boton no hace nada por ahora."));
    }

    public boolean hasSelectedTipoCuotas() {
        return this.selectedTipoCuotas != null && !this.selectedTipoCuotas.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedTipoCuotas()) {
            int size = this.selectedTipoCuotas.size();
            return size > 1 ? size + " tipoCuotas seleccionados" : "1 tipoCuota seleccionado";
        }

        return "Eliminar";
    }

    public void openNew() {
        //this.selectedTipoCuota = new TipoCuota();
        this.tipoCuota = new TipoCuota();
    }

    public void deleteSelectedTipoCuotas() {
        for (TipoCuota s : this.selectedTipoCuotas) {
            ejbTipoCuota.remove(s);
        }
        tipoCuotas = ejbTipoCuota.findAll();

        this.selectedTipoCuotas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TipoCuotas eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tipoCuotas");
    }

    public void deleteTipoCuota() {
        ejbTipoCuota.remove(this.tipoCuota);
        tipoCuotas = ejbTipoCuota.findAll();
        this.tipoCuota = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TipoCuota eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tipoCuotas");
    }

//    public TipoCuota getSelectedTipoCuota() {
//        return selectedTipoCuota;
//    }
//    public void setSelectedTipoCuota(TipoCuota selectedTipoCuota) {
//        this.selectedTipoCuota = selectedTipoCuota;
//    }
    public List<TipoCuota> getSelectedTipoCuotas() {
        return selectedTipoCuotas;
    }

    public void setSelectedTipoCuotas(List<TipoCuota> selectedTipoCuotas) {
        this.selectedTipoCuotas = selectedTipoCuotas;
    }

    public TipoCuota getTipoCuota() {
        return tipoCuota;
    }

    public void setTipoCuota(TipoCuota tipoCuota) {
        this.tipoCuota = tipoCuota;
    }

    public List<TipoCuota> getTipoCuotas() {
        return tipoCuotas;
    }

    public void setTipoCuotas(List<TipoCuota> tipoCuotas) {
        this.tipoCuotas = tipoCuotas;
    }

    public void guardarTipoCuota() {
        try {
            FacesMessage msg ;
            if (tipoCuota.getId()== 0) {
                ejbTipoCuota.create(tipoCuota);
                msg = new FacesMessage("TipoCuota creado ok.");
            } else {
                ejbTipoCuota.edit(tipoCuota);
                msg = new FacesMessage("TipoCuota actualizado ok.");
            }
            tipoCuotas = ejbTipoCuota.findAll();
            tipoCuota = new TipoCuota();
            PrimeFaces.current().executeScript("PF('manageTipoCuotaDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-tipoCuotas");
            
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error !" + ex.getMessage()));
            System.out.println(ex.getMessage());
        }

        

    }
//    private List<Product> products;
//
//    private Product selectedProduct;
//
//    private List<Product> selectedProducts;
//
//    @Inject
//    private ProductService productService;
//
//    @Inject
//    private OrderService orderService;
//
//    @PostConstruct
//    public void init() {
//        this.products = this.productService.getClonedProducts(30);
//    }
//    
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public Product getSelectedProduct() {
//        return selectedProduct;
//    }
//
//    public void setSelectedProduct(Product selectedProduct) {
//        this.selectedProduct = selectedProduct;
//    }
//
//    public List<Product> getSelectedProducts() {
//        return selectedProducts;
//    }
//
//    public void setSelectedProducts(List<Product> selectedProducts) {
//        this.selectedProducts = selectedProducts;
//    }
//
//    public void openNew() {
//        this.selectedProduct = new Product();
//    }
//
//    public void saveProduct() {
//        if (this.selectedProduct.getCode() == null) {
//            this.selectedProduct.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
//            this.products.add(this.selectedProduct);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
//        }
//        else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Updated"));
//        }
//        
//        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//    }
//
//    public void deleteProduct() {
//        this.products.remove(this.selectedProduct);
//        this.selectedProduct = null;
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//    }
//
//    public String getDeleteButtonMessage() {
//        if (hasSelectedProducts()) {
//            int size = this.selectedProducts.size();
//            return size > 1 ? size + " products selected" : "1 product selected";
//        }
//
//        return "Delete";
//    }
//
//    public boolean hasSelectedProducts() {
//        return this.selectedProducts != null && !this.selectedProducts.isEmpty();
//    }
//
//    public void deleteSelectedProducts() {
//        this.products.removeAll(this.selectedProducts);
//        this.selectedProducts = null;
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//    }
//
//    public void onRowToggle(ToggleEvent event) {
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
//    }
}
