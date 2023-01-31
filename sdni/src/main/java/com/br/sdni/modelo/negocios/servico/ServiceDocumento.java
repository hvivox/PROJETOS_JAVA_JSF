package com.br.sdni.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.persistencia.dao.DaoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.util.cdi.qualifier.Transactional;


public class ServiceDocumento implements Serializable {
	//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
	private static final long serialVersionUID = 1L;

	@Inject
	private DaoDocumento daoDocumento;



	public ServiceDocumento() {

	}
	

	@Transactional
	public Documento salvarOuAtualizar(Documento cardapio) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoDocumento.salveOrUpdate(cardapio);
	}
	
	
	public Documento porId(Integer id) {
		// TODO Auto-generated method stub
		return daoDocumento.consultaPorId(id);
	}
	
	

	public List<Documento> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoDocumento.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public Documento consultaUltimoRegistroAtivo() {
		// TODO Auto-generated method stub
		return daoDocumento.consultaUltimoRegistroAtivo();
	}


	public Documento buscarPorIdDocumentoEGrupo(Integer id) {
		// TODO Auto-generated method stub
		return daoDocumento.buscarPorIdDocumentoEGrupo(id);
	}


	public int totalFiltrados(FiltroPesquisaPadrao filtro) {
		return daoDocumento.quantidadeFiltrados(filtro);
		
	}


	public List<Documento> pesquisaPorFiltro(FiltroPesquisaPadrao filtro) {
		return daoDocumento.consultarPorFiltrados(filtro);
	}
	
	
	
}
