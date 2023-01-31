package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pm_grupo", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idgrupo"))
public class Grupo extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	
	
	
	@Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable=true, length=80)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

}