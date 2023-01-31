package com.br.formulario.modelo.persistencia.dao.filter;

import java.io.Serializable;
import java.util.Calendar;

public class FiltroPesquisaPadrao implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String titulo;	
	private boolean mostraInitivos = false;
	private String dtaInicioInscricao;
	private String ano = obterAnoAtual();

	private String obterAnoAtual() {
		String anoAtual = Integer.toString(   Calendar.getInstance().get(Calendar.YEAR) );
		return anoAtual;
	}
	
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
	
	
	
	public String getDtaInicioInscricao() {
		return dtaInicioInscricao;
	}
	public void setDtaInicioInscricao(String dtaInicioInscricao) {
		this.dtaInicioInscricao = dtaInicioInscricao;
	}
		
	
	
	public String getAno() {
		
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
		
	
	

}
