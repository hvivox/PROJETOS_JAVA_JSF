package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;


@Entity
@Table(name="pm_selecao", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idselecao"))
public class Selecao extends ValueObject{


	private static final long serialVersionUID = 9029088585102956906L;
	private String titulo;
	private Calendar dtaVigencia;
	private String descricao;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriSelecao;
	private EstatusEntidadeEnum estatus;
	
	public Selecao() {
		
	}
		
	
	public Selecao(Integer id, String titulo, String descricao, String uriSelecao, EstatusEntidadeEnum estatus) {
		this.setId(id);
		this.descricao = descricao;
		this.titulo = titulo;
		this.uriSelecao = uriSelecao;
		this.estatus = estatus;
	}
	

	@Column(name = "titulo", nullable = false, length = 100)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaVigencia", nullable = false)
	public Calendar getDtaVigencia() {
		return dtaVigencia;
	}


	public void setDtaVigencia(Calendar dtaVigencia) {
		this.dtaVigencia = dtaVigencia;
	}


	@Column(name = "nomeImgOriginal", nullable = false, length = 100)
	public String getNomeImgOriginal() {
		return nomeImgOriginal;
	}

	public void setNomeImgOriginal(String nomeImgOriginal) {
		this.nomeImgOriginal = nomeImgOriginal;
	}
	
	
	
	@Column(name = "nomeImagem", nullable = false, length = 100)
	public String getNomeImagem() {
		return nomeImagem;
	}


	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
	
	
	

	@Column(name = "descricao", nullable = true, length = 255)
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	@Column(name = "uriSelecao", nullable = false, length = 255)
	public String getUriSelecao() {
		return uriSelecao;
	}
	
	public void setUriSelecao(String uriSelecao) {
		this.uriSelecao = uriSelecao;
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
