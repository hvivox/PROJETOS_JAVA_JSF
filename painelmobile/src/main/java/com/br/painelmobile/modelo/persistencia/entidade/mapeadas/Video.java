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
@Table(name="pm_video", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idvideo"))
public class Video extends ValueObject {	
	
	private static final long serialVersionUID = 3974009636205994280L;
	
	private String titulo;
	private String descricao;
	private Calendar dtaInclusaoVideo;
	private String idVideoYoutube;
	private EstatusEntidadeEnum estatus;

	
	
	public Video(){
		
	}
	
	
	
	public Video(Integer id, String titulo, String descricao, Calendar dtaInclusaoVideo, String idVideoYoutube,
			EstatusEntidadeEnum estatus) {
		this.setId(id);
		this.titulo = titulo;
		this.descricao = descricao;
		this.dtaInclusaoVideo = dtaInclusaoVideo;
		this.idVideoYoutube = idVideoYoutube;
		this.estatus = estatus;
	}



	@Column(name = "titulo", nullable = false, length = 250)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	@Column(name = "descricao", nullable = false, length = 250)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaInclusaoVideo", nullable = false, length = 250)
	public Calendar getDtaInclusaoVideo() {
		return dtaInclusaoVideo;
	}
	public String getIdVideoYoutube() {
		return idVideoYoutube;
	}

	
	
	
	@Column(name = "idVideoYoutube", nullable = false, length = 255)
	public void setIdVideoYoutube(String idVideoYoutube) {
		this.idVideoYoutube = idVideoYoutube;
	}
	public void setDtaInclusaoVideo(Calendar dtaInclusaoVideo) {
		this.dtaInclusaoVideo = dtaInclusaoVideo;
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
