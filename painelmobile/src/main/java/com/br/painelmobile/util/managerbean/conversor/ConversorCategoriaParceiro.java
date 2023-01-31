package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = CategoriaParceiro.class)
public class ConversorCategoriaParceiro implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceCategoriaParceiro servicoCategoriaParceiro;

	
	public ConversorCategoriaParceiro() {
		servicoCategoriaParceiro = CDIServiceLocator.getBean(ServiceCategoriaParceiro.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CategoriaParceiro retorno = null;
		
		if (value != null && !value.isEmpty()) {					
			Integer id = Integer.parseInt(value);
			retorno = servicoCategoriaParceiro.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			CategoriaParceiro categoriaParceiro = (CategoriaParceiro) value;
			return categoriaParceiro.getId() == null ? null : categoriaParceiro.getId().toString();			
		}
		
		return "";
	}

}
