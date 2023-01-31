package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;

public class DTOCardapio implements Serializable {

	private static final long serialVersionUID = -8863989366948427335L;
	private List<Cardapio> cardapio;
	

	public DTOCardapio() {
	}




	public DTOCardapio(List<Cardapio> cardapio) {
		this.cardapio = cardapio;
	}


	public List<Cardapio> getCardapio() {
		return cardapio;
	}


	public void setCardapio(List<Cardapio> cardapio) {
		this.cardapio = cardapio;
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
		DTOCardapio other = (DTOCardapio) obj;
		if (cardapio == null) {
			if (other.cardapio != null)
				return false;
		} else if (!cardapio.equals(other.cardapio))
			return false;
		return true;
	}

}
