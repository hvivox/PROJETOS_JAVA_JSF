package com.br.sdni.modelo.persistencia.entidade.enums;

public enum StatusEntidadeEnum {
	ATIVO("ATIVO"), INATIVO("INATIVO");
	
	private String status;
	
	
	
	private StatusEntidadeEnum(String status){
		this.status = status;
	}	
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString(){
		return this.status;
	}
	
}
