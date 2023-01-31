package com.br.painelmobile.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.modelo.negocios.servico.ServiceVideo;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;
import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;



@FacesConverter(forClass = Video.class)
public class ConversorVideo implements Converter {
	//inject a partir da versão 2.3 do jsf
	//@Inject
	private ServiceVideo servicoVideo;

	
	public ConversorVideo() {
		servicoVideo = CDIServiceLocator.getBean(ServiceVideo.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Video retorno = null;
		
		if (value != null && !value.isEmpty()) {
					
			Integer id = Integer.parseInt(value);
			retorno = servicoVideo.porId(id);		
		}		
		return retorno;
	}

	
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		
		if (value != null) {
			Video video = (Video) value;
			return video.getId() == null ? null : video.getId().toString();			
		}
		
		return "";
	}

}
