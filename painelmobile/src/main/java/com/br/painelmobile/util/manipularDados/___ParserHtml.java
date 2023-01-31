package com.br.painelmobile.util.manipularDados;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOCardapioDetalhado;
import com.br.painelmobile.modelo.persistencia.entidade.dto.DTOComposicaoCardapio;

public class ___ParserHtml {

	private static Scanner scanner;

	private static String htmlCardapio = "[container xclass=\"hv-cardapio\"]\n"
			+ "<p style=\"text-align: center;\"><span style=\"font-size: 24pt;\">Restaurantes do Sesc  AM (Balneário e Centro)</span></p>\n"
			+ "<p style=\"text-align: center;\"><strong><span style=\"font-size: 14pt;\">Dia:  16</span></strong><strong><span style=\"font-size: 14pt;\">.07.19</span></strong></p>\n"
			+ "<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\">Acesso somente com o Cartão Sesc atualizado</span></p>\n"
			+ "<p style=\"text-align: center;\"><span style=\"font-size: 12pt; color: #000000;\">Horário de funcionamento - 11h às 14h</span></p>\n"
			+ "<p style=\"text-align: center;\"><span style=\"color: #000000;\">Unidade Balneário - todos os dias</span></p>\n"
			+ "<p style=\"text-align: center;\"><span style=\"color: #000000;\">Unidade Centro - de segunda a sexta-feira</span></p>\n"
			+ "[row]\n"
			+ "[column md=\"6\" <span style=\"font-family: 'book antiqua', palatino, serif;\">]</span>\n"
			+ "\n"
			+ "<img class=\"alignnone size-full wp-image-1676\" src=\"https://www.sesc-am.com.br/wp-content/uploads/2018/09/img-cardapio-lateral.jpg\" alt=\"\" width=\"360\" height=\"980\" />\n"
			+ "\n" + "[/column]\n" + "[column md=\"6\"]\n" + "\n"
			+ "[panel type=\"info\" heading=\"SALADAS\" ]\n" + "<ul>\n"
			+ " 	<li>Grão de bico e soja ao vinagrete</li>\n"
			+ " 	<li>Cenoura c/ repolho</li>\n" + " 	<li>Alface e Tomate</li>\n" + "</ul>\n"
			+ "[/panel]\n" + "\n" + "[panel type=\"info\" heading=\"PROTEÍNAS\" ]\n" + "<ul>\n"
			+ " 	<li>Bife de fígado acebolado</li>\n" + " 	<li>Estrogonofe de frango</li>\n"
			+ " 	<li>Bife a milanesa</li>\n" + "</ul>\n" + "[/panel]\n" + "\n"
			+ "[panel type=\"info\" heading=\"ACOMPANHAMENTOS\"]\n" + "<ul>\n"
			+ " 	<li>Arroz Branco</li>\n" + " 	<li>Baião de dois</li>\n"
			+ " 	<li>Feijão c/ aparas</li>\n" + " 	<li>Macarrão ao alho e óleo</li>\n"
			+ "</ul>\n" + "[/panel]\n" + "\n" + "[panel type=\"info\" heading=\"GUARNIÇÃO\"]\n"
			+ "<ul>\n" + " 	<li>Batata frita</li>\n" + " 	<li>Canja</li>\n"
			+ " 	<li>Farofa c/banana</li>\n" + "</ul>\n" + "[/panel]\n" + "\n"
			+ "[panel type=\"info\" heading=\"SOBREMESAS E FRUTAS\"]\n" + "<ul>\n"
			+ " 	<li>Frutas da estação</li>\n" + "</ul>\n" + "[/panel]\n" + "\n" + "[/column]\n"
			+ "[/row]\n" + "\n" + "<hr />\n" + "\n"
			+ "<span style=\"font-size: 14pt; color: #000000;\"><strong>Preços</strong></span>\n"
			+ "\n" + "<strong>Restaurante Sede (Balneário) e Restaurante Centro:</strong>\n" + "\n"
			+ "Trabalhadores do Comércio e dependentes: R$ 18,00/quilo\n" + "\n"
			+ "Conveniados: R$ 23,00/quilo\n" + "\n" + "Público em geral: R$ 31,00/quilo\n" + "\n"
			+ "Visitante sem carteira: R$ 46,00/quilo\n" + "\n"
			+ "<strong>Refeição transportada* (quentinha 600 gramas / embalagem sem divisórias):</strong>\n"
			+ "\n" + "Trabalhadores do Comércio e dependentes: R$ 12,00\n" + "\n"
			+ "Conveniados: R$ 16,00\n" + "\n" + "Público em geral: R$ 17,00\n" + "\n"
			+ "<strong>Refeição transportada* (quentinha 600 gramas / embalagem com divisórias):</strong>\n"
			+ "\n" + "Trabalhadores do Comércio e dependentes: R$ 14,00\n" + "\n"
			+ "Conveniados: R$ 18,00\n" + "\n" + "Público em geral: R$ 19,00\n" + "\n"
			+ "<address>*Apenas para empresas conveniadas ao Sesc.</address>[/container]";


