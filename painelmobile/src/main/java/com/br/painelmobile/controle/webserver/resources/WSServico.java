package com.br.painelmobile.controle.webserver.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaServico;
import com.br.painelmobile.modelo.negocios.servico.ServiceServico;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOCategoriaServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Servico;

@Named
@Path("/servico")
public class WSServico {

	@Inject
	private ServiceServico serviceServico;
	@Inject
	private ServiceCategoriaServico serviceCategoriaServico;
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	private List<CategoriaServico> listaCatServicoModificado = new ArrayList<CategoriaServico>();
	private DTOCategoriaServico dtoCategoriaServico = new DTOCategoriaServico();
	private List<Servico> listaServicoModificada = new ArrayList<Servico>();


	public WSServico() {

	}


	@GET
	@Path("listar-categoria-servicos")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListarCategoriaServicos() throws WSTratamentoExcecaoGeral {

		try {

			listaCatServicoModificado.clear();
			// Fitro para pegar categorias com qualquer tipo de nome "%%"
			filtro.setTitulo("");
			List<CategoriaServico> listaCategoriaServicos = serviceCategoriaServico
					.consultarPorFiltro(filtro);

			for (CategoriaServico categoriaServico : listaCategoriaServicos) {

				// json irá exibir apenas categorias que tenha algum serviço
				// associado
				if (categoriaServico.getServicos().size() > 0) {
					categoriaServico.setServicos(null);
					listaCatServicoModificado.add(categoriaServico);
				}
			}

			// retorna um json. em um formato personalizado apenas com as
			// categorias que tem
			// serviços associados
			dtoCategoriaServico.setCategoriasDeServico(listaCatServicoModificado);

			// esta é uma forma diferente de envio de informação. A diferença é
			// a possibilidade de setar informações no cabeçalho do http
			return Response.ok(dtoCategoriaServico).encoding("UTF-8")
					.type(MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as categorias de serviços. Por favor tente novamente mais tarde\"}");
		}
	}


	/**
	 * LISTA OS SERVIÇOS ASSOCIADOS AO ID DA CATEGORIA
	 * @param id da categoria passado pelo cliente
	 * @return
	 * @throws WSTratamentoExcecaoGeral
	 */
	@GET
	@Path("listar-servicos-por-idCategoria/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Servico> getListarServicosPorId(
			@NotNull(message = "Entrada de dados vazia") @PathParam("id") Integer id)
			throws WSTratamentoExcecaoGeral {

		try {
			List<Servico> listaServicos = serviceServico.consultarServicoPorIdCategoria(id);

			for (Servico servico : listaServicos) {
				servico.setCatServico(null);
				listaServicoModificada.add(servico);
			}

			return listaServicoModificada;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as Galerias. Por favor tente novamente mais tarde\"}");
		}
	}


	
}
