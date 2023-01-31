package com.br.sdni.util.managerbean.conversor;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import com.br.sdni.modelo.negocios.servico.ServiceGrupo;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;



@FacesConverter("grupoConverter")
public class GrupoConverter implements Converter {

	@Inject
	private ServiceGrupo serviceGrupo;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo retorno = null;

		if (StringUtils.isNotEmpty(value)) {			
			 
			Integer id = Integer.parseInt(value);
			retorno = this.serviceGrupo.porId(id);
		}

		return retorno;
	}


	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && StringUtils.isNotBlank(value.toString())) {
			
			
			Grupo grupo = (Grupo) value;
			
			return grupo == null || grupo.getId() == null ? 
					null : grupo.getId().toString();
		}
		return "";
	}
}