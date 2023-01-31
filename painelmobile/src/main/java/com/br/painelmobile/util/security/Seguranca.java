package com.br.painelmobile.util.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * TEM A FUNÇAO DE PEGAR O NOME DO USUÁRIO LOGADO E MOSTRAR NA TELA
 * @author hermogenes.silva
 *
 */
@Named
@RequestScoped
public class Seguranca {

	public String getNomeCompletoDoUsuarioLogado() {
		String nome = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado();

		// SE NAO HOUVER NENHUM USUARIO LOGADO O SISTEMA IRÁ MOSTRAR APENAS
		// OLÁ USUÁRIO...
		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getNomeCompleto();
		}

		return nome;
	}


	// PEGA O NOME DO USUARIO QUE ESTÁ LOGADO NO SISTEMA
	private UsuarioSistema getUsuarioLogado() {
		
		UsuarioSistema usuario = null;

		// aqui eu pego uma instancia do JSF que representa o usuário logado e
		// seto dentro da instancia do springSecurity
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		// se o resultado acima não for nulo será possivel pegar o usuário
		// logado através de uma instancia do spring
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario;
	}

}