	/** Obtem todas as uris de imagens contidas no html passado por parametro
	 * @param htmlFragment: fragmento do html a ser analisado
	 * @return */
	public static List<String> obterUrldaImagem(String htmlFragment) {
		String corpo = htmlFragment;

		Document doc;
		doc = Jsoup.parseBodyFragment(corpo);// ler o fragmento do html
		List<String> listaUriImagem = new ArrayList<String>();

		Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif|bmp)]");
		for (Element image : images) {
			listaUriImagem.add(image.attr("src"));
			/* System.out.println("\nsrc : " + image.attr("src")); System.out.println("height : " +
			 * image.attr("height")); System.out.println("width : " + image.attr("width"));
			 * System.out.println("alt : " + image.attr("alt")); */
		}

		return listaUriImagem;
	}


	/** Converte um fragmento com tag html e remove a tag <a>
	 * @param htmlFragmento: string com tag html
	 * @return retorna uma string de texto sem tag html */
	public static String converterHtmlEmTexto(String htmlFragmento) {
		Document doc;
		doc = Jsoup.parseBodyFragment(htmlFragmento);// ler o fragmento do html
		// remove todo o tipo de link
		doc.select("a").remove();
		//HtmlToPlainText formatter = new HtmlToPlainText();
		//String plainText = formatter.getPlainText(doc);
		// System.out.println(plainText);

		return doc.text();
	}


	/** Converte um fragmento com tag html em texto sem html
	 * @param htmlFragmento: string com tag html
	 * @return retorna uma string de texto sem tag html */
	public static String converterHtmlEmTexto_RemoveTodasAsTags(String htmlFragmento) {
		Document doc;
		doc = Jsoup.parseBodyFragment(htmlFragmento);// ler o fragmento do html
		// remove todo o tipo de link
		doc.select("tag").remove();

		String HtmlToPlainText = Jsoup.parse(htmlFragmento).text();

		return HtmlToPlainText;
	}


	/** retira a tag IMG do html e retorna o html sem a imagem
	 * @param htmlFragmento
	 * @return */
	public static String removeTagImgdoHtml(String htmlFragmento) {
		Document doc;
		doc = Jsoup.parseBodyFragment(htmlFragmento);// ler o fragmento do html

		// remove todo o tipo de link e IMG
		doc.select("a").remove();
		doc.select("img").remove();

		String plainText = doc.html();
		// System.out.println(plainText);

		return plainText;
	}


	// Ler html puro, também converte para utf-8
	public static String doLerHtmlComTag(String caminho) {
		String saida = "";

		// Leitura de arquivo local da maquina
		try {
			InputStream entrada = new FileInputStream(caminho);
			scanner = new Scanner(entrada);

			while (scanner.hasNextLine()) {
				saida += ('\n' + scanner.next());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ConversorTexto.doConverterCodificacao(saida);
	}


	/** @param htmlFragmento
	 * @return */
	/**
	 * @param htmlFragmento
	 * @return
	 */
	public static String converterCardapioHtmlEmTexto(String htmlFragmento) {
		Document doc;
		String htmlSemColchetes;
		htmlSemColchetes = htmlFragmento.replaceAll("\\[", "<");
		htmlSemColchetes = htmlSemColchetes.replaceAll("\\]", ">");
		doc = Jsoup.parseBodyFragment(htmlSemColchetes);// ler o fragmento do html
		
		// String resultado = doc.select("container").attr("xclass");
		Elements resultado = doc.select("panel");
		for (Element element : resultado) {
			String item = "";
			String opcao = element.select("panel").attr("heading");			
			
			
			
			Elements children = element.select("li");
			for (Element li : children) {
				item += li.getElementsByTag("li").text()+"; " ;
				
				
			}
			
			System.out.println(opcao+": " + " " + item);
		}

		return "";
	}

	
	
	/**
	 * @param htmlFragmento
	 * @return
	 */
	private static List<DTOComposicaoCardapio> listarOpcoesEItensDoCardapio(String htmlSemShortCode) {
		Document doc;
		
		List<DTOComposicaoCardapio> listaComposicaoCardapio = new ArrayList<DTOComposicaoCardapio>();
		DTOComposicaoCardapio composicaoCardapio;
		
		doc = Jsoup.parseBodyFragment(htmlSemShortCode);// ler o fragmento do html
		Elements listaPainel = doc.select("panel");
		// System.out.println( listaPainel.select("panel").attr("heading") );

		for (Element painel : listaPainel) {
			composicaoCardapio = new DTOComposicaoCardapio();
			List<String> itens = new ArrayList<String>();
			String opcao = painel.select("panel").attr("heading");

			Elements listaDeItens = painel.select("li");
			for (Element li : listaDeItens) {
				//itens += li.text() + "; ";
				itens.add( li.text() );		
			}
			
			composicaoCardapio.setOpcao(opcao);
			composicaoCardapio.setItens(itens);
			listaComposicaoCardapio.add(composicaoCardapio);
			
			
		}

		// FALTA RETORNO DE SISTEMA
		return listaComposicaoCardapio;
	}

	/**
	  
	 * @meth exibir cabelho do cardapio
	 * @param htmlFragmento
	 * @return
	 */
	private static String ObterCabecalhoCardapio(String htmlSemShortCode) {
		String cabecalhoCardapio = "";
		Document doc;		

		doc = Jsoup.parseBodyFragment(htmlSemShortCode);// ler o fragmento do html
		Elements listaDeRow = doc.select("row");
		// TRANSFORMAR O ROW EM ELEMENTO, SÓ ASSIM ELE CHAMA O METODO
		// previousElementSiblings()
		for (Element row : listaDeRow) {
			cabecalhoCardapio = "";
			Elements ListaDetagsIrmaosQueEstaoAcimaDoRow = row.previousElementSiblings();

			// inverte a ordem da lista
			Collections.reverse(ListaDetagsIrmaosQueEstaoAcimaDoRow);

			for (Element tagIrmaoQueEstaoAcimaDoRow : ListaDetagsIrmaosQueEstaoAcimaDoRow) {
				cabecalhoCardapio += tagIrmaoQueEstaoAcimaDoRow.text() + "; ";
			}

		}

		
		return cabecalhoCardapio;
	}

	/**
	 * @meth exibir rodape do cardapio
	 * @param htmlFragmento
	 * @return
	 */
	private static String ObterRodapeCardapio(String htmlSemShortCode) {
		String rodapeCardapio = "";
		Document doc;		

		doc = Jsoup.parseBodyFragment(htmlSemShortCode);// ler o fragmento do html
		Elements listaDeRow = doc.select("row");
		// TRANSFORMAR O ROW EM ELEMENTO, SÓ ASSIM ELE CHAMA O METODO
		// previousElementSiblings()
		for (Element row : listaDeRow) {
			rodapeCardapio = "";
			Elements ListaDetagsIrmaosQueEstaoAbaixoDoRow = row.nextElementSiblings();

			// inverte a ordem da lista
			// Collections.reverse(ListaDetagsIrmaosQueEstaoAbaixoDoRow);

			for (Element tagIrmaoQueEstaoAcimaDoRow : ListaDetagsIrmaosQueEstaoAbaixoDoRow) {
				rodapeCardapio += tagIrmaoQueEstaoAcimaDoRow.text() + "; ";
			}

		}

		// FALTA RETORNO DE SISTEMA
		return rodapeCardapio;
	}
	
	
	
	
	
	/**
	 * @method
	 * @param htmlFragmento
	 * @return
	 */
	public static DTOCardapioDetalhado obterInformacoesDoCardapioWordpress( String htmlFragmento ) {
		
		DTOCardapioDetalhado dtoCardapioDetalhado = new DTOCardapioDetalhado();
		
		//ALTERA O SHORTCODE DO HTML
		String htmlSemShortCode;
		htmlSemShortCode = htmlFragmento.replaceAll("\\[", "<");
		htmlSemShortCode = htmlSemShortCode.replaceAll("\\]", ">");		
		
		String cabecalho = ObterCabecalhoCardapio(htmlSemShortCode);
		String rodape = ObterRodapeCardapio(htmlSemShortCode);
		List<DTOComposicaoCardapio> listaComposicaoCardapio = listarOpcoesEItensDoCardapio(htmlSemShortCode);
		
		dtoCardapioDetalhado.setCabecalho(cabecalho);
		dtoCardapioDetalhado.setRodape(rodape);
		dtoCardapioDetalhado.setListaComposicaoCardapio(listaComposicaoCardapio);
	
		
		return dtoCardapioDetalhado;
		
	}

	
	
	
	
	
	/** @param args */

	public static void main(String[] args) {

		Document doc;
		try {
				
			DTOCardapioDetalhado dtoCardapioDetalhado = obterInformacoesDoCardapioWordpress(htmlCardapio);
			
			System.out.println(dtoCardapioDetalhado.getCabecalho());
			System.out.println(dtoCardapioDetalhado.getRodape());
			
			for (DTOComposicaoCardapio dtoComposicaoCardapio : dtoCardapioDetalhado.getListaComposicaoCardapio()) {
				System.out.println( dtoComposicaoCardapio.getOpcao() );
				System.out.println( dtoComposicaoCardapio.getItens() );
			}
			
			
			String rodape = ObterRodapeCardapio(htmlCardapio);
			System.out.println(rodape);

			String cabecalho = ObterCabecalhoCardapio(htmlCardapio);
			System.out.println(cabecalho);

			
			
			
			// doc = Jsoup.connect("https://www.sesc-am.com.br/home/cardapio/").get();
			// doc.select("a").remove();
			// System.out.println(doc.toString());
			// Element link = doc.select("a").first();
			// String teste = doc.attr("abs:href");
			// System.out.println("--------------------------");

			// HtmlToPlainText formatter = new HtmlToPlainText();
			// String plainText = formatter.getPlainText(doc);
			// System.out.println(plainText);

			/* String relHref = link.attr("href"); // == "/" String absHref = link.attr("abs:href");
			 * // "http://jsoup.org/"
			 * 
			 * System.out.println(absHref); */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* Document doc; String corpo = doLerHtmlComTag("c:\\dados2.txt"); doc =
		 * Jsoup.parseBodyFragment(corpo);//ler o fragmento do html
		 * 
		 * 
		 * Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]"); for (Element image :
		 * images) {
		 * 
		 * System.out.println("\nsrc : " + image.attr("src")); System.out.println("height : " +
		 * image.attr("height")); System.out.println("width : " + image.attr("width"));
		 * System.out.println("alt : " + image.attr("alt"));
		 * 
		 * }
		 * 
		 * HtmlToPlainText formatter = new HtmlToPlainText();
		 * 
		 * String plainText = formatter.getPlainText(doc); System.out.println(plainText); */

	}

}
