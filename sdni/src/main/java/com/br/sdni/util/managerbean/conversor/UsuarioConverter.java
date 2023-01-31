package com.br.sdni.util.managerbean.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.br.sdni.modelo.negocios.servico.ServiceUsuario;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private ServiceUsuario servicoUsuario;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			
			Integer id;
			id = Integer.parseInt(value);
			retorno = this.servicoUsuario.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario == null || usuario.getId() == null ? 
					null : usuario.getId().toString();
		}
		return "";
	}
}