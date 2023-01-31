package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoCategoriaServico;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceCategoriaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoCategoriaServico daoCategoriaServico;


	public ServiceCategoriaServico() {

	}
	

	@Transactional
	public CategoriaServico salvarOuAtualizar(CategoriaServico categoriaServico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoCategoriaServico.salveOrUpdate(categoriaServico);
	}
	
	
	public CategoriaServico porId(Integer id) {
		// TODO Auto-generated method stub
		return daoCategoriaServico.consultaPorId(id);
	}
	
	

	public List<CategoriaServico> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoCategoriaServico.consultarPorFiltrados(filtroDePesquisa);
	}
	

	
}
