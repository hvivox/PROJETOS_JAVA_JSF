package com.br.sdni.modelo.persistencia.entidade.mapeadas;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="permissao", catalog="sdni")
@AttributeOverride(name="id", column=@Column(name="idpermissao"))
public class Permissao extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	
	
	@Column(name = "nome", nullable = false, length = 40)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao", nullable = false, length = 40)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

}