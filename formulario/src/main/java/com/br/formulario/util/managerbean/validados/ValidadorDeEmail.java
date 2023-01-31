package com.br.formulario.util.managerbean.validados;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validadorDeEmail")
public class ValidadorDeEmail implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		String email = value.toString();
		
		
		// verifica que o email tem @, se tem ponto e não tem espaço em branco
		if ((email.contains("@")) && (email.contains(".")) && (!email.contains(" "))) {
			String usuario = new String(email.substring(0, email.lastIndexOf('@')));
			String dominio = new String(email.substring(email.lastIndexOf('@') + 1, email.length()));

			/* O email esta dividido em duas parte usuario @ dominio verifica se o lado do usuario
			 * tem mais de 1 caractere e verificar se tem o @ outras verificações */
			if ((usuario.length() >= 1) && (!usuario.contains("@")) && (dominio.contains("."))
					&& (!dominio.contains("@")) && (dominio.indexOf(".") >= 1)
					&& (dominio.lastIndexOf(".") < dominio.length() - 1)) {
				

			}
			else {
				
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"",""));
			}

		}
		else {
			
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"",""));
		}

	}

}
