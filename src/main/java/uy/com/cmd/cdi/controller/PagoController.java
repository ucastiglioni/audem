package uy.com.cmd.cdi.controller;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.FilterMeta;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.ejb.PagoFacade;
import uy.com.cmd.cdi.ejb.PagoFacadeLocal;
import uy.com.cmd.cdi.ejb.SocioFacadeLocal;

@Named
@ViewScoped
public class PagoController implements Serializable {

    private LazyDataModel<Pago> pagosDataModel;

    private Pago selectedPago;

    private List<Pago> selectedPagos;
    
    private List<Socio> socios;

    @EJB
    PagoFacadeLocal ejbPago;
    
    @EJB
    SocioFacadeLocal ejbSocio;
            
            
    
    @PostConstruct
    public void init() {
        socios=ejbSocio.findAll();
        selectedPago=new Pago();
        this.pagosDataModel = new LazyDataModel<Pago>() {

            private static final long serialVersionUID = 1L;

            @Override
            public int count(Map<String, FilterMeta> map) {
                int c = ejbPago.count();
                return c;
            }

            @Override
            public List<Pago> load(int i, int i1, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                List<Pago> list = ejbPago.listar(i, i1, sortBy, filterBy);
                return list;
            }

            @Override
            public Pago getRowData(String rowKey) {
                for (Pago mandatory : pagosDataModel) {
                    if (mandatory.getId().toString().equals(rowKey)) {
                        return mandatory;
                    }
                }
                return null;
            }
            
            @Override
            public String getRowKey(Pago item) {
                System.out.println(item.getId());
                return item.getId().toString();
            }
                
        };
    }


    public void onRowToggle(ToggleEvent event) {
//        if (event.getVisibility() == Visibility.VISIBLE) {
//            Product product = (Product) event.getData();
//            if (product.getOrders() == null) {
//                product.setOrders(orderService.getOrders((int) (Math.random() * 10)));
//            }
//        }
    }

    public LazyDataModel<Pago> getPagosDataModel() {
        return pagosDataModel;
    }

    public void setPagosDataModel(LazyDataModel<Pago> pagosDataModel) {
        this.pagosDataModel = pagosDataModel;
    }

    public Pago getSelectedPago() {
        return selectedPago;
    }

    public void setSelectedPago(Pago selectedPago) {
        this.selectedPago = selectedPago;
    }

    public List<Pago> getSelectedPagos() {
        return selectedPagos;
    }

    public void setSelectedPagos(List<Pago> selectedPagos) {
        this.selectedPagos = selectedPagos;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }
    
    public void deletePago(){
        
        ejbPago.remove(this.selectedPago);
        this.selectedPago = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pago eliminado OK !"));
        PrimeFaces.current().ajax().update("form:messages", "form:tblPagos");
    }
 
    
    
    
}
