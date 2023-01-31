package com.br.sdni.modelo.persistencia.entidade.mapeadas;

import java.io.Serializable;


import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="grupo", catalog="sdni")
@AttributeOverride(name="id", column=@Column(name="idgrupo"))
public class Grupo extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	private List<Permissao> permisoes;
	
	
	@Column(name = "nome", nullable = false, length = 40)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao", nullable=true, length=80)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	public List<Permissao> getPermisoes() {
		return permisoes;
	}

	public void setPermisoes(List<Permissao> permisoes) {
		this.permisoes = permisoes;
	}
	
	
	
	

}