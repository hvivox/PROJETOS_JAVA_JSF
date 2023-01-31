package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="pm_mensagem", catalog="portalsescam")
@AttributeOverride(name="id", column=@Column(name="idmensagem"))
public class Mensagem extends ValueObject {
	

	private static final long serialVersionUID = -4934035735303364957L;
	
	private String nomeRemetente;
	private String email;
	private String assunto;
	private String conteudo;
		
	
	public Mensagem() {
		
	}

	
	@Column(name = "nomeRemetente", nullable = false, length = 255)
	public String getNomeRemetente() {
		return nomeRemetente;
	}
	
	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}


	@Column(name = "email", nullable = false, length = 255)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name = "assunto", nullable = false, length = 255)
	public String getAssunto() {
		return assunto;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	@Column(name="conteudo", columnDefinition="TEXT", nullable = false)	
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
	
	
	
}
