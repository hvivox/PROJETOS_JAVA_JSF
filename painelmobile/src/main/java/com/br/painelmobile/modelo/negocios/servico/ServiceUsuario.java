package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoUsuario;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

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
		return daoUsuario.salveOrUpdate(usuario);
	}
	
	
	public Usuario porId(Integer id) {
		// TODO Auto-generated method stub
		return daoUsuario.consultaPorId(id);
	}
	
	
	
	public List<Usuario> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoUsuario.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	
	public Usuario consultaPorLoginUsuario(String loginUsuario) {		
		return daoUsuario.consultaLoginUsuario(loginUsuario);
	}
	
	
}
