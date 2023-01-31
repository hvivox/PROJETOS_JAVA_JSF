package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;

import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;

public class DTOCategoriaServico implements Serializable {

	private static final long serialVersionUID = -8863989366948427335L;
	private List<CategoriaServico> categoriasDeServico;


	public List<CategoriaServico> getCategoriasDeServico() {
		return categoriasDeServico;
	}


	public void setCategoriasDeServico(List<CategoriaServico> categoriasDeServico) {
		this.categoriasDeServico = categoriasDeServico;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoriasDeServico == null) ? 0 : categoriasDeServico.hashCode());
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
		DTOCategoriaServico other = (DTOCategoriaServico) obj;
		if (categoriasDeServico == null) {
			if (other.categoriasDeServico != null)
				return false;
		} else if (!categoriasDeServico.equals(other.categoriasDeServico))
			return false;
		return true;
	}

}
