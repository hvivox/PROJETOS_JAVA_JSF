package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceGaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = GaleriaImagem.class)
public class ConversorGaleria implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceGaleriaImagem servicoGaleriaImagem;

	
	public ConversorGaleria() {
		servicoGaleriaImagem = CDIServiceLocator.getBean(ServiceGaleriaImagem.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		GaleriaImagem retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoGaleriaImagem.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			GaleriaImagem galeriaImagem = (GaleriaImagem) value;
			return galeriaImagem.getId() == null ? null : galeriaImagem.getId().toString();			
		}
		
		return "";
	}

}
