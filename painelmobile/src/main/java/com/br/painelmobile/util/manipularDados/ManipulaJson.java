package com.br.painelmobile.util.manipularDados;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.ValueObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * @author hvivox
 *
 */

public class ManipulaJson {
	
	
	/**Adiciona novas propriedades ao Json
	 * @param entidade: qualquer entidade que extend ValueObject
	 * @param novaPropriedadeJson: priedade que deve ser adicionada do Json
	 * @param valor a ser adicionado a nova propriedade
	 * @return Object que contem um array com os dados
	 */
	public static Object adicionaPropriedadeJson(ValueObject entidade, String novaPropriedadeJson, String valor){
		Object jsonAlterado;
		
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(entidade);
		jsonElement.getAsJsonObject()
		.addProperty(novaPropriedadeJson, valor);//adiciona uma nova propriedade ao Json					
		jsonAlterado = gson.fromJson(gson.toJson(jsonElement), Object.class);		
		
		return jsonAlterado;
	}
	
	
	
	
	/**Remove as propriedades de um Json
	 * @param entidade: qualquer entidade que extend ValueObject
	 * @param Propridade: priedade que deve ser retirada do Json
	 * @return Object que contem um array com os dados
	 */
	public static Object removePropriedadeJson(ValueObject entidade, String Propridade){
		Object jsonAlterado;
		
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(entidade);
		jsonElement.getAsJsonObject().remove("dtaEvento");// remove uma propriedade do Json			
		gson.fromJson(gson.toJson(jsonElement), Object.class);
		jsonAlterado = gson.fromJson(gson.toJson(jsonElement), Object.class); //converte o Json em Objeto
		
		return jsonAlterado;
	}
	
	
	
}
