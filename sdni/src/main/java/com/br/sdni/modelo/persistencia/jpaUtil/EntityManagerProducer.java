package com.br.sdni.modelo.persistencia.jpaUtil;



import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

@ApplicationScoped
public class EntityManagerProducer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory factory;

	/**
	 * @ApplicationScoped: garante que a classe seja instancia apenas uma vez
	 *                     durante todo o funcionamento da aplicação Cria uma
	 *                     fabrica de conexão baseando em HermesPU que é a
	 *                     configuração do Persistenci Unite do persistenci.xml
	 */
	public EntityManagerProducer() {
		factory = Persistence.createEntityManagerFactory("sdniPU");
	}

	/**
	 * @Metodo: utilziado para retornar sessões de conexaão com o banco
	 * @Produces: produz uma instancia de entityManager, atua de acordo com o
	 *            tipo de clico de vida especificado no caso o RequestScoped
	 * @RequestScoped: utilizado para restringir a execução do método de acordo
	 *                 com as requisições do feitas pelo browser do usuário
	 */
	@Produces
	@RequestScoped
	public Session createEntityManagerFactory() {
		return ( Session) factory.createEntityManager();
	}

	/**
	 * @Metodo: utilizado para fechar as sessões de conexão com o banco abertas
	 *          IMPORTANTE: este metodo sempre será chamado quando o escopo da
	 *          resquisição (@request, @Session etc) for finalizado, ou seja
	 *          sempre que finalizar o escopo o metodo será chamado de forma
	 *          automatica. Isso faz com que o Cache primeiro Nivel, sempre atualize ao sair e entrar na Tela com @viewscoped
	 * @Disposes: libera da memoria a sessão que está sendo fechada
	 */
	public void closeEntityManager(@Disposes Session manager) {
		manager.close();
		LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).info("Fechou o EntityManager###############################3");	
	}

}
