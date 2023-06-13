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
import uy.com.cmd.cdi.domain.cdi.Estado;
import uy.com.cmd.cdi.ejb.EstadoFacadeLocal;


@Named
@ViewScoped
public class EstadoController implements Serializable {

    private Estado estado;
    @EJB
    EstadoFacadeLocal ejbEstado;

    List<Estado> estados;
    private List<Estado> selectedEstados;

    @PostConstruct
    public void init() {
        estado = new Estado();

        estados = ejbEstado.findAll();

    }

    public void onRowToggle(ToggleEvent event) {
        //to estadodo ver cosas del estado, pagos, actividades, ficha etc. 
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
        FacesContext.getCurrentInstance().addMessage("Hola:", new FacesMessage("este boton no hace nada por ahora."));
    }

    public boolean hasSelectedEstados() {
        return this.selectedEstados != null && !this.selectedEstados.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEstados()) {
            int size = this.selectedEstados.size();
            return size > 1 ? size + " estados seleccionados" : "1 estado seleccionado";
        }

        return "Eliminar";
    }

    public void openNew() {
        //this.selectedEstado = new Estado();
        this.estado = new Estado();
    }

    public void deleteSelectedEstados() {
        for (Estado s : this.selectedEstados) {
            ejbEstado.remove(s);
        }
        estados = ejbEstado.findAll();

        this.selectedEstados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estados eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
    }

    public void deleteEstado() {
        ejbEstado.remove(this.estado);
        estados = ejbEstado.findAll();
        this.estado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
    }

//    public Estado getSelectedEstado() {
//        return selectedEstado;
//    }
//    public void setSelectedEstado(Estado selectedEstado) {
//        this.selectedEstado = selectedEstado;
//    }
    public List<Estado> getSelectedEstados() {
        return selectedEstados;
    }

    public void setSelectedEstados(List<Estado> selectedEstados) {
        this.selectedEstados = selectedEstados;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void guardarEstado() {
        try {
            FacesMessage msg ;
            if (estado.getId()== 0) {
                ejbEstado.create(estado);
                msg = new FacesMessage("Estado creado ok.");
            } else {
                ejbEstado.edit(estado);
                msg = new FacesMessage("Estado actualizado ok.");
            }
            estados = ejbEstado.findAll();
            estado = new Estado();
            PrimeFaces.current().executeScript("PF('manageEstadoDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
            
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
