package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.br.painelmobile.modelo.negocios.servico.ServiceUnidade;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Unidade;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Unidade.class)
public class ConversorUnidade implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceUnidade servicoUnidade;

	
	public ConversorUnidade() {
		servicoUnidade = CDIServiceLocator.getBean(ServiceUnidade.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Unidade retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoUnidade.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Unidade unidade = (Unidade) value;
			return unidade.getId() == null ? null : unidade.getId().toString();			
		}
		
		return "";
	}

}
