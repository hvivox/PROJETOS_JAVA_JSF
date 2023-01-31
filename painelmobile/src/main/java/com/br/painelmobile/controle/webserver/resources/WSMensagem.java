package com.br.painelmobile.controle.webserver.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Mensagem;
import com.br.painelmobile.util.mail.Mailer;
import com.br.painelmobile.util.manipularDados.ValidaEmail;
import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
@Path("/mensagem")
public class WSMensagem  {

	@Inject
	private Mailer mailer;


	public WSMensagem() {
		
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enviar-mensagem")
	public String enviarMensagem(Mensagem mensagem) throws WSTratamentoExcecaoGeral {
		if (mensagem != null && isNotCampoVazio(mensagem)
				&& ValidaEmail.isEmailValido(mensagem.getEmail())) {

			MailMessage message = mailer.novaMensagem();

			message.to("informacoes.sesc@sesc-am.com.br")
					.subject("PAINEL MOBILE - "+mensagem.getAssunto())
					.envelopeFrom(mensagem.getEmail())
					.bodyHtml(
							"<strong>Nome Remetente:</strong> " + mensagem.getNomeRemetente() + "<br/><br/>" + 							 
							 mensagem.getConteudo()+ "<br/><br/>"+
									"Mensagem encaminhada via Painel Mobile").send();
		}
		else {
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"Favor preencher corretamente todos os campos\"}");
		}
		return "{\"status\":\"200\",\"mensagem\":\"Mensagem enviada com sucesso\"}";
	}


	
	
	/** Verificar se o campo os campos são nulos ou vazios
	 * @param mensagem
	 * @return */
	public Boolean isNotCampoVazio(Mensagem mensagem) {
		boolean resultado = false;
		if (StringUtils.isNotBlank(mensagem.getNomeRemetente())
				&& StringUtils.isNoneBlank(mensagem.getAssunto())
				&& StringUtils.isNoneBlank(mensagem.getConteudo())
				&& StringUtils.isNoneBlank(mensagem.getEmail())) {

			resultado = true;
		}

		return resultado;
	}

}