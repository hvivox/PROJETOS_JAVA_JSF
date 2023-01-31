package com.br.painelmobile.controle.webserver.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServiceEvento;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.util.manipularDados.ConversorData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Named
@Path("/evento")
public class WSEventos {

	@Inject
	private ConversorData conversorData;
	@Inject
	private ServiceEvento servicoEvento;
	private List<Evento> listaEvento = new ArrayList<Evento>();
	private List<Object> listaJsonModificado = new ArrayList<Object>();


	public WSEventos() {

	}

	
	
	/**
	 * LISTA TODOS OS EVENTOS - NÃO APAGAR 
	 * @return
	 * @throws WSTratamentoExcecaoGeral
	 */
	@GET
	@Path("listar-eventos")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Object> getListarEventos() throws WSTratamentoExcecaoGeral {
		
		
		try {
			listaEvento = servicoEvento.consultarEventosAtivoPorVigencia();
			
			for (Evento evento : listaEvento) {
				listaJsonModificado.add(manipularJsonEvento(evento));
			}
			
			return listaJsonModificado;
			

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir os eventos. Por favor tente novamente mais tarde\"}");
		}
	}
	
	
	
	/**
	 * MOSTRA APENAS OS EVENTOS DO DIA CORRENTE, UTILIZADO PARA FAZER A NOTIFICAÇÃO NO CELULAR DO USUÁRIO
	 * NAO APAGAR
	 * @return
	 * @throws WSTratamentoExcecaoGeral
	 */
	@GET
	@Path("exibir-evento-do-dia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Object> getExibirEventoDoDia() throws WSTratamentoExcecaoGeral {
		
		
		try {
			listaEvento = servicoEvento.exibirEventoDoDia();
			
			for (Evento evento : listaEvento) {
				listaJsonModificado.add(manipularJsonEvento(evento));
			}
			
			return listaJsonModificado;
			

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir os eventos. Por favor tente novamente mais tarde\"}");
		}
	}
	
	

	/** Adicionar e remove propriedades ao Json
	 * @param evento : nome do objeto a ser modificado
	 * @return um objeto array com Json */
	private Object manipularJsonEvento(Evento evento) {
		Gson gson = new Gson();
		Object jsonModificado;

		// adiciona uma nova propriedade ao Json
		JsonElement jsonElement = gson.toJsonTree(evento);

		jsonElement.getAsJsonObject().addProperty("dtaEventoString",
				conversorData.convertCalendarEmTexto(evento.getDtaEncerramento()));

		// remove uma propriedade do Json
		jsonElement.getAsJsonObject().remove("dtaEncerramento");

		// Converte o Json para um objeto, isso retira a sugeira da string {"\"a\}
		jsonModificado = gson.fromJson(gson.toJson(jsonElement), Object.class);
		return jsonModificado;
	}



}
