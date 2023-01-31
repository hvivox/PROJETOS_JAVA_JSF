package com.br.painelmobile.util.mail;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import com.outjected.email.api.MailMessage;
import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.MailMessageImpl;

/**
 * ABRE UMA SEÇÃO DE EMAIL POR REQUISIÇÃO
 * @author hvivox
 *
 */

@RequestScoped
public class Mailer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject@Any
	private SessionConfig config;
	
	
	public Mailer(){
		
	}
	
	public MailMessage novaMensagem() {
		return new MailMessageImpl(this.config);
	}
	
}
