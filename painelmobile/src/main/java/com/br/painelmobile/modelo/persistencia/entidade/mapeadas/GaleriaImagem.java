package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;



@Entity
@Table(name="pm_galeriaImagem", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idGaleriaImagem"))
public class GaleriaImagem extends ValueObject{
	
	
	private static final long serialVersionUID = -3087934122606710789L;
	private String titulo;
	private Calendar dtaEvento;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagemPrincipal;
	private String localEvento;
	private EstatusEntidadeEnum estatus;
	private String mesEvento;
	private String anoEvento;
	private List<Imagem> imagens = new ArrayList<Imagem>();
		
	
	public GaleriaImagem(){
		
	}
	
	
	@Column(name="titulo", nullable=false, length=200)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtaEvento", nullable=false)
	public Calendar getDtaEvento() {
		return dtaEvento;
	}
	public void setDtaEvento(Calendar dtaEvento) {
		this.dtaEvento = dtaEvento;
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

	
	
	@Column(name="uriImagemPrincipal", nullable=true, length=255)	
	public String getUriImagemPrincipal() {
		return uriImagemPrincipal;
	}

	public void setUriImagemPrincipal(String uriImagemPrincipal) {
		this.uriImagemPrincipal = uriImagemPrincipal;
	}
	
		
	
	@Column(name="localEvento", nullable=true, length=200)
	public String getLocalEvento() {
		return localEvento;
	}
	public void setLocalEvento(String localEvento) {
		this.localEvento = localEvento;
	}


	@OneToMany(targetEntity = Imagem.class, mappedBy="galeria", fetch=FetchType.LAZY)	
	public List<Imagem> getImagens() {
		return imagens;
	}	
	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	

	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", nullable = false, length = 7)
	public EstatusEntidadeEnum getEstatus() {
		return estatus;
	}
	public void setEstatus(EstatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}	
	
	
	@Transient
	public String getMesEvento() {
		return mesEvento;
	}
	public void setMesEvento(String mesEvento) {
		this.mesEvento = mesEvento;
	}
	
	
	@Transient
	public String getAnoEvento() {
		return anoEvento;
	}	
	public void setAnoEvento(String anoEvento) {
		this.anoEvento = anoEvento;
	}


	
	
}
