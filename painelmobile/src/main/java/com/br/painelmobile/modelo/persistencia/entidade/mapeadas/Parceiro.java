package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;


@Entity
@Table(name = "pm_parceiro", catalog = "portalsescam")
@AttributeOverride(name = "id", column = @Column(name = "idparceiro"))
public class Parceiro extends ValueObject {

	private static final long serialVersionUID = 8894643585785670472L;

	private String titulo;
	private String nomeImagem;
	private String nomeImgOriginal;
	private String uriLogo;
	private EstatusEntidadeEnum estatus;
	private CategoriaParceiro catParceiro;


	public Parceiro() {

	}


	public Parceiro(Integer id, String uriLogo, CategoriaParceiro catParceiro) {
		super();
		this.setId(id);
		this.uriLogo = uriLogo;
		this.catParceiro = catParceiro;
	}


	@Column(name = "titulo", nullable = false, length = 150)
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	@Column(name = "nomeImgOriginal", nullable = false, length = 100)
	public String getNomeImgOriginal() {
		return nomeImgOriginal;
	}


	public void setNomeImgOriginal(String nomeImgOriginal) {
		this.nomeImgOriginal = nomeImgOriginal;
	}


	@Column(name = "nomeImagem", nullable = false, length = 100)
	public String getNomeImagem() {
		return nomeImagem;
	}


	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}


	@Column(name = "uriLogo", nullable = true, length = 255)
	public String getUriLogo() {
		return uriLogo;
	}


	public void setUriLogo(String uriLogo) {
		this.uriLogo = uriLogo;
	}


	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", nullable = false, length = 7)
	public EstatusEntidadeEnum getEstatus() {
		return estatus;
	}


	public void setEstatus(EstatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}


	@ManyToOne(targetEntity = CategoriaParceiro.class)
	@ForeignKey(name = "fk_idCategoriaParceiro")
	// nome da FK para a config BD
	@JoinColumn(name = "parceiro_idCategoriaParceiro", nullable = false)
	// nome da FK na tabela
	public CategoriaParceiro getCatParceiro() {
		return catParceiro;
	}


	public void setCatParceiro(CategoriaParceiro catParceiro) {
		this.catParceiro = catParceiro;
	}

}
