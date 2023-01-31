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
@Table(name = "DifusaoDanca", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idDifusaoDanca"))
public class DifusaoDanca extends ValueObject{

	private static final long serialVersionUID = -3187020207196413700L;
	
	private String nome;	
	private String Profissao;
	private Calendar dtaNascimento;
	private String formacao;
		
	private String fone;
	private String celular;
	private Boolean	usaWhatsApp;
	private String email;
	
	
	private String endereco;
	private String cep;
	private String cidade;
	private SiglasEstados estado;

	
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
	
		
	
	@Column(name = "profissao", nullable = false, length = 150)
	public String getProfissao() {
		return Profissao;
	}
	public void setProfissao(String profissao) {
		Profissao = profissao;
	}
	
	
		
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaNascimento", nullable = false)
	public Calendar getDtaNascimento() {
		return dtaNascimento;
	}
	public void setDtaNascimento(Calendar dtaNascimento) {
		this.dtaNascimento = dtaNascimento;
	}
	
	
	@Column(name = "fone", nullable = true, length = 150)
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}

	@Column(name = "celular", nullable = false, length = 16)
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	@Type(type="true_false")
    @Column(name="usaWhatsApp")
	public Boolean getUsaWhatsApp() {
		return usaWhatsApp;
	}
	public void setUsaWhatsApp(Boolean usaWhatsApp) {
		this.usaWhatsApp = usaWhatsApp;
	}
	
	
	
	@Column(name = "email", nullable = true, length = 150)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	@Column(name = "endereco", nullable = true, length = 250)
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Column(name = "cep", nullable = true, length = 9)
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name = "cidade", nullable = true, length = 150)
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false, length = 2)
	public SiglasEstados getEstado() {
		return estado;
	}
	public void setEstado(SiglasEstados estado) {
		this.estado = estado;
	}	
	
	
	@Column(name = "formacao", nullable = true, length = 20)
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
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
