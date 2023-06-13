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
import uy.com.cmd.cdi.domain.cdi.Categoria;
import uy.com.cmd.cdi.ejb.CategoriaFacadeLocal;


@Named
@ViewScoped
public class CategoriaController implements Serializable {

    private Categoria categoria;
    @EJB
    CategoriaFacadeLocal ejbCategoria;

    List<Categoria> categorias;
    private List<Categoria> selectedCategorias;

    @PostConstruct
    public void init() {
        categoria = new Categoria();

        categorias = ejbCategoria.findAll();

    }

    public void onRowToggle(ToggleEvent event) {
        //to categoriado ver cosas del categoria, pagos, actividades, ficha etc. 
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
        FacesContext.getCurrentInstance().addMessage("Hola:", new FacesMessage("este boton no hace nada por ahora."));
    }

    public boolean hasSelectedCategorias() {
        return this.selectedCategorias != null && !this.selectedCategorias.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCategorias()) {
            int size = this.selectedCategorias.size();
            return size > 1 ? size + " categorias seleccionados" : "1 categoria seleccionado";
        }

        return "Delete";
    }

    public void openNew() {
        //this.selectedCategoria = new Categoria();
        this.categoria = new Categoria();
    }

    public void deleteSelectedCategorias() {
        for (Categoria s : this.selectedCategorias) {
            ejbCategoria.remove(s);
        }
        categorias = ejbCategoria.findAll();

        this.selectedCategorias = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorias eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categorias");
    }

    public void deleteCategoria() {
        ejbCategoria.remove(this.categoria);
        categorias = ejbCategoria.findAll();
        this.categoria = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categorias");
    }

//    public Categoria getSelectedCategoria() {
//        return selectedCategoria;
//    }
//    public void setSelectedCategoria(Categoria selectedCategoria) {
//        this.selectedCategoria = selectedCategoria;
//    }
    public List<Categoria> getSelectedCategorias() {
        return selectedCategorias;
    }

    public void setSelectedCategorias(List<Categoria> selectedCategorias) {
        this.selectedCategorias = selectedCategorias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void guardarCategoria() {
        try {
            FacesMessage msg ;
            if (categoria.getIdCategoria()== 0) {
                ejbCategoria.create(categoria);
                msg = new FacesMessage("Categoria creado ok.");
            } else {
                ejbCategoria.edit(categoria);
                msg = new FacesMessage("Categoria actualizado ok.");
            }
            categorias = ejbCategoria.findAll();
            categoria = new Categoria();
            PrimeFaces.current().executeScript("PF('manageCategoriaDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-categorias");
            
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
