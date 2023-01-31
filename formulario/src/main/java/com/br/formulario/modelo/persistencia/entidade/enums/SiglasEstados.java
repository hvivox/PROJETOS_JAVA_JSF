package com.br.formulario.modelo.persistencia.entidade.enums;

public enum SiglasEstados {
	AC("AC"),
	AL("AL"), 
	AP("AP"), 
	AM("AM"), 
	BA("BA"),
	CE("CE"),
	DF("DF"), 
	GO("GO"), 
	ES("ES"), 
	MA("MA"), 
	MT("MT"), 
	MS("MS"), 
	MG("MG"), 
	PA("PA"), 
	PB("PB"), 
	PR("PR"), 
	PE("PE"), 
	PI("PI"), 
	RJ("RJ"), 
	RN("RN"), 
	RS("RS"), 
	RO("RO"), 
	RR("RR"), 
	SP("SP"), 
	SC("SC"), 
	SE("SE"), 
	TO("TO");
	
	private String descricao;

	private SiglasEstados(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}


	
	
	
	
	
}
