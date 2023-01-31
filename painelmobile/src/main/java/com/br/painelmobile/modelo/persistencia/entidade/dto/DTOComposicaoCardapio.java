package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;

public class DTOComposicaoCardapio implements Serializable {

	private static final long serialVersionUID = -8863989366948427335L;
	
	private String dataDoCardapio;
	private String opcao;
	private List<String> itens;
		
	
	public DTOComposicaoCardapio() {
		
	}

	
	
	
	
	public String getDataDoCardapio() {
		return dataDoCardapio;
	}

	public void setDataDoCardapio(String dataDoCardapio) {
		this.dataDoCardapio = dataDoCardapio;
	}


	public String getOpcao() {
		return opcao;
	}


	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}


	public List getItens() {
		return itens;
	}


	public void setItens(List itens) {
		this.itens = itens;
	}	
 
		
	

}
