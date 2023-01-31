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
@Table(name = "pm_categoriaServico", catalog = "portalsescam")
@AttributeOverride(name = "id", column = @Column(name = "idCategoriaServico"))
public class CategoriaServico extends ValueObject {

	private static final long serialVersionUID = 4211898282250941219L;

	private String nomeCategoria;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;	
	private List<Servico> servicos = new ArrayList<Servico>();
	

	public CategoriaServico() {

	}
	
	
	@Column(name = "nomeCategoria", nullable = false, length = 150)
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
	
	
	

	@OneToMany(targetEntity = Servico.class, mappedBy = "catServico", fetch = FetchType.LAZY)
	public List<Servico> getServicos() {
		return servicos;
	}
	
	
	public void setServicos(List<Servico> servico) {
		this.servicos = servico;
	}


	@Override
	public String toString() {
		return String.valueOf(getId()) ;
	}
	
	
	
	
}
