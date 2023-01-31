package com.br.painelmobile.controle.webserver.excecoes;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



/**
 * @author hvivox
 *Classe utilizada como um HandleException com o objetivo de devolver ao cliente uma resposta
 *sobre as excessões
 */
@Provider
public class WSExceptionHandler implements ExceptionMapper<WSTratamentoExcecaoGeral>{

	@Override
	public Response toResponse(WSTratamentoExcecaoGeral exception) {		
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}

}
