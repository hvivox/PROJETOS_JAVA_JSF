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
@Table(name = "PortaAberta", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idPortaAberta"))
public class PortaAberta extends ValueObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187020207196413700L;
	
	private String nomeResponsavel;
	private String nomeArtistico;	
	private String Profissao;
	private String rg;
	private String cpf;
	private Calendar dtaNascimento;
	
	private String fone;
	private String celular;
	private Boolean	usaWhatsApp;
	private String email;
	
	private String endereco;
	private String cep;
	private String cidade;
	private SiglasEstados estado;
	
	
	private String nomeGrupo;
	private String historico;
	private Integer qtdParticipantes;
	private String membrosGrupo;
	private String nomeEspetaculo;
	private String modalide;	
	private String horarioUso;
	
	private Calendar dtaInscricao;
	private StatusEntidadeEnum status;
	private Boolean aceiteRegulamento;
	
	
	@Column(name = "nomeResponsavel", nullable = false, length = 150)
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	
	@Column(name = "nomeArtistico", nullable = false, length = 150)
	public String getNomeArtistico() {
		return nomeArtistico;
	}
	public void setNomeArtistico(String nomeArtistico) {
		this.nomeArtistico = nomeArtistico;
	}
	
	
	@Column(name = "profissao", nullable = false, length = 150)
	public String getProfissao() {
		return Profissao;
	}
	public void setProfissao(String profissao) {
		Profissao = profissao;
	}
	
	
	@Column(name = "nomeRg", nullable = false, length = 14)
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
	
	
	
	@Column(name = "nomeGrupo", nullable = true, length = 150)
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	
	
	
	@Column(name = "historico", columnDefinition = "TEXT", nullable = false)
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;	
	
	}
	
	@Column(name = "qtdParticipantes", nullable = false)
	public Integer getQtdParticipantes() {
		return qtdParticipantes;
	}
	public void setQtdParticipantes(Integer qtdParticipantes) {
		this.qtdParticipantes = qtdParticipantes;	
	}
	
	@Column(name = "membroGrupo", columnDefinition = "TEXT", nullable = false)
	public String getMembrosGrupo() {
		return membrosGrupo;
	}
	public void setMembrosGrupo(String membrosGrupo) {
		this.membrosGrupo = membrosGrupo;
	}
	
	
	@Column(name = "nomeEspetaculo", nullable = true, length = 150)
	public String getNomeEspetaculo() {
		return nomeEspetaculo;
	}
	public void setNomeEspetaculo(String nomeEspetaculo) {
		this.nomeEspetaculo = nomeEspetaculo;
	}
	
	@Column(name = "modalidade", nullable = true, length = 20)
	public String getModalide() {
		return modalide;
	}
	public void setModalide(String modalide) {
		this.modalide = modalide;
	}
	
	
	@Column(name = "horario", nullable = true, length = 5)
	public String getHorarioUso() {
		return horarioUso;
	}
	public void setHorarioUso(String horarioUso) {
		this.horarioUso = horarioUso;
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
