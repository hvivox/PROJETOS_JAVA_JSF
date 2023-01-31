package com.br.painelmobile.controle.webserver.resources;

import java.io.Serializable;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServicePostagem;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOListaDeNoticia;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTONoticia;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Postagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.PostagemComCategoria;
import com.br.painelmobile.util.manipularDados.ParserHtml;

@Named
@Path("/postagem-portal")
public class WSPostagem implements Serializable {
	@Inject
	private ServicePostagem servicoPostagem;
	private static final long serialVersionUID = 1L;
	// defini a quantidade noticias que será mostradas ou listadas
	private static final int qtdDeNoticias = 6;
	private static final int idPostagemCardapio = 1301;
	private static final int idPostagemPoliticaPrivacidade = 654;
	private List<DTONoticia> listaNoticia = new ArrayList<DTONoticia>();

	private DTOListaDeNoticia dtoListaDeNoticias = new DTOListaDeNoticia();


	public WSPostagem() {

	}


	/**
	 * classe utilizada para retornar apenas as 6 ultimas noticias
	 * 
	 * @return List<noticias>
	 * @throws WSTratamentoExcecaoGeral
	 * @throws ConsultaInvalidaException
	 * @throws ObjetoNaoEncontradoException
	 */
	@GET
	@Path("listar-noticias")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getConsultaNoticias() throws WSTratamentoExcecaoGeral {
		List<Postagem> listaPostagens;

		try {
			listaPostagens = servicoPostagem.buscarPostagemPorQuantidade(qtdDeNoticias);

			// percorre a lista para personalizar as informações
			for (Postagem postagem : listaPostagens) {
				DTONoticia noticia = new DTONoticia();
				
				/*SOLUÇÃO PROBLEMA DE CODIFICAÇÃO DA INFORMAÇÃO
				 * os dois campos abaixo são do tipo byte[] pois só assim é
				 * possível converter do formato errado do banco para UTF-8
				 * Abaixo os byte estão sendo convertido em String, é necessário
				 * setar esta informação
				 * useUnicode=yes&amp;characterEncoding=UTF-8"na string de
				 * conexão do persistence.xml para informar ao sistema qual o
				 * charset será utilizado
				 */
				
				// String titulo = new String(postagem.getPostTitle(),"UTF-8");
				String titulo = new String(postagem.getPostTitle(),"UTF-8");
				String conteudo = new String(postagem.getPostContent(),"UTF-8");				
				noticia.setId(postagem.getId());
				noticia.setTitulo(titulo);
				noticia.setUriImagem(definirUriImagem(conteudo));
				noticia.setParteTexto(formatarParteDoTexto(conteudo));
				noticia.setHtmlTexto(ParserHtml.removeTagImgdoHtml(conteudo));
				
				listaNoticia.add(noticia);

			}

			dtoListaDeNoticias.setListaNoticias(listaNoticia);
						
			return Response.ok(dtoListaDeNoticias).type(MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

	}


	
	
	
	/**
	 * classe utilizada para retornar apenas as 6 ultimas noticias para 
	 * servir o novo app
	 * 
	 * @return List<noticias>
	 * @throws WSTratamentoExcecaoGeral
	 * @throws ConsultaInvalidaException
	 * @throws ObjetoNaoEncontradoException
	 */
	@GET
	@Path("listar-ultimos-destaques")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListarUltimosDestaques() throws WSTratamentoExcecaoGeral {
		List<PostagemComCategoria> listaPostagens;

		try {
			listaPostagens = servicoPostagem.buscarPostagemComCategoriaPorQuantidade(qtdDeNoticias);

			// percorre a lista para personalizar as informações
			for (PostagemComCategoria postagemComCategoria : listaPostagens) {
				
				DTONoticia noticia = new DTONoticia();				
				
				String titulo = new String(postagemComCategoria.getTitulo(),"UTF-8");
				String conteudo = new String(postagemComCategoria.getConteudo(),"UTF-8");
				
				noticia.setId(postagemComCategoria.getIdpostagem());
				noticia.setTitulo(titulo);
				noticia.setLinkPublicacao( postagemComCategoria.getLinkDaPostagem() );
				noticia.setUriImagem(definirUriImagem(conteudo));
				noticia.setParteTexto(formatarParteDoTexto(conteudo));
				//noticia.setHtmlTexto(ParserHtml.removeTagImgdoHtml(conteudo));
				String conteudoComShortCodeCaption_ConvertidaParaHtml = ParserHtml.convertShortCodeEmHtml(conteudo);				
				noticia.setHtmlTexto( ParserHtml.removeTagImgdoHtml( conteudoComShortCodeCaption_ConvertidaParaHtml ) );
				
				noticia.setCategoria(postagemComCategoria.getCategoria());
				listaNoticia.add(noticia);

			}

			dtoListaDeNoticias.setListaNoticias(listaNoticia);

			return Response.ok(dtoListaDeNoticias).type(MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

	}
	
			
	
	
	
	@GET
	@Path("exibir-politica-privacidade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getExibirCardapioDoDia() throws WSTratamentoExcecaoGeral {
		DTONoticia noticia = new DTONoticia();
		Postagem postagem;

		try {
			postagem = servicoPostagem.recuperarPorId(idPostagemPoliticaPrivacidade);			
			
			// String titulo = new String(postagem.getPostTitle(),"UTF-8");
			String titulo = new String(postagem.getPostTitle(),"UTF-8");
			String conteudo = new String(postagem.getPostContent(),"UTF-8");
			
			noticia.setId(postagem.getId());
			noticia.setTitulo(titulo);
			noticia.setLinkPublicacao( postagem.getGuid() );
			noticia.setUriImagem(definirUriImagem(conteudo));
			noticia.setParteTexto(formatarParteDoTexto(conteudo));
			
			noticia.setHtmlTexto(ParserHtml.removeTagImgdoHtml(conteudo));
			
			
			return Response.ok(noticia).type(MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

	}
	
	

	
	/**
	 * Formatar texto para retirar html e reduzir o numero de caracteres para
	 * 200
	 * 
	 * @param parteTexto
	 * @return
	 */
	private String formatarParteDoTexto(String parteTexto) {
		String textoProcessado = ParserHtml.converterHtmlEmTexto_RemoveTodasAsTags(parteTexto);
		
		//textoProcessado = ParserHtml.removerShortCodeCaptionDoTexto(textoProcessado);
		//String textoProcessado = ParserHtml.converterHtmlEmTexto(parteTexto);
		// caso o texto tenha menos que 200(nao retorna nulo) caracteres ou
		// nenhum (_) o sistema irá tratar
		return (StringUtils.defaultIfEmpty(StringUtils.substring(textoProcessado, 0, 200), "_") + "....");
	}
	

	/**
	 * utilizado para definir a uri de uma unica imagem mesmo se houver mais de
	 * uma imagem, só será retornada uma ou null
	 * @param textoHtml
	 * @return urI de localização da imagem
	 */
	private String definirUriImagem(String textoHtml) {
		// localiza todas as uri referente a imagens
		List<String> uriImagem = ParserHtml.obterUrldaImagem(textoHtml);

		// evita que o sistema receba mais de uma imagem por noticia
		if (uriImagem.size() <= 0) {
			return null;
		} else {
			return uriImagem.get(0);
		}

	}


	@GET
	@Path("exibir-postagem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Postagem getExbirCardapio() throws WSTratamentoExcecaoGeral {
		try {

			// busca a informacao do banco
			Postagem postagem = servicoPostagem.recuperarPorId(9209);

			//String output = new String(postagem.getPostTitle());
			
			return postagem;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"erro\":\"No momento não é possível exibir o cardápio da semana. Por favor tente novamente mais tarde\"}");
		}
	}

}
