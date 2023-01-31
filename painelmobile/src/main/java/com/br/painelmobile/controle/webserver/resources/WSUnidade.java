package com.br.painelmobile.controle.webserver.resources;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.servico.ServiceUnidade;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOUnidade;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Unidade;

@Path("/unidade")
@Named
public class WSUnidade {
	private CacheControl control = new CacheControl();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	@Inject
	private ServiceUnidade serviceUnidade;


	// @DEFALT já está sendo utilizado por outro classe por isso tive que usar o
	// @ANY outra solução seria criar um novo Qualifier

	public WSUnidade() {

	}


	@GET
	@Path("listar-unidades")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRetornaLista() throws WSTratamentoExcecaoGeral {

		try {

			filtro.setMostraInitivos(false);
			List<Unidade> listaUnidades = serviceUnidade.consultarPorFiltro(filtro);
			DTOUnidade dtoUnidade = new DTOUnidade(listaUnidades);
			return Response.ok(dtoUnidade).cacheControl(control).encoding("UTF-8").type(MediaType.APPLICATION_JSON)
					.build();

		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as unidades. Por favor tente novamente mais tarde\"}");
		}
	}
	
	
	/*	public List<Unidade> simulaBancoDeDados() {
		List<Unidade> lista = new ArrayList<Unidade>();
		Unidade unidade1 = new Unidade(
				1,
				"SEDE01",
				"Manaus",
				"(92) 2126-9580",
				"-3.133707",
				"-60.0246123",
				EstatusEntidadeEnum.ATIVO,
				"http://files.softicons.com/download/system-icons/touchit-3-icons-by-steven-zangh/png/60x60/Phone.png");

		Unidade unidade2 = new Unidade(
				2,
				"CENTRO DE ATIVIDADES DANILO DE MATOS AREOSA",
				"Manaus",
				"(92) 2126-9501",
				"-3.133707",
				"-60.0246123",
				EstatusEntidadeEnum.ATIVO,
				"http://files.softicons.com/download/system-icons/touchit-3-icons-by-steven-zangh/png/48x48/SMS.png");

		Unidade unidade3 = new Unidade(
				3,
				"RESTAURANTE DO COMERCIÁRIO",
				"Manaus",
				"(92) 2126-9511",
				"-3.133707",
				"-60.0246123",
				EstatusEntidadeEnum.ATIVO,
				"http://files.softicons.com/download/system-icons/touchit-3-icons-by-steven-zangh/png/48x48/Maps.png");

		Unidade unidade4 = new Unidade(
				4,
				"SESC BALNEÁRIO",
				"Manaus",
				"(92) 2121-5353",
				"-3.0695981",
				"-60.0479328",
				EstatusEntidadeEnum.ATIVO,
				"http://files.softicons.com/download/system-icons/touchit-3-icons-by-steven-zangh/png/48x48/Photos.png");

		Unidade unidade5 = new Unidade(
				5,
				"SESC CIDADE NOVA",
				"Manaus",
				"(92) 3641-4929",
				"-3.133707",
				"-60.0246123",
				EstatusEntidadeEnum.ATIVO,
				"http://files.softicons.com/download/system-icons/touchit-3-icons-by-steven-zangh/png/48x48/Calculator.png");

	 Unidade unidade6 = new Unidade(6, "CENTRO DE ATIVIDADES FAUSTO VENTURA", "Manacapuru",
		 * "(92) 3361-1456", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade7 = new Unidade(7, "CENTRO EDUCACIONAL SESC - RAFAEL AZIZI RESTAURAÇÃO",
		 * "Manacapuru", "(92) 3361-1464", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade8 = new Unidade(8, "CENTRO EDUCACIONAL SESC - ABDUL RAZAE HAUACHE",
		 * "Itacoatiara", "(92) 3521-2466", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade9 = new Unidade(9, "CENTRO EDUCACIONAL SESC - J.G ARAÚJO", "Parintins",
		 * "(92) 3533-3565", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade10 = new Unidade(10, "CENTRO EDUCACIONAL - FERNANDO MATOS DE SOUZA",
		 * "Presidente Figueiredo", "(92) 3324-1269", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade11 = new Unidade(11, "CENTRO EDUCACIONAL - CLÓVIS PRADO DE NEGREIROS",
		 * "Maués", "(92) 3542-1451", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade12 = new Unidade(12, "CENTRO EDUCACIONAL - MANSOUR FRANCIS CHEHUAN",
		 * "Tefé", "(97) 3343-4561", EstatusEntidadeEnum.ATIVO);
		 * 
		 * Unidade unidade13 = new Unidade(13, "CENTRO EDUCACIONAL - OESEL NUNES TORRES", "Coarí",
		 * "(97) 3561-3203", EstatusEntidadeEnum.ATIVO); */
/*
		lista.add(unidade1);
		lista.add(unidade2);
		lista.add(unidade3);
		lista.add(unidade4);
		lista.add(unidade5);
		// lista.add(unidade6);
		// lista.add(unidade7);
		// lista.add(unidade8);
		// lista.add(unidade9);
		// lista.add(unidade10);
		// lista.add(unidade11);
		// lista.add(unidade12);
		// lista.add(unidade13);
		//
		return lista;

	}*/

}
