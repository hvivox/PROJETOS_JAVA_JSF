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
import javax.ws.rs.core.Response;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServiceCardapio;
import com.br.painelmobile.modelo.negocios.servico.ServicePostagem;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOCardapio;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOCardapioDetalhado;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Postagem;
import com.br.painelmobile.util.manipularDados.ParserHtml;

@Named
@Path("/cardapio")
public class WSCardapio {

	@Inject
	private ServiceCardapio serviceCardapio;
	private Cardapio cardapio;

	@Inject
	private DTOCardapio dtoCardapio;
	private List<Cardapio> listCardapios = new ArrayList<Cardapio>();


	@Inject
	private ServicePostagem servicoPostagem;
	private static final int idPostagemCardapio = 1301;
	
	
	public WSCardapio() {
		
	}


	@GET
	@Path("exibir-cardapio")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DTOCardapio getExbirCardapio() throws WSTratamentoExcecaoGeral {
		try {

			// busca a informacao do banco
			cardapio = serviceCardapio.consultaUltimoRegistroAtivo();

			// para atendar um formato especifico do json foi necessario add em
			// um lista
			listCardapios.remove(cardapio);
			listCardapios.add(cardapio);

			// o dtoCardapio representa uma formato especico do json, isso foi
			// solicitado pelo cliente
			dtoCardapio.setCardapio(listCardapios);

			return dtoCardapio;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir o cardápio da semana. Por favor tente novamente mais tarde\"}");
		}
	}
	
	
	
	
	
	
	@GET
	@Path("exibir-cardapio-detalhado-do-dia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getExibirCardapioDoDia() throws WSTratamentoExcecaoGeral {
		DTOCardapioDetalhado cardapioDetalhado = new DTOCardapioDetalhado();
		Postagem postagem;
		
		try {
			
			// busca a informacao do banco
			cardapio = serviceCardapio.consultaUltimoRegistroAtivo();
		
			postagem = servicoPostagem.recuperarPorId(idPostagemCardapio);			

			
			//CONVERTE A POSTAGEM DE BIT PARA TEXT
			String conteudo = new String(postagem.getPostContent(),"UTF-8");
						
			cardapioDetalhado = ParserHtml.obterInformacoesDoCardapioWordpress(conteudo);
			cardapioDetalhado.setCardapio(cardapio);
			
			
			return Response.ok(cardapioDetalhado).type(MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			e.printStackTrace();
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

	}
	
	
	
	

}
