package com.br.painelmobile.util.manipularDados;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;

public class ValidaEmail {
	/**
	 * Valida o email da mensagem
	 * @param email: email passado por parametro
	 * @return
	 */
	public static boolean isEmailValido(String email) throws WSTratamentoExcecaoGeral {
		boolean resultado = false;
		
		//verifica que o email tem @, se tem ponto e não tem espaço em branco
		if ((email.contains("@")) && (email.contains(".")) && (!email.contains(" "))) {
			String usuario = new String(email.substring(0, email.lastIndexOf('@')));
			String dominio = new String(email.substring(email.lastIndexOf('@') + 1, email.length()));

			
			/*O email esta dividido em duas parte usuario @ dominio
			 * verifica se o lado do usuario tem mais de 1 caractere e verificar se tem o @
			 * outras verificações
			 * */
			if ((usuario.length() >= 1) && (!usuario.contains("@")) && (dominio.contains("."))
					&& (!dominio.contains("@")) && (dominio.indexOf(".") >= 1)
					&& (dominio.lastIndexOf(".") < dominio.length() - 1)) {
				resultado = true;

			} 
			else {
				resultado = false;
				throw new WSTratamentoExcecaoGeral(
						"{\"erro\":\"Preencha corretamente o email. Ex: joao@exemplo.com.br\"}");
			}

		} 
		else {
			resultado = false;
			throw new WSTratamentoExcecaoGeral(
					"{\"erro\":\"Preencha corretamente o email. Ex: joao@exemplo.com.br\"}");
		}

		return resultado;
	}
}
