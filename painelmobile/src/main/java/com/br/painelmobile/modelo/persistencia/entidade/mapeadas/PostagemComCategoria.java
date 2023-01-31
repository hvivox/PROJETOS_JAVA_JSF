package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.util.Arrays;

public class PostagemComCategoria {
	private static final long serialVersionUID = 1L;
	private Integer idpostagem;
	private byte[] titulo;
	private String linkDaPostagem;
	private byte[] conteudo;
	private String categoria;
	

	
	
	public PostagemComCategoria() {
	}

	
	


	public PostagemComCategoria(Integer idpostagem, byte[] titulo, String linkDaPostagem, byte[] conteudo,
			String categoria) {
		super();
		this.idpostagem = idpostagem;
		this.titulo = titulo;
		this.linkDaPostagem = linkDaPostagem;
		this.conteudo = conteudo;
		this.categoria = categoria;
	}







	public Integer getIdpostagem() {
		return idpostagem;
	}



	public void setIdpostagem(Integer idpostagem) {
		this.idpostagem = idpostagem;
	}



	public byte[] getTitulo() {
		return titulo;
	}



	public void setTitulo(byte[] titulo) {
		this.titulo = titulo;
	}


	
	public String getLinkDaPostagem() {
		return linkDaPostagem;
	}

	public void setLinkDaPostagem(String linkDaPostagem) {
		this.linkDaPostagem = linkDaPostagem;
	}

	
	
	public byte[] getConteudo() {
		return conteudo;
	}



	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}



	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	
	
	
	
	

	@Override
	public String toString() {
		return "PostagemComCategoria [idpostagem=" + idpostagem + ", titulo=" + Arrays.toString(titulo) + ", conteudo="
				+ Arrays.toString(conteudo) + ", categoria=" + categoria + "]";
	}
	
	
	
	

}
