package com.br.formulario.modelo.persistencia.entidade.mapeadas;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;

@Entity
@Table(name = "SeminarioEnvelhecimento", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idSeminarioEnvelhecimento"))
public class SeminarioEnvelhecimento extends ValueObject{

	private static final long serialVersionUID = -3187020207196413700L;
	
	private String nome;	
	private Calendar dtaNascimento;
	private String sexo;
	private String email;
	private String fone;	
	private String tipo;	
		
	private Calendar dtaInscricao;
	private StatusEntidadeEnum status;

	
	
	@Column(name = "nome", nullable = false, length = 150)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
		
	
	@Column(name = "tipo", nullable = false, length = 50)
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
		
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaNascimento", nullable = false)
	public Calendar getDtaNascimento() {
		return dtaNascimento;
	}
	public void setDtaNascimento(Calendar dtaNascimento) {
		this.dtaNascimento = dtaNascimento;
	}
	
	
	@Column(name = "fone", nullable = false, length = 150)
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}


	@Column(name = "sexo", nullable = false, length = 10)
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
	
	@Column(name = "email", nullable = true, length = 150)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

		
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaInscricao", nullable = true)
	public Calendar getDtaInscricao() {
		return dtaInscricao;
	}
	public void setDtaInscricao(Calendar dtaInscricao) {
		this.dtaInscricao = dtaInscricao;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "statusTipo", nullable = false, length = 7)
	public StatusEntidadeEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEntidadeEnum status) {
		this.status = status;
	}
	

	
	
}
