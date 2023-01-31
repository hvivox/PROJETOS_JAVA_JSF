package com.br.sdni.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.br.sdni.modelo.negocios.excecao.EntityIdNuloException;
import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.persistencia.dao.DaoUsuario;
import com.br.sdni.modelo.persistencia.dao.filter.UsuarioFilter;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.sdni.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoUsuario daoUsuario;


	public ServiceUsuario() {

	}
	
	@Transactional
	public Usuario salvarOuAtualizar(Usuario usuario) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
	
		if (usuario.getId() != null && StringUtils.isBlank(usuario.getSenha())){
			usuario.setSenha(daoUsuario.buscarSenha(usuario.getId()));
		}		
		return daoUsuario.salveOrUpdate(usuario);
	}
	

	@Transactional	
	public void remover(Usuario usuario) throws ObjetoNaoEncontradoException, EntityIdNuloException {
			daoUsuario.removerObjetoPorId(usuario.getId());		
	}
	
	
	public Usuario porId(Integer id) {
		// TODO Auto-generated method stub
		return daoUsuario.consultaPorId(id);
	}
	


	public Usuario consultaPorLoginUsuario(String loginUsuario) {		
		return daoUsuario.consultaLoginUsuario(loginUsuario);
	}


	public int totalFiltrados(UsuarioFilter filtro) {		
		return daoUsuario.quantidadeFiltrados(filtro);
	}

	public List<Usuario> pesquisaPorFiltro(UsuarioFilter filtro) {		
		return daoUsuario.consultarPorFiltrados(filtro);
	}
	
	
	
	/*public List<Usuario> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
	throws ObjetoNaoEncontradoException {
		return daoUsuario.consultarPorFiltrados(filtroDePesquisa);
	}*/
	
	
	
}
