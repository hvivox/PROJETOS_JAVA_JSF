package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Servico;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Servico.class)
public class ConversorServico implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceServico servicoServico;

	
	public ConversorServico() {
		servicoServico = CDIServiceLocator.getBean(ServiceServico.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Servico retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoServico.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Servico servico = (Servico) value;
			return servico.getId() == null ? null : servico.getId().toString();			
		}
		
		return "";
	}

}
