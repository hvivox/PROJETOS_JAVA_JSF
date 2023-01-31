package com.br.sdni.modelo.persistencia.entidade.mapeadas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;

@Entity
@Table(name = "usuario", catalog = "sdni")
@AttributeOverride(name = "id", column = @Column(name = "idusuario"))
public class Usuario extends ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String loginUsuario;
	private String nomeCompleto;
	private String email;
	private String senha;
	private StatusEntidadeEnum estatus;
	private List<Grupo> grupos = new ArrayList<>();


	@Column(name = "loginUsuario", nullable = false, unique = true, length = 255)
	public String getLoginUsuario() {
		return loginUsuario;
	}


	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}


	@Column(name = "nomeCompleto", nullable = false, unique = true, length = 80)
	public String getNomeCompleto() {
		return nomeCompleto;
	}


	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}


	@Column(name = "email", nullable = true, length = 255)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name = "senha", nullable = false, length = 255)
	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", nullable = false, length = 7)
	public StatusEntidadeEnum getEstatus() {
		return estatus;
	}


	public void setEstatus(StatusEntidadeEnum estatus) {
		this.estatus = estatus;
	}


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	public List<Grupo> getGrupos() {
		return grupos;
	}


	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}