package com.br.painelmobile.modelo.persistencia.entidade.dto;


public class DTONoticia {
	
	private Integer id;
	private String titulo;
	private String linkPublicacao;
	private String uriImagem;
	private String parteTexto;
	private String htmlTexto;
	private String categoria;
	
		
	
	public DTONoticia(){
		
	}	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getLinkPublicacao() {
		return linkPublicacao;
	}


	public void setLinkPublicacao(String linkPublicacao) {
		this.linkPublicacao = linkPublicacao;
	}


	public String getUriImagem() {
		return uriImagem;
	}


	public void setUriImagem(String uriImagem) {
		this.uriImagem = uriImagem;
	}


	public String getParteTexto() {
		return parteTexto;
	}


	public void setParteTexto(String parteTexto) {
		this.parteTexto = parteTexto;
	}


	public String getHtmlTexto() {
		return htmlTexto;
	}


	public void setHtmlTexto(String htmlTexto) {
		this.htmlTexto = htmlTexto;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	
	
}
