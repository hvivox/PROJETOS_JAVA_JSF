package com.br.sdni.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.br.sdni.modelo.negocios.servico.ServiceUsuario;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.sdni.util.cdi.produtor.CDIServiceLocator;

/** BUSCA NO BANCO O NOME DO USUARIO QUE TENTOU LOGAR E CHAMA OUTRA CLASSE PARA VALIDAR A SENHA E O
 * GRUPO DE USUÁRIO Classe do springSecurity que UserDetailsService fornece os detalhes do usuário
 * esta classe deve ser registrada como um bean do spring
 * @author hermogenes.silva */
public class AppUserDetailsService implements UserDetailsService {

	
	
	/* Carrega o usuário informado na tela de login E VERIFICAR SE O MESMO É VÁLIDO ALÉM DE
	 * VERIFICAR OS ACESSOS */
	@Override
	public UserDetails loadUserByUsername(String loginUsuario) throws UsernameNotFoundException {
		
		ServiceUsuario servicoUsuario = CDIServiceLocator.getBean(ServiceUsuario.class);
		
		Usuario usuario = servicoUsuario.consultaPorLoginUsuario(loginUsuario);	
		
		
		UsuarioSistema user = null;

		// valida a o login e senha e verifica quais os perfis de acesso do mesmo
		// em seguida irá verificar no applicationContext que páginas web o grupo
		// do usuário pode acessar

		if (usuario != null) {			
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}else{ 
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		return user;
	}


	/** Responsável por identificar quais os grupos/permissoes que o usário está associado ou relacionado
	 * @param usuario
	 * @return */
	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		// adiciona em uma lista a lista de grupos dos usuários
		for (Grupo grupo : usuario.getGrupos()) {
			//authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
			authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
			
			//faz a iteração para encontrar as permissões do usuário
			for(Permissao permissao : grupo.getPermisoes()){
				authorities.add(new SimpleGrantedAuthority("ROLE_" + permissao.getNome()
						.toUpperCase()));
			}
			
		}

		return authorities;
	}

}
