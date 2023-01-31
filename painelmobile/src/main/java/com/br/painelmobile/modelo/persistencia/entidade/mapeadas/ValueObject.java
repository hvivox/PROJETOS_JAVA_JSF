package com.br.painelmobile.modelo.persistencia.entidade.mapeadas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class ValueObject implements Serializable {	
	
	private static final long serialVersionUID = 323535032259483123L;	
	
	private Integer id;
		
	public ValueObject (){
		
	}
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object outro) {
		if ((outro == null) || !(outro instanceof ValueObject)) {
			return false;
		}
		return this.id == ((ValueObject) outro).id;
	}
	

}
