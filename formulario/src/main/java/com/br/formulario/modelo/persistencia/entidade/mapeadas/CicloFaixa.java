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
@Table(name = "CicloFaixa", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idCicloFaixa"))
public class CicloFaixa extends ValueObject{

	private static final long serialVersionUID = -3187020207196413700L;
	
	private String nome;	
	private String email;
	private String fone;
	private String rg;
	private String cpf;
	private Boolean	possuiBicicleta;
	private Boolean	possuiCapacete;

	
	
	private Calendar dtaInscricao;
	private StatusEntidadeEnum status;
	private Boolean aceiteRegulamento;
	
	
	
	@Column(name = "nome", nullable = false, length = 150)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
		
	@Column(name = "email", nullable = true, length = 150)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Column(name = "fone", nullable = true, length = 150)
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}


	@Column(name = "rg", nullable = false, length = 14)
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	
	@Column(name = "cpf", nullable = false, length = 15)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	@Type(type="true_false")
    @Column(name="possuiBicicleta")
	public Boolean getPossuiBicicleta() {
		return possuiBicicleta;
	}
	public void setPossuiBicicleta(Boolean possuiBicicleta) {
		this.possuiBicicleta = possuiBicicleta;
	}
	


	@Type(type="true_false")
    @Column(name="possuiCapacete")
	public Boolean getPossuiCapacete() {
		return possuiCapacete;
	}
	public void setPossuiCapacete(Boolean possuiCapacete) {
		this.possuiCapacete = possuiCapacete;
	}
	
		
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaInscricao", nullable = false)
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
	
	
	
	@Type(type="true_false")
    @Column(name="aceiteRegulamento")
	public Boolean getAceiteRegulamento() {
		return aceiteRegulamento;
	}
	public void setAceiteRegulamento(Boolean aceiteRegulamento) {
		this.aceiteRegulamento = aceiteRegulamento;
	}

	
	
}
