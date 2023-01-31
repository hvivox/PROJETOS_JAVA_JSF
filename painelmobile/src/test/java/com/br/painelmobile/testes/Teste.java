package com.br.painelmobile.testes;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;

public class Teste {
	Evento evento02;
	
	
	public static void main(String[] args) {
		
		Teste teste = new Teste();
		teste.verificacaoEvento();
		
		
		

	}

	public void verificacaoEvento(){
		Evento evento = new Evento();
		
		
		
		if(evento.getId() == null){
			System.out.println("entrou" +evento.getId());
		}
		if(( evento instanceof Object)){
			System.out.println("entrou" +evento02.getId());
		}
		
	}
	
}
