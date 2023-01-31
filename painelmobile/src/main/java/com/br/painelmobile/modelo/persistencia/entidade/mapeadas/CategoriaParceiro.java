package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="pm_categoriaParceiro", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idCategoriaParceiro"))
public class CategoriaParceiro extends ValueObject{

	private static final long serialVersionUID = 4211898282250941219L;

	
	private String nomeCategoria;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;	
	private List<Parceiro> parceiros = new ArrayList<Parceiro>();
	
	
	
	public CategoriaParceiro(){
		
	}
	
	
	@Column(name="nomeCategoria", nullable=false, length=150)
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	 
	
	@Column(name = "uriImagem", nullable = false, length = 255)	
	public String getUriImagem() {
		return uriImagem;
	}
	
	public void setUriImagem(String uriImagem) {
		this.uriImagem = uriImagem;
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

	
	 
	@OneToMany(targetEntity = Parceiro.class, mappedBy="catParceiro", fetch=FetchType.LAZY)	 
	public List<Parceiro> getParceiros() {
		return parceiros;
	}
	public void setParceiros(List<Parceiro> parceiros) {
		this.parceiros = parceiros;
	}

	
	
	
}
