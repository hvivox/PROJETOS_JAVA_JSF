package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;


@Entity
@Table(name="pm_cardapio", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idcardapio"))
public class Cardapio extends ValueObject{
	

	private static final long serialVersionUID = 4508484380771850573L;
	private String titulo;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriCardapio;
	private EstatusEntidadeEnum estatus;
	
	
	public Cardapio(){
		
	}

	
	@Column(name = "titulo", nullable = false, length = 200)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	@Column(name = "nomeImagem", nullable = false, length = 100)
	public String getNomeImagem() {
		return nomeImagem;
	}
	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	
	@Column(name = "nomeImgOriginal", nullable = false, length = 100)
	public String getNomeImgOriginal() {
		return nomeImgOriginal;
	}
	public void setNomeImgOriginal(String nomeImgOriginal) {
		this.nomeImgOriginal = nomeImgOriginal;
	}
	
	
	
	
	@Column(name = "uriCardapio", nullable = false, length = 255)
	public String getUriCardapio() {
		return uriCardapio;
	}
	
	public void setUriCardapio(String uriCardapio) {
		this.uriCardapio = uriCardapio;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", nullable = false, length = 7)	
	public EstatusEntidadeEnum getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}
		
	
	
}
