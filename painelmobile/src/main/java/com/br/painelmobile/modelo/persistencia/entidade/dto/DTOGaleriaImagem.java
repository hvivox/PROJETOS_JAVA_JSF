package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;

public class DTOGaleriaImagem implements Serializable{

	private static final long serialVersionUID = -8863989366948427335L;
	private List<GaleriaImagem> galeriaImagens;
	
	
	
	public List<GaleriaImagem> getGaleriaImagens() {
		return galeriaImagens;
	}
	public void setGaleriaImagens(List<GaleriaImagem> galeriaImagens) {
		this.galeriaImagens = galeriaImagens;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((galeriaImagens == null) ? 0 : galeriaImagens.hashCode());
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
		DTOGaleriaImagem other = (DTOGaleriaImagem) obj;
		if (galeriaImagens == null) {
			if (other.galeriaImagens != null)
				return false;
		} else if (!galeriaImagens.equals(other.galeriaImagens))
			return false;
		return true;
	}
	
	
}
