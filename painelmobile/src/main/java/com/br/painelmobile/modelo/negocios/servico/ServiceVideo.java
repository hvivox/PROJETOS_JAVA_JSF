package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoVideo;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceVideo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoVideo daoVideo;


	public ServiceVideo() {

	}
	

	@Transactional
	public Video salvarOuAtualizar(Video video) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoVideo.salveOrUpdate(video);
	}
	
	
	public Video porId(Integer id) {
		// TODO Auto-generated method stub
		return daoVideo.consultaPorId(id);
	}
	
	

	public List<Video> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoVideo.consultarPorFiltrados(filtroDePesquisa);
	}
	

	
}
