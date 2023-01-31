package com.br.painelmobile.util.manipularDados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ConversorTexto {

	
	
	/**
	 * CONVERTE O TEXTO DE ISO-8859-1 PARA UTF-8
	 * @param texto: conteudo a ser convertido
	 * @return
	 */
	public static String doConverterCodificacao(String texto) {
		String output = "Regional â€“ CICC_R â€";

		/* From ISO-8859-1 to UTF-8 */
		try {
			output = new String(texto.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//output = new String(texto.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
		return output;	
	}
		
	
	
	
	
	/**
	 * Ler arquivo e tenta tratar o html retirando as tag, também converte para utf-8
	 * @param caminho: local do arquivo a ser lido
	 * @return string
	 */
	public static String dolerHtmlTentaRetirarTag(String caminho) {
		String saida = "";
		try {
			InputStream entrada = new FileInputStream(caminho);
			InputStreamReader reader = new InputStreamReader(entrada);
			BufferedReader buffer = new BufferedReader(reader);
			String texto = buffer.readLine();

			// subustituição de caracteres
			saida = texto.replaceAll("\\<.*?>", "");
			// texto.replaceAll("<", "");

			buffer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doConverterCodificacao(saida);

	}	

	
	
	
	/**
	 * decodifica um texto html de iso para utf8, o texto fica com as tags
	 * @param caminho: local do arquivo a ser lido
	 * @throws IOException
	 */
	public static void doLerLinhaHtmlOficial(String caminho) throws IOException {

		InputStream entrada = new FileInputStream(caminho);
		InputStreamReader reader = new InputStreamReader(entrada);
		BufferedReader in = new BufferedReader(reader);
		// BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			
			byte[] bytesDoTextoEmISO = inputLine.getBytes();
					String texto = new String(bytesDoTextoEmISO, "ISO-8859-1");
					
			byte[] bytesDoTextoEmUTF = texto.getBytes("UTF-8");
			String texto02 = new String(bytesDoTextoEmUTF, "UTF-8");
					
					System.out.println(texto02);
			
			
			/*System.out.println(doConverterCodificacao(inputLine)
					.replace("GUARNI�?�?O", "GUARNIÇÃO"));*/
		}

		in.close();
	}
	
	

	/**
	 * pega todo texto de um site e transforma em texto
	 * @param uriStr: caminho da uri que será convertida em texto
	 * @return
	 */
	@SuppressWarnings("serial")
	public static String dogetPage(String uriStr) {
		final StringBuffer buf = new StringBuffer(1000);
		try {
			HTMLDocument doc = new HTMLDocument() {
				@Override
				public HTMLEditorKit.ParserCallback getReader(int pos) {
					return new HTMLEditorKit.ParserCallback() {
						@Override
						public void handleText(char[] data, int pos) {
							buf.append(data);
							buf.append('\n');
						}
					};
				}
			};
			URL url = new URI(uriStr).toURL();
			URLConnection conn = url.openConnection();
			Reader rd = new InputStreamReader(conn.getInputStream());
			EditorKit kit = new HTMLEditorKit();
			kit.read(rd, doc, 0);
		} catch (MalformedURLException e) {
		} catch (URISyntaxException e) {
		} catch (BadLocationException e) {
		} catch (IOException e) {
		}
		// Retorna todo o texto encontrado
		System.out.println(buf.toString());
		return buf.toString();
	}
	
	
	/**
	 * Lista todos os encodigins suportados pela vm tais como US-ASCII, Cp1252 e UTF-16
	 */
	public static void verificaEncodingSuportadoPelaVm(){			
		System.out.println(Charset.availableCharsets());		
	}	
	
	
	public static void gravarEmArquivoTXT(String string) throws IOException {
		BufferedWriter fr = new BufferedWriter(new FileWriter("C:\\texto.txt",true));//Abre arquivo para escrita
			 fr.write(string+";");//escreve a matricula do aluno no arquivo
			
                         fr.newLine();//passa para a proxima linha
                         fr.flush();
		         fr.close();
}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			@SuppressWarnings("unused")
			String input = "Salada c/ abÃ³bora e cebolinha";
						
			String caminho = "c:\\texto.txt";
			
			doLerLinhaHtmlOficial(caminho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
/*	
	public static String ISOtoUTF8(String str){
		Charset utf8charset = Charset.forName("UTF-8");
		Charset iso88591charset = Charset.forName("ISO-8859-1");
		
		ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());
		
		// decode UTF-8
		CharBuffer data = iso88591charset.decode(inputBuffer);
		
		// encode ISO-8559-1
		ByteBuffer outputBuffer = utf8charset.encode(data);
		byte[] outputData = outputBuffer.array();
		
		return new String(outputData);
	}*/
	
	
	/**
	 * CONVERTE O TEXTO DE ISO-8859-1 PARA UTF-8
	 * @param texto: conteudo a ser convertido
	 * @return
	
	public static String doConverterCodificacao02(String texto) {
		byte[] output;
		String s = "";

		try {
			// From ISO-8859-1 to UTF-8 			
			output = new String(texto.getBytes(), "ISO-8859-1").getBytes("UTF-8");
				
			s = new String (output);
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	*/
	
	
	
	

}
