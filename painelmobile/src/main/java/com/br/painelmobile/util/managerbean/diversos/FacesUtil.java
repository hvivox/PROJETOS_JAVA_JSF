package com.br.painelmobile.util.managerbean.diversos;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/** CONTÉM MÉTODOS: Para auxiliar no desenvolvimento e esconder toda a complexidade do framework e
 * componentes ALGUNS MÉTODOS NORMALMETNE NÃO SÃO ROTINEIRAMENTE UTILZADOS
 * @author hermogenes.silva */
public class FacesUtil {

	/** Obtem o contexto da sistema.
	 * @return facesContext */
	public static FacesContext getContexto() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}


	/** FORMA DE OBTER UM PARAMETRO ENVIADO PELA TELA, É UTILIZADO EM CONJUNTO COM ESTES COMANDOS
	 * <f:attribute name="attributeName1" value="#{imagem.id}" /> String attributeName1 =
	 * FacesUtil.getActionAttribute(event, "attributeName1");
	 * @param event
	 * @param name
	 * @return */
	public static String getActionAttribute(ActionEvent event, String name) {
		return (String) event.getComponent().getAttributes().get(name);
	}


	/** PEGA UMA PARAMETRO PASSADO PELA TELA UTILIZADO EM CONJUNTO COM ESTE COMANDO EXEMPLO: <f:param
	 * name="idImagem" value="#{imagem.id}" /> String idImagemExcluida =
	 * FacesUtil.getRequestParameter("idImagem");
	 * @param name nome do parametro definido na tela
	 * @return */
	public static String getRequestParameter(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get(name);
	}


	// verificar se a tela foi teve sua arvore de componentes limpa
	public static boolean isPostBack() {
		return FacesContext.getCurrentInstance().isPostback();
	}


	// verifica se a tela foi recarregada sem limpar a arvore de compomentes
	public static boolean isNotPostBack() {
		return !isPostBack();
	}


	/** @Conteudo: utilizado para pegar as informações que estão dentro do componente com o id
	 *            especificado
	 * @param idComponente é o nome do id do componente
	 * @return sempre irá retornar uma string; */
	public String obterParametroDaTela(String idComponente) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getRequestParameterMap().get(idComponente);

	}


	/** Este metodo é responsável por mostrar mensagens na tela.
	 * @Severity: mostra o nível de severidade da mensagem INFO, Warn, ERROR
	 * @Sumary: é o conteúdo da mensagem, neste caso esta mensagem está definida dentro do pacote
	 *          bundles no arquivo mensagem.properties
	 * @Detail: mostra o detalhe da mensagem normalmente fica apenas com as "" vazia produto */
	public static void addMessage(Severity severity, String summary, String detail) {
		// obtem a mensagem salvar especificada no pacote bundles dentro do
		// arquivo mensagens
		String mensagem = (String) ResourceBundle.getBundle("bundles.mensagens").getString(summary);
		// seta a mensagem na tela
		FacesMessage message = new FacesMessage(severity, mensagem, detail);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}


	/** Utilizado para mostrar as mensagens lançadas por meio de tratamento de exceção. Objetivo:
	 * sobrecarga do método addMensage para diferenciar mensagem de exceção
	 * @Severity: mostra o nível de severidade da mensagem INFO, Warn, ERROR
	 * @Sumary: é o conteúdo da mensagem, neste caso esta mensagem está definida dentro do pacote
	 *          bundles no arquivo mensagem.properties
	 * @Detail: mostra o detalhe da mensagem normalmente fica apenas com as "" vazia produto OBS:
	 *          parametro detail esta fixo com "" e pode não funcionar */
	public static void addMessage(Severity severity, String summary) {
		// seta a mensagem na tela
		FacesMessage message = new FacesMessage(severity, summary, "");

		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	// Comando para setar e pegar informações da seção do browser
	/* FacesContext context = FacesContext.getCurrentInstance(); session = (HttpSession)
	 * context.getExternalContext().getSession(true); session.setAttribute("file",
	 * file.getContents()); //this.fotoCarregada = (byte[]) session.getAttribute("file"); */

	/* COMANDOS PARA PEGAR ATRIBUTOS QUE ESTÃO NA SESSÃO. ELE LISTA AS CHAVES E VC SETA O ATRIBUTO
	 * DESEJADO else { ExternalContext externalContext =
	 * FacesContext.getCurrentInstance().getExternalContext(); Map<String, Object> sessionMap =
	 * externalContext.getSessionMap(); for(String key: sessionMap.keySet()) System.out.println(key
	 * + " - " + sessionMap.get(key)); System.out.println(); PersonBean personBean = (PersonBean)
	 * sessionMap.get("personBean"); Person person = personBean.getPerson();
	 * System.out.println("After casting to person"); String filenamenew =
	 * person.getFile().getFileName();
	 * System.out.println("phase NOT render_response AFter cast Uploaded File Name Is :: "
	 * +filenamenew); Long filesize = person.getFile().getSize();
	 * System.out.println("phase NOT render_response AFter cast Uploaded File Size Is :: "
	 * +filesize); UploadedFile file1 = person.getFile();
	 * System.out.println("phase NOT render_response Uploaded File Name Is :: "
	 * +file1.getFileName()+" :: Uploaded File Size :: "+file1.getSize()); return new
	 * DefaultStreamedContent(new ByteArrayInputStream(file1.getContents())); } */

}