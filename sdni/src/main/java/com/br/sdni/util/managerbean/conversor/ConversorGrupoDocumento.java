package com.br.sdni.util.managerbean.conversor;

import javax.faces.component.UIComponent;


import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = GrupoDocumento.class)
public class ConversorGrupoDocumento implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceGrupoDocumento servicoGrupoDocumento;

	
	public ConversorGrupoDocumento() {
		servicoGrupoDocumento = CDIServiceLocator.getBean(ServiceGrupoDocumento.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		GrupoDocumento retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoGrupoDocumento.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			GrupoDocumento GrupoDocumento = (GrupoDocumento) value;
			return GrupoDocumento.getId() == null ? null : GrupoDocumento.getId().toString();			
		}
		
		return "";
	}

}
