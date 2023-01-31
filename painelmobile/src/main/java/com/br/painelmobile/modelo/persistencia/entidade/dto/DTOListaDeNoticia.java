package com.br.painelmobile.modelo.persistencia.entidade.dto;

import java.util.ArrayList;
import java.util.List;


/**
 *Esta classe está redundante propositalmente parar se adaptar ao formato Json
 * solicitado pelo desenvolvedor appAndroid algo como:
 * "listaNoticias": [
    {
      "id": 1,
      "Titulo": "SEDE01",
 * 
 * @author hermogenes.silva
 *
 */
public class DTOListaDeNoticia {
	
	private List<DTONoticia> listaNoticias = new ArrayList<DTONoticia>();

	
	
	public DTOListaDeNoticia() {
		
	}
	
	public DTOListaDeNoticia(List<DTONoticia> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}

	
	
	public List<DTONoticia> getListaNoticias() {
		return listaNoticias;
	}

	public void setListaNoticias(List<DTONoticia> listaNoticias) {
		this.listaNoticias = listaNoticias;
	}

	
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaNoticias == null) ? 0 : listaNoticias.hashCode());
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
		DTOListaDeNoticia other = (DTOListaDeNoticia) obj;
		if (listaNoticias == null) {
			if (other.listaNoticias != null)
				return false;
		} else if (!listaNoticias.equals(other.listaNoticias))
			return false;
		return true;
	}
	
	
	


	
}