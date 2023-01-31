package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoModAmArtes;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.DTO.DTODadosInscricaoAmArtes;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.ModAmArtes;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceModAmArtes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoModAmArtes daoModAmArtes;
	private List<DTODadosInscricaoAmArtes> listaDTODadosInscricaoAmArtes;
	private List<Object[]> listaDadosInscricaoObject;
	private DTODadosInscricaoAmArtes dtoDadosInscricaoAmArtes;
	private static final Integer LIMITE_VAGAS_POR_MODALIDADE = 10;

	public ServiceModAmArtes() {

	}


	@Transactional
	public ModAmArtes salvarOuAtualizar(ModAmArtes amArtes)			
			throws campoObrigatorioNaoPreenchido, ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, ObjetoTransienteSendoPersistido {
		return daoModAmArtes.salveOrUpdate(amArtes);
	}


	public ModAmArtes porId(Integer id) {
		// TODO Auto-generated method stub
		return daoModAmArtes.consultaPorId(id);
	}


	
	
	public List<DTODadosInscricaoAmArtes> consultarDadosInscricao(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		listaDTODadosInscricaoAmArtes = new ArrayList<DTODadosInscricaoAmArtes>();
		
		listaDadosInscricaoObject = daoModAmArtes
				.consultarDadosInscricao(filtroDePesquisa);
		
		
		for (Object[] objects : listaDadosInscricaoObject) {
			dtoDadosInscricaoAmArtes = new DTODadosInscricaoAmArtes();
			dtoDadosInscricaoAmArtes.setModalidade((ModAmArtes) objects[0]);
			
			Integer vagasLivresPorModalidade = calcularQtdVagasLivresPorModalidade(Integer
					.parseInt(objects[1].toString()));
			
			dtoDadosInscricaoAmArtes.setQtdVagasAbertas(vagasLivresPorModalidade);
			
			listaDTODadosInscricaoAmArtes.add(dtoDadosInscricaoAmArtes);
		}
		
		return listaDTODadosInscricaoAmArtes;
	}

	
	
	
	//METODO UTILIZADO PARA CALCULAR QUANTIDADE DE VAGAS LIVRES E APRESENTAR NA TELA INICIAL
	public Integer calcularQtdVagasLivresPorModalidade(Integer qtdInscritosPorModAmArtes) {
		Integer totalVagasLivre = LIMITE_VAGAS_POR_MODALIDADE - qtdInscritosPorModAmArtes;

		return totalVagasLivre;
	}

}
