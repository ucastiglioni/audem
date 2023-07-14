package uy.com.cmd.cdi.controller;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.IOException;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.FilterMeta;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import uy.com.cmd.cdi.domain.cdi.Pago;
import uy.com.cmd.cdi.domain.cdi.PagoAdeudos;
import uy.com.cmd.cdi.domain.cdi.Socio;
import uy.com.cmd.cdi.ejb.AbstractFacade;
import uy.com.cmd.cdi.ejb.PagoFacade;
import uy.com.cmd.cdi.ejb.PagoFacadeLocal;
import uy.com.cmd.cdi.ejb.SocioFacadeLocal;

@Named
@ViewScoped
public class PagoController implements Serializable {

    private LazyDataModel<Pago> pagosDataModel;

    List<PagoAdeudos> deudores;
    
    private Pago selectedPago;

    private List<Pago> selectedPagos;

    private List<Socio> socios;

    private String invoiceName;
    private JRBeanCollectionDataSource beanCollectionDataSource;
    private Map<String, Object> parameters;

    @EJB
    PagoFacadeLocal ejbPago;

    @EJB
    SocioFacadeLocal ejbSocio;

    @PostConstruct
    public void init() {
        
        deudores=new ArrayList<PagoAdeudos>();
        
        socios = ejbSocio.findAll();
        selectedPago = new Pago();
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

    public List<PagoAdeudos> getDeudores() {
        deudores = ejbPago.getDeudores();
        return deudores;
    }

    public void setDeudores(List<PagoAdeudos> deudores) {
        this.deudores = deudores;
    }
    
    

    public void modificarPago() {

        try {
            boolean yaPagoMesAnio = false;

            //generarReciboPDF(idPagos);
            ejbPago.edit(selectedPago);

            FacesMessage msg = new FacesMessage("Pago realizado con éxito !");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            //prepararPago();
            PrimeFaces.current().ajax().update("pnlEditPago form:tblPagos");

        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error al modificar el pago ", msg);
        }

    }

    public void deletePago() {

        ejbPago.remove(this.selectedPago);
        this.selectedPago = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pago eliminado OK !"));
        PrimeFaces.current().ajax().update("form:messages", "form:tblPagos");
    }

    public void imprimirPago() {
        generarReciboPDF(selectedPago.getId() + "");
    }

    private void generarReciboPDF(String idPago) {
        try {
            generaPDF(idPago);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage());
            FacesContext.getCurrentInstance().addMessage("Error al realizar el pago ", msg);
        }
    }

    public void generaPDF(String idPago) throws JRException, IOException {

        System.out.println("inicio printPDF");
        List<String> datasource = new ArrayList<>();
        String filename = "recibo_" + idPago + ".pdf";
        String jasperPath = "/reportes/reporteReciboCdi.jasper";
//        String jasperPath = "/reportes/prueba.jasper";
        Map parameters = new HashMap();

//via original
        parameters.put("idPago", Integer.valueOf(idPago));
        parameters.put("via", "original");
        parameters.put("p_nombre", selectedPago.getSocio().getApellido() + " " + selectedPago.getSocio().getNombre());
        parameters.put("p_numerosocio", selectedPago.getSocio().getId());
        parameters.put("p_categoria", selectedPago.getSocio().getCategoria().getDescripcion());
        parameters.put("p_ci", selectedPago.getSocio().getCi());
        Date fechaInscripcion = selectedPago.getSocio().getFechaInscripcion();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strFechaInscripcion = dateFormat.format(fechaInscripcion);
        parameters.put("p_fechaingreso", strFechaInscripcion);
        parameters.put("p_mes", selectedPago.getMes());
        parameters.put("p_anio", selectedPago.getAnio());
        parameters.put("p_funcionario", selectedPago.getUsuario().getNombre());
        Date fechaPago = selectedPago.getFechaPago();
        String strFechaPago = dateFormat.format(fechaPago);
        parameters.put("p_fechapago", strFechaPago);
        parameters.put("p_importe", selectedPago.getImporte());
        parameters.put("p_logo", FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/omega-layout/images/logocdi.png"));
        

        this.pdfMaxi(parameters, jasperPath, datasource, filename);
    }

    public void pdfMaxi(Map<String, Object> params, String jasperPath, List<?> datasource, String filename) throws JRException, IOException {
        //08012020 Maxi. se hace un metodo que exporta a pdf los recibos en lugar de mandarlos directo a la impresora.
        try {
            Connection conn = null;

            //conn = utilitariosDao.getDBConexion();
            //-----------------------------------------------------
            String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
            System.out.println("pdfMaxi - relativeWebPath " + relativeWebPath);
            File file = new File(relativeWebPath);
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource, false);
            //JasperPrint print=JasperFillManager.
            JasperPrint print = JasperFillManager.fillReport(file.getPath(), params,source);
            print.setName(filename);

            //https://www.youtube.com/watch?v=oTigbEyBelg
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment;filename=" + filename + ".pdf");
            FacesContext.getCurrentInstance().getExternalContext().setResponseContentType("application/pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(print, servletOutputStream);
            System.out.println("All done the report is done");
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            Logger.getLogger(ex.getMessage());
        }

    }

}
