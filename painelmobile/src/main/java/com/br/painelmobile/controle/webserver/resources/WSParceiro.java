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
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaParceiro;
import com.br.painelmobile.modelo.negocios.servico.ServiceParceiro;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOCategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Parceiro;

@Named
@Path("/parceiro")
public class WSParceiro {
	@Inject
	private ServiceParceiro serviceParceiro;
	@Inject
	private ServiceCategoriaParceiro serviceCategoriaParceiro;
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	private List<CategoriaParceiro> listaCatParceiroModificado = new ArrayList<CategoriaParceiro>();
	private DTOCategoriaParceiro dtoCategoriaParceiro = new DTOCategoriaParceiro();
	private List<Parceiro> listaParceiroModificada = new ArrayList<Parceiro>();
	
	
	public WSParceiro() {
		
	}	
	
	
	@GET
	@Path("listar-categoria-parceiros")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListarCategoriaParceiros() throws WSTratamentoExcecaoGeral {

		try {

			listaCatParceiroModificado.clear();
			// Fitro para pegar categorias com qualquer tipo de nome "%%"
			filtro.setTitulo("");
			List<CategoriaParceiro> listaCategoriaParceiros = serviceCategoriaParceiro
					.consultarPorFiltro(filtro);

			for (CategoriaParceiro categoriaParceiro : listaCategoriaParceiros) {

				// json irá exibir apenas categorias que tenha algum serviço
				// associado
				if (categoriaParceiro.getParceiros().size() > 0) {
					categoriaParceiro.setParceiros(null);
					listaCatParceiroModificado.add(categoriaParceiro);
				}
			}

			// retorna um json. em um formato personalizado apenas com as
			// categorias que tem
			// serviços associados
			dtoCategoriaParceiro.setCategoriasDeParceiro(listaCatParceiroModificado);

			// esta é uma forma diferente de envio de informação. A diferença é
			// a possibilidade de setar informações no cabeçalho do http
			return Response.ok(dtoCategoriaParceiro).encoding("UTF-8")
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
	@Path("listar-parceiros-por-idCategoria/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Parceiro> getListarParceirosPorId(
			@NotNull(message = "Entrada de dados vazia") @PathParam("id") Integer id)
			throws WSTratamentoExcecaoGeral {

		try {
			List<Parceiro> listaParceiros = serviceParceiro.consultarParceiroPorIdCategoria(id);
		
			
			for (Parceiro parceiro : listaParceiros) {
				parceiro.setCatParceiro(null);
				listaParceiroModificada.add(parceiro);
			}

			return listaParceiroModificada;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as Galerias. Por favor tente novamente mais tarde\"}");
		}
	}
	
	
	/**
	 * LISTA TODOS OS PARCEIROS
	 * @return
	 * @throws WSTratamentoExcecaoGeral
	 */
	@GET
	@Path("listar-parceiros")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListarParceiros() throws WSTratamentoExcecaoGeral {

		try {
			
			filtro.setMostraInitivos(false);
			List<Parceiro> listaParceiros = serviceParceiro.consultarPorFiltro(filtro);
			
			// evita o problema de at
			// com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:505)
			for (Parceiro servico : listaParceiros) {
				servico.setCatParceiro(null);
				listaParceiroModificada.add(servico);
			}

			// esta é uma forma diferente de envio de informação. A diferença é
			// a possibilidade de setar informações no cabeçalho do http
			return Response.ok(listaParceiros).encoding("UTF-8").type(MediaType.APPLICATION_JSON)
					.build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir os parceiros. Por favor tente novamente mais tarde\"}");
		}
	}

	
	

}
