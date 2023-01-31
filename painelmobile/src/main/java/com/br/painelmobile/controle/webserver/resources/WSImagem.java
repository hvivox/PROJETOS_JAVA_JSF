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

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServiceImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


@Named
@Path("/imagemgaleria")
public class WSImagem {	
	
	@Inject
	private ServiceImagem serviceImagem;
	private List<Imagem> listaImagem  = new ArrayList<Imagem>();
	
	
	
	public WSImagem() {

	}
	

	@GET
	@Path("listar-imagens-por-id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Object> getRetornaImagemPorId(@NotNull(message="Entrada de dados vazia")@PathParam("id")Integer id) throws WSTratamentoExcecaoGeral {
		
		try{			
		
		List<Object> arrayJsonImagem = new ArrayList<Object>(); 
		
		listaImagem = serviceImagem.consultarImagensPorGaleria(id);
			
		for (Imagem imagem : listaImagem) {	
			imagem.setGaleria(null);
			arrayJsonImagem.add(manipularJsonEvento(imagem));							
		}
		
		return arrayJsonImagem;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as Galerias. Por favor tente novamente mais tarde\"}");
		}
	}
		
	

	/**
	 * Adicionar e remove propriedades ao Json
	 * 
	 * @param imagem
	 *            : nome do objeto a ser modificado
	 * @return um objeto array com Json
	 */
	private Object manipularJsonEvento(Imagem imagem) {
		Gson gson = new Gson();
		Object jsonModificado;
		
		// adiciona uma nova propriedade ao Json
		JsonElement jsonElement = gson.toJsonTree(imagem);
		
		/*jsonElement.getAsJsonObject().addProperty("idGaleria",
				imagem.getGaleria().getId());*/
		
		// remove uma propriedade do Json
		jsonElement.getAsJsonObject().remove("galeria");
		
		// Converte o Json para um objeto, isso retira a sugeira da string {"\"a\}
		jsonModificado = gson.fromJson(gson.toJson(jsonElement), Object.class);
		return jsonModificado;
	}
	
	
	/*	
	@GET
	@Path("listar-imagens")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Object> getRetornaListarImage() throws WSTratamentoExcecaoGeral {
		
		List<Object> arrayJsonImagem = new ArrayList<Object>(); 
		
		listaImagem = simulaListaImagen();		
		
		
		if (listaImagem instanceof Object && listaImagem != null) {
			for (Imagem imagem : listaImagem) {
					
				arrayJsonImagem.add(manipularJsonEvento(imagem));
			}			
			return arrayJsonImagem;
		
		} else {
			throw new WSTratamentoExcecaoGeral("{\"erro\":\"No momento não é possível exibir as imagens. Por favor tente novamente mais tarde\"}");
		}
		
	}	*/
	

}
