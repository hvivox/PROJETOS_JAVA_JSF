package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceEvento;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Evento.class)
public class ConversorEvento implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceEvento servicoEvento;

	
	public ConversorEvento() {
		servicoEvento = CDIServiceLocator.getBean(ServiceEvento.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Evento retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoEvento.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Evento evento = (Evento) value;
			return evento.getId() == null ? null : evento.getId().toString();			
		}
		
		return "";
	}

}
