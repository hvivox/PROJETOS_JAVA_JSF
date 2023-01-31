package com.br.painelmobile.modelo.persistencia.entidade.enums;

public enum EstatusEntidadeEnum {
	ATIVO("ATIVO"), INATIVO("INATIVO");
	
	private String estatus;
	
	
	
	private EstatusEntidadeEnum(String estatus){
		this.estatus = estatus;
	}	
	
	
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	@Override
	public String toString(){
		return this.estatus;
	}
	
}
