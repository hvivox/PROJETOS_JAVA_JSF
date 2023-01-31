package com.br.sdni.modelo.persistencia.dao.filter;

import java.io.Serializable;

import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;

public class UsuarioFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private boolean mostraInativos = false;
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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

	public boolean isMostraInativos() {
		return mostraInativos;
	}

	public void setMostraInativos(boolean mostraInativos) {
		this.mostraInativos = mostraInativos;
	}
	
	
	
	
	
}