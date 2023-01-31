package com.br.sdni.util.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*UTILIZADO PARA INICIALIZAR O SPRING SECURITY*/
public class SecurityWebApplicationInitializer  extends AbstractSecurityWebApplicationInitializer{

	public SecurityWebApplicationInitializer(){		
		//passa ao spring a classe com toda a programação criada
		super(SecurityConfig.class);
	}
	
}
