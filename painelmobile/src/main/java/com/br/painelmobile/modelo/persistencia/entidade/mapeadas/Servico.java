package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;



@Entity
@Table(name="pm_servico", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idservico"))
public class Servico extends ValueObject {

	private static final long serialVersionUID = 33246093417902085L;
	
	private String titulo;
	private String nomeImagem;
	private String nomeImgOriginal;	
	private String uriImagem;
	private String descricao;
	private CategoriaServico catServico;
	private EstatusEntidadeEnum estatus;
	
	
	public Servico(){
		
	}
	
	
	public Servico(Integer id, String uriImagem, String descricao, EstatusEntidadeEnum estatus) {
		this.setId(id);
		this.uriImagem = uriImagem;
		this.descricao = descricao;
		this.estatus = estatus;
	}
		
	
	@Column(name = "titulo", nullable = false, length = 100)
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	
	@Column(name = "Descricacao", nullable = true, length = 255)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
		
	

	@ManyToOne(targetEntity = CategoriaServico.class)
	@ForeignKey(name = "fk_idCategoriaServico")
	// nome da FK para a config BD
	@JoinColumn(name = "servico_idCategoriaServico", nullable = false)
	// nome da FK na tabela
	public CategoriaServico getCatServico() {
		return catServico;
	}


	public void setCatServico(CategoriaServico catServico) {
		this.catServico = catServico;
	}


	@Enumerated(EnumType.STRING)
	@Column(name="estatus", nullable=false, length=7)
	public EstatusEntidadeEnum getEstatus() {
		return estatus;
	}
	public void setEstatus(EstatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}


	@Override
	public String toString() {
		return String.valueOf(getId());
	}
	
	
	
	
	
}
