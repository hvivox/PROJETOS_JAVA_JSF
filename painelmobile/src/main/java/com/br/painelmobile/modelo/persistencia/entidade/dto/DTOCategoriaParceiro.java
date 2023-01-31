package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;

public class DTOCategoriaParceiro implements Serializable {

	private static final long serialVersionUID = -8863989366948427335L;
	private List<CategoriaParceiro> categoriasDeParceiro;


	public List<CategoriaParceiro> getCategoriasDeParceiro() {
		return categoriasDeParceiro;
	}


	public void setCategoriasDeParceiro(List<CategoriaParceiro> categoriasDeParceiro) {
		this.categoriasDeParceiro = categoriasDeParceiro;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoriasDeParceiro == null) ? 0 : categoriasDeParceiro.hashCode());
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
		DTOCategoriaParceiro other = (DTOCategoriaParceiro) obj;
		if (categoriasDeParceiro == null) {
			if (other.categoriasDeParceiro != null)
				return false;
		} else if (!categoriasDeParceiro.equals(other.categoriasDeParceiro))
			return false;
		return true;
	}

}
