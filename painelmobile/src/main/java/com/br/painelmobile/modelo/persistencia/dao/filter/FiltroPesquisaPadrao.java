package com.br.painelmobile.modelo.persistencia.dao.filter;

import java.io.Serializable;

public class FiltroPesquisaPadrao implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String titulo;	
	private boolean mostraInitivos = false;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	public boolean isMostraInitivos() {
		return mostraInitivos;
	}
	public void setMostraInitivos(boolean mostraInitivos) {
		this.mostraInitivos = mostraInitivos;
	}
		
	
	
	
	
	
	

}
