package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceCardapio;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Cardapio.class)
public class ConversorCardapio implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceCardapio servicoCardapio;

	
	public ConversorCardapio() {
		servicoCardapio = CDIServiceLocator.getBean(ServiceCardapio.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cardapio retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoCardapio.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Cardapio cardapio = (Cardapio) value;
			return cardapio.getId() == null ? null : cardapio.getId().toString();			
		}
		
		return "";
	}

}
