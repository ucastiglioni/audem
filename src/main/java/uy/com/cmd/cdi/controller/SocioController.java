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

import java.io.ByteArrayInputStream;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.PhaseId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.primefaces.omega.domain.Product;
import uy.com.cmd.cdi.domain.cdi.Categoria;
import uy.com.cmd.cdi.domain.cdi.Estado;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.domain.cdi.TipoCuota;
import uy.com.cmd.cdi.domain.cdi.Usuario;
import uy.com.cmd.cdi.ejb.CategoriaFacadeLocal;
import uy.com.cmd.cdi.ejb.EstadoFacadeLocal;
import uy.com.cmd.cdi.ejb.PagoFacadeLocal;
import uy.com.cmd.cdi.ejb.SocioFacadeLocal;
import uy.com.cmd.cdi.ejb.TipoCuotaFacadeLocal;

@Named
@ViewScoped
public class SocioController implements Serializable {

    private Socio socio;
    @EJB
    SocioFacadeLocal ejbSocio;
    @EJB
    CategoriaFacadeLocal ejbCategoria;
    @EJB
    TipoCuotaFacadeLocal ejbTipoCuota;
    @EJB
    EstadoFacadeLocal ejbEstado;
    @EJB
    PagoFacadeLocal ejbPago;

    //Socio selectedSocio;
    List<Socio> socios;
    private List<Socio> selectedSocios;
    private List<Categoria> categorias;
    private List<TipoCuota> tipoCuotas;
    private List<Estado> estados;
    Pago ultimoPago;
    Pago selectedPago;
    int cantCuotasAPagar;
    Usuario usuario;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        ultimoPago = new Pago();
        selectedPago = new Pago();
        socio = new Socio();
        socios = ejbSocio.findAll();
        categorias = ejbCategoria.findAll();
        tipoCuotas = ejbTipoCuota.findAll();
        estados = ejbEstado.findAll();

    }

    public void onRowToggle(ToggleEvent event) {
        //to sociodo ver cosas del socio, pagos, actividades, ficha etc. 
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
        FacesContext.getCurrentInstance().addMessage("Hola:", new FacesMessage("este boton no hace nada por ahora."));
    }

    public boolean hasSelectedSocios() {
        return this.selectedSocios != null && !this.selectedSocios.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSocios()) {
            int size = this.selectedSocios.size();
            return size > 1 ? size + " socios seleccionados" : "1 socio seleccionado";
        }

        return "Delete";
    }

    public void openNew() {
        //this.selectedSocio = new Socio();
        this.socio = new Socio();
    }

    public void deleteSelectedSocios() {
        for (Socio s : this.selectedSocios) {
            ejbSocio.remove(s);
        }
        socios = ejbSocio.findAll();

        this.selectedSocios = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Socios eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-socios");
    }

    public void deleteSocio() {
        ejbSocio.remove(this.socio);
        socios = ejbSocio.findAll();
        this.socio = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Socio eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-socios");
    }

//    public Socio getSelectedSocio() {
//        return selectedSocio;
//    }
//    public void setSelectedSocio(Socio selectedSocio) {
//        this.selectedSocio = selectedSocio;
//    }
    public int getCantCuotasAPagar() {
        return cantCuotasAPagar;
    }

    public Pago getSelectedPago() {
        return selectedPago;
    }

//---------------getters/setters--------------

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    public void setSelectedPago(Pago selectedPago) {
        this.selectedPago = selectedPago;
    }

    public void setCantCuotasAPagar(int cantCuotasAPagar) {
        this.cantCuotasAPagar = cantCuotasAPagar;
    }

    public Pago getUltimoPago() {
        return ejbPago.findUltimoPago(socio);
    }

    public void setUltimoPago(Pago pago) {
        this.ultimoPago = pago;
    }

    public List<Socio> getSelectedSocios() {
        return selectedSocios;
    }

    public void setSelectedSocios(List<Socio> selectedSocios) {
        this.selectedSocios = selectedSocios;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<TipoCuota> getTipoCuotas() {
        return tipoCuotas;
    }

    public void setTipoCuotas(List<TipoCuota> tipoCuotas) {
        this.tipoCuotas = tipoCuotas;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void guardarSocio() {
        try {
            FacesMessage msg;
            if (socio.getId() == 0) {
                ejbSocio.create(socio);
                msg = new FacesMessage("Socio creado ok.");
            } else {
                ejbSocio.edit(socio);
                msg = new FacesMessage("Socio actualizado ok.");
            }
            socios = ejbSocio.findAll();
            socio = new Socio();
            PrimeFaces.current().executeScript("PF('manageSocioDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-socios");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error !" + ex.getMessage()));
            System.out.println(ex.getMessage());
        }

    }

    public StreamedContent getDameFoto() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            return DefaultStreamedContent.builder()
                    .contentType("image/png")
                    .stream(() -> {
                        try {
                            return new ByteArrayInputStream(socio.getFoto());

                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reimprimirRecibo() {
        FacesContext.getCurrentInstance().addMessage("TODO Impresion ", null);
    }

    public void prepararPago() {
        try {
            ultimoPago=ejbPago.findUltimoPago(socio);
            if (ultimoPago.getId() == null) {
                // nunca pagó 
                ZoneId timeZone = ZoneId.systemDefault();
                LocalDate fecha = socio.getFechaInscripcion().toInstant().atZone(timeZone).toLocalDate();
                int mes = fecha.getDayOfMonth();
                int anio = fecha.getYear();
                int importe = socio.getTipoCuota().getImporte();
                selectedPago.setMes(mes);
                selectedPago.setAnio(anio);
                selectedPago.setImporte(importe);
                selectedPago.setSocio(socio);
                selectedPago.setTipoCuota(socio.getTipoCuota());
                selectedPago.setUsuario(usuario);
                selectedPago.setFechaPago(new Date());

                PrimeFaces.current().ajax().update("manage-pagosocio-content");
            }else{
                int anio = ultimoPago.getMes()==12?ultimoPago.getAnio()+1:ultimoPago.getAnio();
                int mes = ultimoPago.getMes()==12?1:ultimoPago.getMes()+1;
                
                int importe = socio.getTipoCuota().getImporte();
                selectedPago.setMes(mes);
                selectedPago.setAnio(anio);
                selectedPago.setImporte(importe);
                selectedPago.setSocio(socio);
                selectedPago.setTipoCuota(socio.getTipoCuota());
                selectedPago.setUsuario(usuario);
                selectedPago.setFechaPago(new Date());
            }
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error ", msg);
        }
    }

    public void guardarPago() {
        try {
            boolean yaPagoMesAnio=false;
            yaPagoMesAnio=ejbPago.yaPagoMesAnio(selectedPago);
            if (!yaPagoMesAnio){
                
            
            //String idPagos = ejbPago.guardarPago(socio,tipocuota,mes,anio,importe,usuario);
            //imprimirRecibo();
            //generarReciboPDF(idPagos);
            ejbPago.create(selectedPago);
            cantCuotasAPagar = 1;
            FacesMessage msg = new FacesMessage("Pago realizado con éxito !");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            prepararPago();
            PrimeFaces.current().ajax().update("manage-datosSocio-content");
            }else{
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El pago para ese mes/año ya fue realizado !");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("manage-datosSocio-content");
            }

        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error al realizar el pago ", msg);
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
