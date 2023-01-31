package com.br.formulario.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.formulario.modelo.negocios.servico.ServiceDancaAmazonas;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DancaAmazonas;
import com.br.formulario.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = DancaAmazonas.class)
public class ConversorDancaAmazonas implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceDancaAmazonas servicoDancaAmazonas;

	
	public ConversorDancaAmazonas() {
		servicoDancaAmazonas = CDIServiceLocator.getBean(ServiceDancaAmazonas.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		DancaAmazonas retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoDancaAmazonas.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			DancaAmazonas dancaAmazonas = (DancaAmazonas) value;
			return dancaAmazonas.getId() == null ? null : dancaAmazonas.getId().toString();			
		}
		
		return "";
	}

}
