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
@Table(name = "FestivalNovosTalentos", catalog = "formulario")
@AttributeOverride(name = "id", column = @Column(name = "idNovoTalento"))
public class FestivalNovosTalentos extends ValueObject{
	

	private static final long serialVersionUID = -5802869956108720586L;
	private Calendar dtaInscricao;
	private String nome;
	private Calendar dtaNascimento;
	
	private String cidade;
	private SiglasEstados estado;	
	private String endereco;
	
	
	
	private String fone;
	private String whatZapp;
	private String email;
	
	
	private String musica;
	private String compositor;	
	
	private StatusEntidadeEnum status;
	private Boolean aceiteRegulamento;
	
	
	
	
	@Column(name = "nome", nullable = false, length = 150)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaNascimento", nullable = false)
	public Calendar getDtaNascimento() {
		return dtaNascimento;
	}
	public void setDtaNascimento(Calendar dtaNascimento) {
		this.dtaNascimento = dtaNascimento;
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
	
	@Column(name = "endereco", nullable = true, length = 250)
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
	
	
	@Column(name = "musica", nullable = false, length = 150)
	public String getMusica() {
		return musica;
	}
	public void setMusica(String musica) {
		this.musica = musica;
	}
	

	@Column(name = "compositor", nullable = false, length = 150)
	public String getCompositor() {
		return compositor;
	}
	public void setCompositor(String compositor) {
		this.compositor = compositor;
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
