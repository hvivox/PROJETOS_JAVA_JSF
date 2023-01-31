package com.br.painelmobile.util.managerbean.diversos;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StaleObjectStateException;

/**
 * @author hvivox
 <!-- mapeamento do fitro: primeiro classe a ser executuda quando há uma requisição cliente/servidor.
 o filtro é executado antes da requisição -->
 *<!-- Quando qualquer URL for executada deve-se passar por esse filtro -->
 *O Filtro tem um problema: pois é criado um unico filtro para atender todas as requisição de todos os usuários
 *
 *caso um consulta demore a ser executadas o sistema irá travar até que a consulta termine
 *@WebFilter(name = "MeuFiltro", ulrPatterns = {"/cadProExterna", "/inicio"})
 *@WebFilter("/*")
 *
 */
@WebFilter(filterName = "Filtro", urlPatterns = { "*.jsp", "*.xhtml", "*.jsf" })
public class Filtro implements Filter {

	private static Log log = LogFactory.getLog(Filtro.class);
	//private SessionFactory sf;

	
	// Realiza o processamento do filtro
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	

		try {
		
			
			System.out.println("EXECUSSÃO NA IDA##### DA REQUISIÇÃO - FILTRO");
			
			//sf.getCurrentSession().beginTransaction();
			
			// Ele nos permite indicar ao container que o request deve
			// prosseguir seu processamento, dependendo da condição
			chain.doFilter(request, response);
						
			//sf.getCurrentSession().getTransaction().commit();
			//sf.getCurrentSession().setFlushMode(FlushMode.MANUAL);//evita erros com a sobrecarga de dados dentro do buffer
			System.out.println("EXECUSSÃO NA VOLTA##### DA REQUISIÇÃO - FILTRO");

			
		} 
		
		catch (StaleObjectStateException staleEx) {
			log.error("This interceptor does not implement optimistic concurrency control!");
			log.error("Your application will not work until you add compensation actions!");

			throw staleEx;
		}
		
		catch (Throwable ex) {
           
			ex.printStackTrace();
			
			try {
				/*if (sf.getCurrentSession().getTransaction().isActive()) {
					log.debug("Trying to rollback database transaction after exception");
					sf.getCurrentSession().getTransaction().rollback();
				}*/
			} catch (Throwable rbEx) {
				log.error("Não foi possível aplicar o roodback após a exceção!", rbEx);
			}

			// Let others handle it... maybe another interceptor for exceptions?
			throw new ServletException(ex);
		}finally{
			//fecha a sessão a cada requisição
			//sf.getCurrentSession().close();
		}
	}

	
	
	
	// Executado quando o filtro é inicializado pelo conteiner
	public void init(FilterConfig configuracaoFiltro) throws ServletException {

		log.debug("Initializing filter...");
		log.debug("Obtaining SessionFactory from static HibernateUtil singleton");		
		
		//sf = HibernateSessionManager.getSessionFactory();// vem da minha classe
		
		// SO É MOSTRADO NA INICIALIZAÇÃO DO FILTRO SO INICIALIZA UMA VEZ
		System.out.println("NOME DO FILTRO:##########################"+ configuracaoFiltro.getFilterName());
	}

	
	// Executado quando o filtro é descarregado pelo conteinner
	public void destroy() {
		/*Este comando evita este erro:
		 * hibernate-c3p0 project on tomcat 7 without getting strange c3p0 errors
		 * */
		//sf.close();
	}

}
