package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;

public class DTOCardapioDetalhado implements Serializable {

	private static final long serialVersionUID = -8863989366948427335L;
	
	private String cabecalho;
	private String rodape;		
	private List<DTOComposicaoCardapio> listaComposicaoCardapio; 
	private Cardapio cardapio;
	
	
	public DTOCardapioDetalhado() {
		
	}
	
	
	


	public String getCabecalho() {
		return cabecalho;
	}


	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}


	public String getRodape() {
		return rodape;
	}


	public void setRodape(String rodape) {
		this.rodape = rodape;
	}


	
	public Cardapio getCardapio() {
		return cardapio;
	}


	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}
	
	
	public List<DTOComposicaoCardapio> getListaComposicaoCardapio() {
		return listaComposicaoCardapio;
	}


	public void setListaComposicaoCardapio(List<DTOComposicaoCardapio> listaComposicaoCardapio) {
		this.listaComposicaoCardapio = listaComposicaoCardapio;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardapio == null) ? 0 : cardapio.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOCardapioDetalhado other = (DTOCardapioDetalhado) obj;
		if (cardapio == null) {
			if (other.cardapio != null)
				return false;
		} else if (!cardapio.equals(other.cardapio))
			return false;
		return true;
	}

}
