package com.br.formulario.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;


@Entity
@Table(name="modAmArte", catalog="formulario")
@AttributeOverride(name="id", column=@Column(name="idModAmArte"))
public class ModAmArtes extends ValueObject{

	private static final long serialVersionUID = 1L;
	private String nome;
	private StatusEntidadeEnum status;
	
	
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
	
	
	
	
	
}
