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
@Table(name="pm_evento", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idevento"))
public class Evento extends ValueObject{

	private static final long serialVersionUID = 4679271239112491436L;
	
	private String titulo;	
	private String descricao;
	private Calendar dtaEncerramento;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;	
	private EstatusEntidadeEnum estatus;
	
	
	public Evento(){
		
	}
	
	
	public Evento(Integer id, Calendar dtaEvento, String uriImagem, String descricao,
			EstatusEntidadeEnum estatus) {
		this.setId(id);
		this.dtaEncerramento = dtaEvento;
		this.uriImagem = uriImagem;
		this.descricao = descricao;
		this.estatus = estatus;
	}
	
	
	
	@Column(name = "titulo", nullable = false, length = 150)
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaEncerramento", nullable = false)
	public Calendar getDtaEncerramento() {
		return dtaEncerramento;
	}
	
	public void setDtaEncerramento(Calendar dtaEvento) {
		this.dtaEncerramento = dtaEvento;
	}
	
	
	@Column(name = "uriImagem", nullable = false, length = 255)	
	public String getUriImagem() {
		return uriImagem;
	}
	
	public void setUriImagem(String uriImagem) {
		this.uriImagem = uriImagem;
	}
	
	
	@Column(name = "Descricacao", nullable = true, length = 255)	
	public String getDescricao() {
		return descricao;
	}	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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


	@Enumerated(EnumType.STRING)
	@Column(name="estatus", nullable=false, length=7)
	public EstatusEntidadeEnum getEstatus() {
		return estatus;
	}
	public void setEstatus(EstatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}	
	
	
	
}
