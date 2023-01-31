package com.br.painelmobile.testes;

import java.io.UnsupportedEncodingException;

public class TesteConversaoTexto {

	public static void main(String[] args) {
		TesteConversaoTexto teste = new TesteConversaoTexto();
		teste.conversao();

	}

	private void conversao() {
		try {
			byte[] bytesDoTextoEmISO = "Regional â€“ CICC_R â€".getBytes("ISO-8859-1");
					String texto = new String(bytesDoTextoEmISO, "ISO-8859-1");
					byte[] bytesDoTextoEmUTF = texto.getBytes("UTF-8"); 
					String textoTF8 = new String(bytesDoTextoEmUTF, "UTF-8");
	
					System.out.println(textoTF8);
					
			String output = new String("Regional â€“ CICC_R â€".getBytes("ISO-8859-1"), "UTF-8");
			System.out.println(output);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
