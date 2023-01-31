package com.br.formulario.modelo.persistencia.entidade.mapeadas;

import java.util.Calendar;



import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;


@Entity
@Table(name = "AmazonasArtes", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idAmArtes"))
public class AmazonasArtes extends ValueObject{
	

	private static final long serialVersionUID = -5802869956108720586L;
	private String nomeGrupo;
	private String cnpj;
	private String diretor;
	private String cidade;
	private SiglasEstados estado;
	private Integer qtdParticipantes;
	private ModAmArtes modAmArtes;
	private String nomeEspetaculo;
	private String historico;
	private String fone;
	private String whatZapp;
	private String email;
	private String linkCoreografia;
	private String release;
	private Calendar dtaInscricao;
	private StatusEntidadeEnum status;
	private Boolean aceiteRegulamento;
	private String nomeDoc;
	private String nomeOriginalDoc;
	private String uri;
	
	
	
	@Column(name = "nomeGrupo", nullable = false, length = 150)
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	
	@Column(name = "cnpj", nullable = false, length = 20)
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	
	@Column(name = "nomeDiretor", nullable = false, length = 150)
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	
	
	
	@Column(name = "cidade", nullable = false, length = 150)
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
	
	
	@Column(name = "qtdParticipantes", nullable = false)
	public Integer getQtdParticipantes() {
		return qtdParticipantes;
	}
	public void setQtdParticipantes(Integer qtdParticipantes) {
		this.qtdParticipantes = qtdParticipantes;
	}
	
	
	
	@ManyToOne(targetEntity = ModAmArtes.class)
	// nome da FK para a config BD
	@ForeignKey(name = "fk_idModAmArtes")
	// nome da FK na tabela
	@JoinColumn(name = "idModAmArtes", nullable = false)
	public ModAmArtes getModAmArtes() {
		return modAmArtes;
	}
	public void setModAmArtes(ModAmArtes modAmArtes) {
		this.modAmArtes = modAmArtes;
	}
	
	
	
	@Column(name = "nomeEspetaculo", nullable = false, length = 150)
	public String getNomeEspetaculo() {
		return nomeEspetaculo;
	}
	public void setNomeEspetaculo(String nomeEspetaculo) {
		this.nomeEspetaculo = nomeEspetaculo;
	}
	
	
	@Column(name = "historico", columnDefinition = "TEXT", nullable = false)
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
	
	
	@Column(name = "fone", nullable = false, length = 150)
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	
	
	@Column(name = "whatzap", nullable = false, length = 15)
	public String getWhatZapp() {
		return whatZapp;
	}
	public void setWhatZapp(String whatZapp) {
		this.whatZapp = whatZapp;
	}
	
	
	@Column(name = "email", nullable = false, length = 150)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	@Column(name = "linkCoreografia", nullable = true, length = 150)
	public String getLinkCoreografia() {
		return linkCoreografia;
	}
	public void setLinkCoreografia(String linkCoreografia) {
		this.linkCoreografia = linkCoreografia;
	}
	
	
	
	@Column(name = "releaseObjetiva", columnDefinition = "TEXT", nullable = false)
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
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
	
	
	@Column(name = "nomeDoc", nullable = false, length = 100)
	public String getNomeDoc() {
		return nomeDoc;
	}	
	public void setNomeDoc(String nomeDoc) {
		this.nomeDoc = nomeDoc;
	}
	
	

	@Column(name = "nomeOriginalDoc", nullable = false, length = 100)
	public String getNomeOriginalDoc() {
		return nomeOriginalDoc;
	}
	public void setNomeOriginalDoc(String nomeOriginalDoc) {
		this.nomeOriginalDoc = nomeOriginalDoc;
	}
		
	
	@Column(name = "uriDocumento", nullable = false, length = 255)
	public String getUri() {
		return uri;
	}	
	public void setUri(String uri) {
		this.uri = uri;
	}
			
	
	
	
}
