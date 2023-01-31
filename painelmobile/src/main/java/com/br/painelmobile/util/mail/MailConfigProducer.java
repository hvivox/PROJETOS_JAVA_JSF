package com.br.painelmobile.util.mail;



import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

/**
 * CONFIGURA A SECAO COM DADOS DO EMAIL
 * LER O ARQUIVO MAIL.PROPERTIES QUE ESTA EM RESOUCES
 * @author hvivox
 *
 */
public class MailConfigProducer implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Produces@Any
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/mail.properties"));
		
		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(props.getProperty("mail.server.host"));
		config.setServerPort(Integer.parseInt(props.getProperty("mail.server.port")));
		config.setEnableSsl(Boolean.parseBoolean(props.getProperty("mail.enable.ssl")));
		config.setEnableTls(Boolean.parseBoolean(props.getProperty("mail.enable.tls")));
		config.setAuth(Boolean.parseBoolean(props.getProperty("mail.auth")));
		config.setUsername(props.getProperty("mail.username"));
		config.setPassword(props.getProperty("mail.password"));
		
		return config;
	}
	
}
