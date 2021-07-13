/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Proveedor;
import org.primefaces.PrimeFaces;

/**
 *
 * @author JAIME
 */
@ManagedBean(name = "bean")
@SessionScoped
public class ProveedorBean implements Serializable {

    private List<Proveedor> proveedor;

    private Proveedor selectedproveedor;

    public Proveedor getSelectedproveedor() {
        return selectedproveedor;
    }

    public void setSelectedproveedor(Proveedor selectedproveedor) {
        this.selectedproveedor = selectedproveedor;
    }

    public List<Proveedor> getSelectedproveedores() {
        return selectedproveedores;
    }

    public void setSelectedproveedores(List<Proveedor> selectedproveedores) {
        this.selectedproveedores = selectedproveedores;
    }

    private List<Proveedor> selectedproveedores;
    private ArrayList<Proveedor> lista = new ArrayList<>();

    public ProveedorBean() {
        lista.add(new Proveedor("001", "Carlos", "28.29", "092827728", "Juan", true));
        lista.add(new Proveedor("001", "Carlos", "28.29", "092827728", "Juan", true));
        lista.add(new Proveedor("001", "Carlos", "28.29", "092827728", "Juan", true));
        lista.add(new Proveedor("001", "Carlos", "28.29", "092827728", "Juan", true));

    }

    public ArrayList<Proveedor> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Proveedor> lista) {
        this.lista = lista;
    }
    
    public void openNew() {
        this.selectedproveedor = new Proveedor();
    }
    
    public void saveProduct() {
        if (this.selectedproveedor.getCode() == null) {
            this.selectedproveedor.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.proveedor.add(this.selectedproveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Proveedor Added"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Proveedor Actualizar"));
        }
        PrimeFaces.current().executeInitScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void deleteProduct() {
        this.proveedor.remove(this.selectedproveedor);
        this.selectedproveedor = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedproveedores.size();
            return size > 1 ? size + " products selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selectedproveedores != null && !this.selectedproveedores.isEmpty();
    }

    public void deleteSelectedProducts() {
        this.proveedor.removeAll(this.selectedproveedores);
        this.selectedproveedores = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

}
