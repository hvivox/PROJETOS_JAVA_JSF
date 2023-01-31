package com.br.painelmobile.util.managerbean.diversos;


import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named("navegacao")
@RequestScoped
public class Navegacao {

	public String getItemCssClass(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();
		
		viewId = "/" + viewId + ".xhtml";
		
		
		
		return currentViewId.equals(viewId) ? "is-selected" : null;
	}
	
}
