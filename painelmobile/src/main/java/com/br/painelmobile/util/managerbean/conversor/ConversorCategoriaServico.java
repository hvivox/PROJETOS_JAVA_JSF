package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = CategoriaServico.class)
public class ConversorCategoriaServico implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceCategoriaServico servicoCategoriaServico;

	
	public ConversorCategoriaServico() {
		servicoCategoriaServico = CDIServiceLocator.getBean(ServiceCategoriaServico.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CategoriaServico retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoCategoriaServico.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			CategoriaServico categoriaServico = (CategoriaServico) value;
			return categoriaServico.getId() == null ? null : categoriaServico.getId().toString();			
		}
		
		return "";
	}

}
