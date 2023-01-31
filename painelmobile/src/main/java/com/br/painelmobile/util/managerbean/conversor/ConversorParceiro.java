package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Parceiro;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Parceiro.class)
public class ConversorParceiro implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceParceiro servicoParceiro;

	
	public ConversorParceiro() {
		servicoParceiro = CDIServiceLocator.getBean(ServiceParceiro.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Parceiro retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoParceiro.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Parceiro parceiro = (Parceiro) value;
			return parceiro.getId() == null ? null : parceiro.getId().toString();			
		}
		
		return "";
	}

}
