package com.br.painelmobile.util.security;



import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Usuario;


/**
 * FAZ A COMPARAÇÃO ENTRE USUÁRIO E SENHA E VERIFICAR OS GRUPOS QUE O USUÁRIO PERTENCE
 * Classe utilizada para extender a classe user do spring securite e acrescentar mais recursos
 * classe que representa o usuário para o springSecurity
 * @author hermogenes.silva
 *
 */
public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	/**
	 * FAZ A VALIDACAO ENTRE USUÁRIO E SENHA E PEGAR TODAS AS PERMISSÕES DO MESMO
	 * @param usuario NOME DO USUÁRIO QUE ESTÁ TENTANDO LOGAR
	 * @param authorities COLEÇÃO DAS AUTORIDADES QUE O USUÁRIO ESTÁ RELACIONADO
	 */
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getLoginUsuario(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	
	
	/**
	 * UTILIZADO PELA CLASSE SEGURANÇA PARA PEGAR O NOME DO USUÁRIO LOGADO 
	 * E MOSTRA NO TOPO DA TELA PRINCIPAL
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

}
