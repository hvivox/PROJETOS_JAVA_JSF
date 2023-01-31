package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;



import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;


@Entity
@Table(name="pm_unidade", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idunidade"))
public class Unidade extends ValueObject{
	

	private static final long serialVersionUID = 1941692496514242709L;
	
	private String nomeUnidade;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;
	private String cidade;
	private String fone; 
	private String longitude;
	private String latitude;	
	private EstatusEntidadeEnum estatus;
	
	public Unidade(){
		
	}
	
	
	public Unidade(Integer id, String nomeUnidade, String cidade, String fone,
			EstatusEntidadeEnum estatus) {
	
		setId(id);
		this.nomeUnidade = nomeUnidade;
		this.cidade = cidade;
		this.fone = fone;
		this.estatus = estatus;
	}


	
	public Unidade(Integer id, String nomeUnidade, String cidade, String fone, String logitude,
			String latitude, EstatusEntidadeEnum estatus, String uriImagem) {
		
		setId(id);
		this.nomeUnidade = nomeUnidade;
		this.cidade = cidade;
		this.fone = fone;
		this.longitude = logitude;
		this.latitude = latitude;
		this.estatus = estatus;
		this.uriImagem = uriImagem;
		
	}
	

	
	@Column(name = "nomeUnidade", nullable = false, length = 200)
	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
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
	
	
	@Column(name = "uriImagem", nullable = false, length = 255)
	public String getUriImagem() {
		return uriImagem;
	}
	public void setUriImagem(String uriImagem) {
		this.uriImagem = uriImagem;
	}


	@Column(name = "cidade", nullable = false, length = 200)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	
	@Column(name = "telefone", nullable = false, length = 15)
	public String getFone() {
		return fone;
	}


	public void setFone(String fone) {
		this.fone = fone;
	}

	
	
	@Column(name = "longitude", nullable = false, length = 200)
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String logitude) {
		this.longitude = logitude;
	}



	@Column(name = "latitude", nullable = false, length = 200)
	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
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