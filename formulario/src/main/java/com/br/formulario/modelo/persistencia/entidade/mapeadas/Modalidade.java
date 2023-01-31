package com.br.formulario.modelo.persistencia.entidade.mapeadas;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;


@Entity
@Table(name="modalidadeDancaAmazonas", catalog="formulario")
@AttributeOverride(name="id", column=@Column(name="idModalidadeDancaAmazonas"))
public class Modalidade extends ValueObject{

	private static final long serialVersionUID = 4211898282250941219L;

	
	private String nome;
	private StatusEntidadeEnum status;
	private List<DancaAmazonas> dancaAmazonas = new ArrayList<DancaAmazonas>();
	private Integer quantidade;
	
	
	public Modalidade(){
		
	}


	@Column(name="nome", nullable=false, length=150)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	@Enumerated(EnumType.STRING)
	public StatusEntidadeEnum getStatus() {
		return status;
	}	
	public void setStatus(StatusEntidadeEnum status) {
		this.status = status;
	}



	@OneToMany(targetEntity = DancaAmazonas.class, mappedBy="modalidade", fetch=FetchType.LAZY)
	public List<DancaAmazonas> getDancaAmazonas() {
		return dancaAmazonas;
	}

	public void setDancaAmazonas(List<DancaAmazonas> dancaAmazonas) {
		this.dancaAmazonas = dancaAmazonas;
	}

	
	@Transient
	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	 


	
	
	
}
