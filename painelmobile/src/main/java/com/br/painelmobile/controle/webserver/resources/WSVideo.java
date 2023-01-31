package com.br.painelmobile.controle.webserver.resources;

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
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.servico.ServiceVideo;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOVideo;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;

@Named
@Path("/video")
public class WSVideo {
	@Inject
	private ServiceVideo serviceVideo;
	
	private DTOVideo dtoVideo = new DTOVideo();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	//@Inject
	//private ConversorData conversorData;
	
	
	public WSVideo() {

	}


	@GET
	@Path("listar-videos")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DTOVideo getRetornaLista() throws WSTratamentoExcecaoGeral {

		try {

			filtro.setMostraInitivos(false);
			List<Video> listaVideos = serviceVideo.consultarPorFiltro(filtro);
			dtoVideo.setVideos(listaVideos);				
			
			return dtoVideo;

		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"erro\":\"No momento não é possível exibir as videoss do SESC. Por favor tente novamente mais tarde\"}");
		}
	}

	
	/** Adicionar e remove propriedades ao Json
	 * @param evento : nome do objeto a ser modificado
	 * @return um objeto array com Json */
	/*private Object manipularJsonEvento(Video video) {
		Gson gson = new Gson();
		Object jsonModificado;

		// adiciona uma nova propriedade ao Json
		JsonElement jsonElement = gson.toJsonTree(video);

		jsonElement.getAsJsonObject().addProperty("dtaPublicacaoString",
				conversorData.convertCalendarEmTexto(video.getDtaInclusaoVideo()));

		// Converte o Json para um objeto, isso retira a sugeira da string {"\"a\}
		jsonModificado = gson.fromJson(gson.toJson(jsonElement), Object.class);
		return jsonModificado;
	}*/
	
	
}
