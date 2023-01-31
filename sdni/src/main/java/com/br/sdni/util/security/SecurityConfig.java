package com.br.sdni.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private AppUserDetailsService userDetailsService;


	/*
	 * CLASSES DE CONFIGURAÇÃO DO SPRING
	 * SECURITY#################################################################
	 * ## --> <!--registrando a classe AppUserDetailsService como um bean cdi.
	 * Classe utilizada para validar o login senha e grupo o usuário que está
	 * tentando se logar
	 */
	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* CONFIGURAÇÕES DO OMIFACES PARA RESOLVER O PROBLEMA COM AJAX */
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntity = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntity.setLoginFormUrl("/login.xhtml");
		jsfLoginEntity.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDenieEntry = new JsfAccessDeniedHandler();
		jsfDenieEntry.setLoginPath("/error/403.xhtml");
		jsfDenieEntry.setContextRelative(true);

		// COMEÇANDO AS REGRAS DE SEGURANÇA

		// authorizeRequest: informa ao spring as telas que terão ou não acesso
		// ao sistema -->
		http.
		// desabilitar esta função para não causar problemas no sistema
				csrf().disable()
				// habilita o spring mostrar frames exemplo do combo (caso não
				// habilitado haverá problemas com combos mostrar dados)
				.headers().frameOptions().sameOrigin().and()

				/*
				 * USUARIO NÃ PRECISSA ESTAR AUTENTICADO PARA ACESSAR ESSAS
				 * PAGINAS - Se o usuário digitar o caminha errado durante a
				 * utilização do webservice dará uma msg de erro. - Retira a
				 * checagem de seguraça do link abaixo que permite acesso ao
				 * javaScrip e a imagem. Este caminho representa a pasta de
				 * recusos.
				 */
				.authorizeRequests()
				.antMatchers("/login.xhtml", "/consulta/**", "/webservice/**",
						"javax.faces.resource/**", "/error/404.xhtml", "/error/500.xhtml")
				.permitAll()
				
				/*
				 * PARA ACESSSAR ESSAS PAGINAS O USUARIO TEM QUE ESTAR
				 * AUTENTICADO - Possibilita a entrada para /home.xhtml a
				 * qualquer usuário autenticado - Importante q seja mostrada
				 * essa tela apenas para usuários logados. Motivo explicado na
				 * aula 7 de segurança
				 */
				.antMatchers("/home.xhtml", "/error/403.xhtml").authenticated()

				/*
				 * ACESSA ESSAS PAGINAS APENAS SE TIVER PERMISSÕES ROLE DE
				 * ACESSO
				 */

				// UTILIZANDO PERMISSAO
				.antMatchers("/main/documento/**").hasRole("GERENCIAR_DOCUMENTO")
				.antMatchers("/main/grupodocumento/**").hasRole("GERENCIAR_GRUPO")
				.antMatchers("/main/usuario/**").hasRole("GERENCIAR_USUARIO")
				
				// UTILIZANDO
				// GRUPO.antMatchers("/main/documento/**").hasAnyRole("ADMINISTRADOR")
				.and()

				// CONFIGURAR O FOMULARIO DE LOGIN
				/*
				 * - login-page: indica qual a tela que representa o login do
				 * sistema - primeira tela a ser apresentada --> -
				 * default-target-url: indica para onde o usuário será
				 * direcionado quando logar no sistema. direciona para o
				 * welcome-file definido no web.xml
				 */
				.formLogin().loginPage("/login.xhtml").failureUrl("/login.xhtml?invalid=true").and()

				/*
				 * CONFIGURANDO O LOGOUT Quando o usuário acessar a url /logout
				 * a seção será invalidada e redirecionado para tela inicial
				 */
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()

				/*
				 * CONFIGURANDO EXCEÇÕES ACESSO NEGADO 403
				 */
				.exceptionHandling().accessDeniedPage("/error/403.xhtml")
				.authenticationEntryPoint(jsfLoginEntity).accessDeniedHandler(jsfDenieEntry);
	}


	// CONVERTE A SENHA DE ENTRADA DA TELA DE LOGIN EM MD5 PARA COMPARAR COM A
	// SENHA DO BANCO E MD5
	// PARA VALITAR COM A SENHA DESCRIPTOGRAFADA BASTA COMENTAR ESSE COMANDO
	protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception { auth.userDetailsService(userDetailsService)
	  .passwordEncoder(new Md5PasswordEncoder()); 
	  //.passwordEncoder(new BCryptPasswordEncoder()); 
	  }

	
	/*
	@Value("${ldap.url:ldap://sescamazonas.local:389}")
	private String url;
	@Value("${ldap.domain}:sescamazonas.local")
	private String domain;
	@Value("${ldap.userDNPattern}:hermogenes.silva")
	private String userDNPattern;
*/
	
	
	
	
	/*
	 * @Override public void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { ActiveDirectoryLdapAuthenticationProvider adProvider = new
	 * ActiveDirectoryLdapAuthenticationProvider( domain, url);
	 * System.out.println("PASSOU POR AQUI #########################");
	 * adProvider.setConvertSubErrorCodesToExceptions(true);
	 * adProvider.setUseAuthenticationRequestCredentials(true);
	 * adProvider.setUserDetailsContextMapper(userDetailsContextMapper());
	 * System.out.println("PASSOU POR AQUI 2 #########################"); // set
	 * pattern if it exists // The following example would authenticate a user
	 * if they were a member // of the ServiceAccounts group //
	 * (&(objectClass=user)(userPrincipalName={0}) //
	 * (memberof=CN=ServiceAccounts,OU=alfresco,DC=mycompany,DC=com)) if
	 * (userDNPattern != null && userDNPattern.trim().length() > 0) {
	 * adProvider.setSearchFilter(userDNPattern);
	 * System.out.println(adProvider.toString());
	 * 
	 * }
	 * 
	 * auth.authenticationProvider(adProvider);
	 * 
	 * 
	 * // don't erase credentials if you plan to get them later // (e.g using
	 * them for another web service call) auth.eraseCredentials(false); }
	 * 
	 * public UserDetailsContextMapper userDetailsContextMapper() {
	 * UserDetailsContextMapper contextMapper = new
	 * AttributesLDAPUserDetailsContextMapper(); return contextMapper; }
	 * 
	 */

}
