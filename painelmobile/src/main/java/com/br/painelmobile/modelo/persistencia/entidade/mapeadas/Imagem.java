package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;


@Entity
@Table(name="pm_imagem", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idimagem"))
public class Imagem extends ValueObject {

	private static final long serialVersionUID = -8073140027814563862L;
	
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;
	private int ordecaoImagem;
	private GaleriaImagem galeria;
	
	
	public Imagem() {
		
	}	
	
	
	public Imagem(Integer id, String uriImagem, GaleriaImagem galeria) {
		setId(id);
		this.uriImagem = uriImagem;
	
		this.galeria = galeria;
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

	

	@Column(name="uriImagem", nullable=false, length=255)
	public String getUriImagem() {
		return uriImagem;
	}
	public void setUriImagem(String uriImagem) {
		this.uriImagem = uriImagem;
	}
		
	
	@Column(name="ordenacaoImagem", nullable = true)
	public int getOrdecaoImagem() {
		return ordecaoImagem;
	}
	public void setOrdecaoImagem(int ordecaoImagem) {
		this.ordecaoImagem = ordecaoImagem;
	}


	@ManyToOne(targetEntity = GaleriaImagem.class)
	@ForeignKey(name="fk_idGaleriaImagem")//nome da FK para a config BD	
	@JoinColumn(name = "imagem_idGaleriaImagem", nullable = false)//nome da FK na tabela
	public GaleriaImagem getGaleria() {
		return galeria;
	}
	public void setGaleria(GaleriaImagem galeria) {
		this.galeria = galeria;
	}

			
	@Override
	public String toString() {
		//{\"status\":\"ok\",\"mensagem\":\"Mensagem enviada com sucesso\"}"
		//"{'id':1,'firstName':'Lokesh'"
		return " {uriImagem=" + uriImagem + ", ordecaoImagem=" + ordecaoImagem + ", idgaleria="
				+ galeria.getId() + ", idImagens=" + getId() + "}";
	}
	

}
