package com.br.painelmobile.controle.webserver.resources;

import java.util.Set;

import javax.ws.rs.core.Application;

import com.br.painelmobile.controle.webserver.excecoes.WSExceptionHandler;


/**
 * CLASSE DE CONFIGURAÇÃO PRINCIPAL UTILIZADA PELO REST. Resgistar as classe de webservice
 * @author hermogenes.silva
 *
 */
@javax.ws.rs.ApplicationPath("webservice")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    //NECESSARIO DECLARAR TODAS AS CLASSES WEBSERVICE AQUI
    private void addRestResourceClasses(Set<Class<?>> resources) {
    	resources.add(WSExceptionHandler.class);
    	resources.add(WSUnidade.class);
        resources.add(WSPostagem.class);
        resources.add(WSCardapio.class);
        resources.add(WSParceiro.class);
        resources.add(WSEventos.class);
        resources.add(WSMensagem.class);
        resources.add(WSGaleria.class);
        resources.add(WSImagem.class);
        resources.add(WSServico.class);
        resources.add(WSVideo.class);
        resources.add(WSSelecao.class);
    }
    
}
