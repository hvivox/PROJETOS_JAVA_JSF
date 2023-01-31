package com.br.painelmobile.util.managerbean.excecaojsf;

import java.io.IOException;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



//Esta classe irá sobreescrever a classe já utilizada pelo JSF
public class JsfExceptionHandler extends ExceptionHandlerWrapper {
	
	//será capturado apenas exceções do JSF da classe JSFExceptionHandler
	//Registra o nome da classe do qual o log será registrado
	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);
	private ExceptionHandler wrapped;
	
	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	
	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents()
				.iterator();
		
		//IRÁ PERCORRER TODOS OS EVENTOS ENCONTRADOS E TRATA-LOS CASO ENCONTRE 
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();

			Throwable exception = context.getException();			
			//é uma flag que verificaq que ocorreu ou não a viewExpired
			boolean handled = false;

			
			try {
				// Se a exception for do tipo ViewExpired ela entra na condição e direciona para a tela inicial (web.xml - welcome)
				if (exception instanceof ViewExpiredException) {
					handled = true;
					redirect("/");
					//primeiro parametro é uma mensagem do erro e o segundo parametro é a causa do erro
					log.error("\n EXIBIDO PELO LOG4J - SESSÃO EXPRIDADA: "
							+ "" + exception.getMessage(), exception);
				}
				//Se for encontrado qualquer erro diferente do ViewExpiredException ser encaminhado para abaixo
				else {
					handled = true;	
					//primeiro parametro é uma mensagem do erro e o segundo parametro é a causa do erro
					log.error("\n EXIBIDO PELO LOG4J: " +exception.getMessage(), exception);					
					redirect("/error/500.xhtml");
					
				}

			} finally {
				//Os eventos serão removidos apenas se for encontrado o ViewExpiredException
				if (handled) {					
					//remove todos os eventos (msg de erro)
					events.remove();
				}

			}
		}
		
		//passa novamente a responsabilidade para a classe original fazer seguir com o tratamento de erros
		getWrapped().handle();
	}

	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();

			// Seta o caminho ao qual o usuário será direcionado hermes/
			externalContext.redirect(contextPath + page);
			
			// utilizado para informar que a resposta está completa e evitar
			// outro processamento do JSF
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}