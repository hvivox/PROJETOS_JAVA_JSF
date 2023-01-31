package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceSelecao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Selecao;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Selecao.class)
public class ConversorSelecao implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceSelecao servicoSelecao;

	
	public ConversorSelecao() {
		servicoSelecao = CDIServiceLocator.getBean(ServiceSelecao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Selecao retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoSelecao.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Selecao selecao = (Selecao) value;
			return selecao.getId() == null ? null : selecao.getId().toString();			
		}
		
		return "";
	}

}
