package com.br.sdni.modelo.persistencia.dao.filter;

import java.io.Serializable;

public class FiltroPesquisaPadrao implements Serializable {

	private static final long serialVersionUID = 1L;
	private String titulo;
	private boolean mostraInativos = false;
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public boolean isMostraInativos() {
		return mostraInativos;
	}


	public void setMostraInativos(boolean mostraInativos) {
		this.mostraInativos = mostraInativos;
	}


	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}


	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}


	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}


	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}


	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}


	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}


	public boolean isAscendente() {
		return ascendente;
	}


	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}

}
