package com.br.sdni.modelo.persistencia.entidade.mapeadas;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;


@Entity
@Table(name="Documento", catalog="sdni")
@AttributeOverride(name="id", column=@Column(name="iddocumento"))
public class Documento extends ValueObject{
	

	private static final long serialVersionUID = 4508484380771850573L;
	private String titulo;
	private String nomedoc;
	private String nomeDocOriginal;
	private String uridoc;
	private StatusEntidadeEnum status;
	private Calendar dtaCadastro;
	private GrupoDocumento grupoDoc;
	
	
	public Documento(){

	}

	
	@Column(name = "titulo", nullable = false, length = 200)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	
	@Column(name = "nomedoc", nullable = false, length = 100)
	public String getNomedoc() {
		return nomedoc;
	}

	public void setNomedoc(String nomedoc) {
		this.nomedoc = nomedoc;
	}

	
	
	@Column(name = "nomeDocOriginal", nullable = false, length = 250)
	public String getNomeDocOriginal() {
		return nomeDocOriginal;
	}

	public void setNomeDocOriginal(String nomeDocOriginal) {
		this.nomeDocOriginal = nomeDocOriginal;
	}

	
	
	@Column(name = "uriDocumento", nullable = false, length = 255)
	public String getUridoc() {
		return uridoc;
	}

	public void setUridoc(String uridoc) {
		this.uridoc = uridoc;
	}

	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 7)
	public StatusEntidadeEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEntidadeEnum status) {
		this.status = status;
	}

		
		
	@Temporal(TemporalType.DATE)
	@Column(name = "dtaCadastro", nullable = false)	
	public Calendar getDtaCadastro() {
		return dtaCadastro;
	}

	public void setDtaCadastro(Calendar dtaCadastro) {
		this.dtaCadastro = dtaCadastro;
	}

	
		
	/*@ManyToOne(targetEntity = GrupoDocumento.class)
	@ForeignKey(name = "fk_idGrupoDoc")
	// nome da FK para a config BD
	@JoinColumn(name = "grupo_idGrupoDoc", nullable = false)
	// nome da FK na tabela	*/
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idGrupoDoc", nullable=false,
	columnDefinition= "INT(11)",
	foreignKey = @ForeignKey(name="fk_GrupoDoc"))
	public GrupoDocumento getGrupoDoc() {
		return grupoDoc;
	}
	
	
	public void setGrupoDoc(GrupoDocumento grupoDoc) {
		this.grupoDoc = grupoDoc;
	}
	
	
}
