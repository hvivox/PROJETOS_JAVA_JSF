package com.br.painelmobile.controle.webserver.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.servico.ServiceGaleriaImagem;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOGaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.util.manipularDados.ConversorData;


@Named
@Path("/galeria")
public class WSGaleria {
	@Inject
	private ConversorData conversorData;	
	@Inject
	private ServiceGaleriaImagem serviceGaleria;	
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	
	private DTOGaleriaImagem dtoGaleriaImagem = new DTOGaleriaImagem();
	
	
	public WSGaleria() {
		
	}	


	@GET
	@Path("listar-galeria")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DTOGaleriaImagem getListarGalerias() throws WSTratamentoExcecaoGeral{
		
		try {

			filtro.setMostraInitivos(false);
			List<GaleriaImagem> listaGaleria = serviceGaleria.consultarPorFiltro(filtro);
			List<GaleriaImagem> listaGaleriaModificada = new ArrayList<GaleriaImagem>();
					
			//adiciona duas informações em cada entidade mes ex: janeiro e ano ex: 2015
			for (GaleriaImagem galeriaImagem : listaGaleria) {				
				
				//se a galeria nao tiver imagens associadas não será mostrada
				if(galeriaImagem.getImagens().size()>0){
					galeriaImagem.setAnoEvento(conversorData.getAnoPorExtenson(galeriaImagem.getDtaEvento()));
					galeriaImagem.setMesEvento(conversorData.getMesPorExtenson(galeriaImagem.getDtaEvento()));
					galeriaImagem.setImagens(null);
					
					listaGaleriaModificada.add(galeriaImagem);
				}								
			}				
			
			dtoGaleriaImagem.setGaleriaImagens(listaGaleriaModificada);			
			
			// esta é uma forma diferente de envio de informação. A diferença é
			// a possibilidade de setar informações no cabeçalho do http
			return dtoGaleriaImagem;

		} catch (Exception e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			throw new WSTratamentoExcecaoGeral(
					"{\"status\":\"400\",\"mensagem\":\"No momento não é possível exibir as Galerias. Por favor tente novamente mais tarde\"}");
		}
	}
		
	
		
	
}
