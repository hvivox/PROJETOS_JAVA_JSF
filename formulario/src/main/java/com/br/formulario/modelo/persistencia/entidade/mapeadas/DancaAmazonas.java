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
@Table(name = "dancaAmazonas", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idDancaAmazonas"))
public class DancaAmazonas extends ValueObject {

	private static final long serialVersionUID = 8894643585785670472L;

	private String nomeGrupo;
	private String diretor;
	private String cidade;
	private SiglasEstados estado;
	private Integer qtdParticipantes;
	private Modalidade modalidade;
	private String nomeCoreografia;
	private String historico;
	private String fichaTecnica;
	private String coreografo;
	private Integer tempoApresentacao;
	private String fone;
	private String whatZapp;
	private String email;
	private String siteContato;
	private String musicaCompositor;
	private String linkCoreografia;
	private Calendar dtaInscricao;
	private StatusEntidadeEnum status;
	private Boolean aceiteRegulamento;

	public DancaAmazonas() {

	}


	@Column(name = "nomeGrupo", nullable = false, length = 150)
	public String getNomeGrupo() {
		return nomeGrupo;
	}


	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
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


	@ManyToOne(targetEntity = Modalidade.class)
	// nome da FK para a config BD
	@ForeignKey(name = "fk_idModalidadeDancaAmazonas")
	// nome da FK na tabela
	@JoinColumn(name = "idModalidadeDancaAmazonas", nullable = false)
	public Modalidade getModalidade() {
		return modalidade;
	}


	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}


	@Column(name = "nomeCoreografia", nullable = false, length = 150)
	public String getNomeCoreografia() {
		return nomeCoreografia;
	}


	public void setNomeCoreografia(String nomeCoreografia) {
		this.nomeCoreografia = nomeCoreografia;
	}


	@Column(name = "historico", columnDefinition = "TEXT", nullable = false)
	public String getHistorico() {
		return historico;
	}


	public void setHistorico(String historico) {
		this.historico = historico;
	}


	@Column(name = "fichaTecnica", columnDefinition = "TEXT", nullable = false)
	public String getFichaTecnica() {
		return fichaTecnica;
	}


	public void setFichaTecnica(String fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}


	@Column(name = "coreografo", nullable = false, length = 150)
	public String getCoreografo() {
		return coreografo;
	}


	public void setCoreografo(String coreografo) {
		this.coreografo = coreografo;
	}


	@Column(name = "tempoApresentacao", nullable = false)
	public Integer getTempoApresentacao() {
		return tempoApresentacao;
	}


	public void setTempoApresentacao(Integer tempoApresentacao) {
		this.tempoApresentacao = tempoApresentacao;
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


	@Column(name = "siteContato", nullable = true, length = 150)
	public String getSiteContato() {
		return siteContato;
	}


	public void setSiteContato(String siteContato) {
		this.siteContato = siteContato;
	}


	@Column(name = "musicaCompositor", nullable = false, length = 150)
	public String getMusicaCompositor() {
		return musicaCompositor;
	}


	public void setMusicaCompositor(String musicaCompositor) {
		this.musicaCompositor = musicaCompositor;
	}


	@Column(name = "linkCoreografia", nullable = true, length = 250)
	public String getLinkCoreografia() {
		return linkCoreografia;
	}


	public void setLinkCoreografia(String linkCoreografia) {
		this.linkCoreografia = linkCoreografia;
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
	@Column(name = "status", nullable = false, length = 7)
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
