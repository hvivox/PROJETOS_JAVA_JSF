package com.br.sdni.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.br.sdni.modelo.negocios.servico.ServicePermissao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;



@FacesConverter("permissaoConverter")
public class PermissaoConverter implements Converter {

	@Inject
	private ServicePermissao servicePermissao;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Permissao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			
			 
			Integer id = Integer.parseInt(value);
			retorno = this.servicePermissao.porId(id);
		}

		return retorno;
	}


	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Permissao permissao = (Permissao) value;
			return permissao == null || permissao.getId() == null ? 
					null : permissao.getId().toString();
		}
		return "";
	}
}