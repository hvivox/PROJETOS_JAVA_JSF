package com.br.formulario.controle.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.CicloFaixa;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DancaAmazonas;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DifusaoDanca;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.FestivalNovosTalentos;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PortaAberta;
import com.br.formulario.util.mail.Mailer;
import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
public class MBEnvioConfirmacaoInscricao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;

	public void enviarPedido(DancaAmazonas dancaAmazonas) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(dancaAmazonas.getEmail())
				.cc("hermogenes.silva@sesc-am.com.br")	
				.cc("jennyfer.lages@sesc-am.com.br")
				.subject("INSCRIÇÃO DANÇA AMAZONAS - " + dancaAmazonas.getNomeGrupo())
				.envelopeFrom("informacoes.sesc@sesc-am.com.br")
				.bodyHtml(
						"<strong>Nome Grupo:</strong> " + dancaAmazonas.getNomeGrupo()
								+ "<br/><br/>" + "Parabéns sua inscrição está confirmada!"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}
	
	
	public void enviarPedidoAmazonasArtes(AmazonasArtes amazonasArtes) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(amazonasArtes.getEmail())
			.envelopeFrom("informacoes.sesc@sesc-am.com.br")	
			.cc("hermogenes.silva@sesc-am.com.br")	
			.cc("denise.vicentim@sesc-am.com.br")
			.cc("jennyfer.lages@sesc-am.com.br")
				.subject("INSCRIÇÃO AMAZONAS DAS ARTES- " + amazonasArtes.getNomeGrupo())
				
				.bodyHtml(
						"<strong>Nome Grupo:</strong> " + amazonasArtes.getNomeGrupo()
								+ "<br/><br/>" + "Parabéns sua inscrição está confirmada!"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}


	public void enviarPedidoFestivalNovosTalentos(FestivalNovosTalentos festivalNovosTalentos) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(festivalNovosTalentos.getEmail())
			.envelopeFrom("informacoes.sesc@sesc-am.com.br")	
			.cc("hermogenes.silva@sesc-am.com.br")	
			.cc("izis.andrade@sescamazonas.onmicrosoft.com")
			.cc("izis.cruz@sesc-am.com.br")			
				.subject("INSCRIÇÃO FESTIVAL DE CALOUROS - " + festivalNovosTalentos.getNome())				
				.bodyHtml(
						"<strong>Candidato:</strong> " + festivalNovosTalentos.getNome()
								+ "<br/><br/>" + "<h3>Parabéns sua inscrição está confirmada!</h3>"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		
	}
	
	
	
	public void enviarEmailPortaAberta(PortaAberta portaAberta) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(portaAberta.getEmail())
			.envelopeFrom("informacoes.sesc@sesc-am.com.br")	
			.cc("hermogenes.silva@sesc-am.com.br")
			.cc("zeudimar.souza@sesc-am.com.br")
			.cc("jennyfer.lages@sesc-am.com.br")
				.subject("[INSCRIÇÃO PORTAS ABERTAS] " + portaAberta.getNomeGrupo())				
				.bodyHtml(
						"<strong>Nome Grupo:</strong> " + portaAberta.getNomeGrupo()
								+ "<br/><br/>" + "<h3>Parabéns sua inscrição está confirmada!</h3>"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		
	}
	
	
	public void enviarEmailDifusaoDanca(DifusaoDanca difusaoDanca) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(difusaoDanca.getEmail())
			.envelopeFrom("informacoes.sesc@sesc-am.com.br")	
			.cc("hermogenes.silva@sesc-am.com.br")
			.cc("jennyfer.lages@sesc-am.com.br")
				.subject("[INSCRIÇÃO PROJETO SESC DIFUSÃO DE DANÇA] " + difusaoDanca.getNome())				
				.bodyHtml(
						"<strong>Nome:</strong> " + difusaoDanca.getNome()
								+ "<br/><br/>" + "<h3>Parabéns sua inscrição está confirmada!</h3>"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		
	}
	
	
	public void enviarEmailCicloFaixa(CicloFaixa cicloFaixa) {
		MailMessage message = mailer.novaMensagem();
		
		message.to(cicloFaixa.getEmail())
			.envelopeFrom("informacoes.sesc@sesc-am.com.br")	
			.cc("hermogenes.silva@sesc-am.com.br")
			.cc("lisandra.xavier@sesc-am.com.br")
				.subject("[INSCRIÇÃO CICLO FAIXA] - " + cicloFaixa.getNome())				
				.bodyHtml(
						"<strong>Nome:</strong> " + cicloFaixa.getNome()
								+ "<br/><br/>" + "<h3>Parabéns sua inscrição está confirmada!</h3>"
								+ "<br/><br/>" + "Mensagem Automática - via sistema de inscrição").send();		
		// FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		
	}
	
	
	
	
	
	
}
