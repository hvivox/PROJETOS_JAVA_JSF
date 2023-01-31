package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoModalidadeDancaAmazonas;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.DTO.DTODadosInscricao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.Modalidade;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceModalidadeDancaAmazonas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoModalidadeDancaAmazonas daoModalidadeDancaAmazonas;
	private List<DTODadosInscricao> listaDTODadosInscricao;
	private List<Object[]> listaDadosInscricaoObject;
	private DTODadosInscricao dtoDadosInscricao;
	private static final Integer LIMITE_VAGAS_POR_MODALIDADE = 10;

	public ServiceModalidadeDancaAmazonas() {

	}


	@Transactional
	public Modalidade salvarOuAtualizar(Modalidade modalidade)
			throws campoObrigatorioNaoPreenchido, ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, ObjetoTransienteSendoPersistido {
		return daoModalidadeDancaAmazonas.salveOrUpdate(modalidade);
	}


	public Modalidade porId(Integer id) {
		// TODO Auto-generated method stub
		return daoModalidadeDancaAmazonas.consultaPorId(id);
	}


	public List<DTODadosInscricao> consultarDadosInscricao(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		listaDTODadosInscricao = new ArrayList<DTODadosInscricao>();
		
		listaDadosInscricaoObject = daoModalidadeDancaAmazonas
				.consultarDadosInscricao(filtroDePesquisa);
		
		
		for (Object[] objects : listaDadosInscricaoObject) {
			dtoDadosInscricao = new DTODadosInscricao();
			dtoDadosInscricao.setModalidade((Modalidade) objects[0]);
			
			Integer vagasLivresPorModalidade = calcularQtdVagasLivresPorModalidade(Integer
					.parseInt(objects[1].toString()));
			
			dtoDadosInscricao.setQtdVagasAbertas(vagasLivresPorModalidade);

			listaDTODadosInscricao.add(dtoDadosInscricao);
		}
		
		return listaDTODadosInscricao;
	}

	
	//METODO UTILIZADO PARA CALCULAR QUANTIDADE DE VAGAS LIVRES E APRESENTAR NA TELA INICIAL
	public Integer calcularQtdVagasLivresPorModalidade(Integer qtdInscritosPorModalidade) {
		Integer totalVagasLivre = LIMITE_VAGAS_POR_MODALIDADE - qtdInscritosPorModalidade;

		return totalVagasLivre;
	}

}
