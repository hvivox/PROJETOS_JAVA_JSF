package com.br.painelmobile.controle.mb;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbLogin")
@SessionScoped
public class MBLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	private String loginUsuario;


	/** verifica se o usuário ou a senha é invalido, caso isso aconteça será mostrada a mensagem para
	 * o usuário. O comando pega um parametro da url e verifica se é invalido*/
	public void preRender() {
		if ("true".equals(request.getParameter("invalid"))) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválido!");
		}
	}


	public void login() throws ServletException, IOException {
		// dipacha a tela de login do security (/j_spring_security_check) e
		// redireciona para outro lugar.
		// AQUI SÃO REDIRECIOANDOS OS METODOS DO SPRING
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);

		// encerra o ciclo de vida do jsf para passa a bola para o
		// springsecurity gerenciar o processo de login
		facesContext.responseComplete();
	}


	public String getLoginUsuario() {
		return loginUsuario;
	}


	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

}