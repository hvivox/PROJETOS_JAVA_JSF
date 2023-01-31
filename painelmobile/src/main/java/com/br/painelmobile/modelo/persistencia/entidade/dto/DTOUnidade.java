package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.io.Serializable;
import java.util.List;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Unidade;

public class DTOUnidade implements Serializable{

	private static final long serialVersionUID = -8863989366948427335L;
	private List<Unidade> unidades;
	
	
	public DTOUnidade() {	
	
	}
	
	public DTOUnidade(List<Unidade> unidades) {	
		this.unidades = unidades;
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}
	
	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unidades == null) ? 0 : unidades.hashCode());
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
		DTOUnidade other = (DTOUnidade) obj;
		if (unidades == null) {
			if (other.unidades != null)
				return false;
		} else if (!unidades.equals(other.unidades))
			return false;
		return true;
	}

	
	
}
