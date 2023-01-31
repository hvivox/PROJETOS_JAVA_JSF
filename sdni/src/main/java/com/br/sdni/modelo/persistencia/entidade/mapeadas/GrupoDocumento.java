package com.br.sdni.modelo.persistencia.entidade.mapeadas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;


@Entity
@Table(name = "grupodocumento", catalog = "sdni")
@AttributeOverride(name = "id", column = @Column(name = "idGrupoDoc"))
public class GrupoDocumento extends ValueObject {

	private static final long serialVersionUID = 4211898282250941219L;

	private String nomeGrupoDoc;
	private StatusEntidadeEnum status;
	private List<Documento> documentos = new ArrayList<Documento>();
	

	public GrupoDocumento() {

	}
	
	
	@Column(name = "nomeGrupoDoc", nullable = false, length = 150)
	public String getNomeGrupoDoc() {
		return nomeGrupoDoc;
	}


	public void setNomeGrupoDoc(String nomeGrupoDoc) {
		this.nomeGrupoDoc = nomeGrupoDoc;
	}

	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 7)
	public StatusEntidadeEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEntidadeEnum status) {
		this.status = status;
	}	
	
	

	@OneToMany( targetEntity = Documento.class, mappedBy = "grupoDoc", fetch = FetchType.LAZY)
	public List<Documento> getDocumentos() {
		//documentos.get(1).getNomedoc()
		return documentos;
	}	
	
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}


	@Override
	public String toString() {
		return String.valueOf(getId()) ;
	}
	
	
	
	
}
