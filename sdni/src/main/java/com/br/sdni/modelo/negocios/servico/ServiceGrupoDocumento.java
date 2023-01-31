package com.br.sdni.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.persistencia.dao.DaoGrupoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.cdi.qualifier.Transactional;

public class ServiceGrupoDocumento implements Serializable {
	// CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O
	// DAO GENERICO
	private static final long serialVersionUID = 1L;

	@Inject
	private DaoGrupoDocumento daoGrupoDocumento;


	public ServiceGrupoDocumento() {

	}


	@Transactional
	public GrupoDocumento salvarOuAtualizar(GrupoDocumento grupoDocumento)
			throws campoObrigatorioNaoPreenchido, ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, ObjetoTransienteSendoPersistido {
		return daoGrupoDocumento.salveOrUpdate(grupoDocumento);
	}


	public GrupoDocumento porId(Integer id) {
		// TODO Auto-generated method stub
		return daoGrupoDocumento.consultaPorId(id);
	}


	public List<GrupoDocumento> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoGrupoDocumento.consultarPorFiltrados(filtroDePesquisa);
	}


	public List<GrupoDocumento> consultarGruposAtivosOrdemAlfabetica() {
		return daoGrupoDocumento.consultarGruposAtivosOrdemAlfabetica();
	}
	
	
	public int quantidadeFiltrados(FiltroPesquisaPadrao filtro)  {
		return daoGrupoDocumento.quantidadeFiltrados(filtro);
		
	}


	public List<GrupoDocumento> filtrados(FiltroPesquisaPadrao filtro)  {
		return daoGrupoDocumento.consultarPorFiltrados(filtro);
		
	}

}
