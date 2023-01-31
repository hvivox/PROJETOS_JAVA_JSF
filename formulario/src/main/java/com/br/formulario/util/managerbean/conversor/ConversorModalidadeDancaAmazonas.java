package com.br.formulario.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.formulario.modelo.negocios.servico.ServiceModalidadeDancaAmazonas;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.Modalidade;
import com.br.formulario.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Modalidade.class)
public class ConversorModalidadeDancaAmazonas implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceModalidadeDancaAmazonas servicoModalidadeDancaAmazonas;

	
	public ConversorModalidadeDancaAmazonas() {
		servicoModalidadeDancaAmazonas = CDIServiceLocator.getBean(ServiceModalidadeDancaAmazonas.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Modalidade retorno = null;
		
		if (value != null && !value.isEmpty()) {					
			Integer id = Integer.parseInt(value);
			retorno = servicoModalidadeDancaAmazonas.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Modalidade modalidade = (Modalidade) value;
			return modalidade.getId() == null ? null : modalidade.getId().toString();			
		}
		
		return "";
	}

}
