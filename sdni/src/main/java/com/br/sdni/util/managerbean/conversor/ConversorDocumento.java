package com.br.sdni.util.managerbean.conversor;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.sdni.modelo.negocios.servico.ServiceDocumento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Documento.class)
public class ConversorDocumento implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceDocumento servicoDocumento;

	
	public ConversorDocumento() {
		servicoDocumento = CDIServiceLocator.getBean(ServiceDocumento.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Documento retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			
			retorno = servicoDocumento.buscarPorIdDocumentoEGrupo(id);
			//retorno = servicoDocumento.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Documento documento = (Documento) value;
			return documento.getId() == null ? null : documento.getId().toString();			
		}
		
		return "";
	}

}
