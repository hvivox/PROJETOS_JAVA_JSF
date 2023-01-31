package com.br.painelmobile.controle.mb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbGerImagem")
@SessionScoped
public class MBGerImagem implements Serializable {

	private static final long serialVersionUID = 1L;
	// FALSE INDICA QUE O BOTAO DE UPLOAD NAO FOI PRESSIONADO
	
	private static final String caminhoImagemEmBranco = "/resources/images/imagemEmBranco.jpg";
	private String caminhoAbsolutoArmazenarImagemEvento;
	private int idEntidade = 0;
	private UploadedFile uploadFile;
	private String nomeImagem;

	@SuppressWarnings("unused")
	private StreamedContent imagem;// utilizado pelas telas .xhtml. Não apagar


	public MBGerImagem() {
		System.out.println("Instancia GerImagem............................");
	}


	/** REALIZA O UPLOAD DO ARQUIVO E CARREGA EM UMA VARIÁVEL
	 * @param event evento para manusear o arquivo enviado ao servidor */
	public void uploadImagem(FileUploadEvent event) {
		// converte a imagem para o tipo uploadFile. Envia a imagem para um MB
		// @sessionScoped, pois o GraphicImage não
		// funciona com o @ViewScoped para mostrar a imagem no GraphicImage
		setUploadFile(event.getFile());
	
	}


	/** METODO CHAMADO SOMENTE QUANDO A TELA FOR CARREGADA PELA PRIMEIRA VEZ ENCAMINHA IMAGEM PARA O
	 * IMAGEGRAPHIC, SE NÃO, ENVIA IMAGEM PADRAO
	 * @return StreamedContent */
	public StreamedContent getImagem() {
		System.out.println("Entrou no getImagem##########################");

		// VERIFICA SE O BOTAO UPLOAD FOI PRESSIONADO
		if (uploadFile instanceof Object) {
			// DEVE OCORRER SEMPRE QUE FOR PRESSIONADO O FILE UPLOAD
			return getImagemMemoria();
		}
		
		// SE O BOTÃO UPLOAD NAO FOI PRESSIONADO APÓS A RENDENRIZAÇÃO DA TELA, ELE
		// ENTRARÁ AQUI
		else {
			// NOVO CADASTRO - EXIBE DA IMAGEM NA MEMORIA
			if (idEntidade <= 0) {
				System.out.println("VALOR DA ENTIDADE TEM QUE SER ZERO: " + idEntidade);
				return getImagemMemoria();
			}

			// EDICAO DE CADASTRO - EXIBE IMAGEM EM DISCO
			else {
				System.out.println("VALOR DA ENTIDADE MAIOR QUE ZERO: " + idEntidade);
				return getImagemDisco();
			}
		}

	}


	/** MOSTRA IMAGEM DO DISCO. METODO UTILIZADO AO TENTAR ALTERAR UM CADASTRO COM IMAGEM
	 * @return StreamedContent */
	public StreamedContent getImagemDisco() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			System.out.println("getImagemDisco");
			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				return new DefaultStreamedContent();
			}
			else {
				System.out.println("Caminho: "+caminhoAbsolutoArmazenarImagemEvento+"/"+nomeImagem);
				
				return new DefaultStreamedContent(new FileInputStream(new File(
						caminhoAbsolutoArmazenarImagemEvento, nomeImagem)));
			}

		} catch (FileNotFoundException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR,
					"A imagem pode estar corropida, favor tente novamente");
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).fatal(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

			return new DefaultStreamedContent(FacesUtil.getContexto().getExternalContext()
					.getResourceAsStream(caminhoImagemEmBranco));
		}
	}


	/** PEGA IMAGEM DA MEMORIA CASO SEJA PRESSIONADO O BOTAO UPLOAD OU NO MOMENTO DA RENDERIZAO DE UM
	 * NOVO CADASTRO
	 * @return StreamedContent */
	public StreamedContent getImagemMemoria() {

		// Sen nulo exibe uma imagem padrão em branco
		if (!(uploadFile instanceof Object) || uploadFile == null) {
			return new DefaultStreamedContent(FacesUtil.getContexto().getExternalContext()
					.getResourceAsStream(caminhoImagemEmBranco));
		}
		
		else {
			// exibe a imagem do upload
			return new DefaultStreamedContent(new ByteArrayInputStream(uploadFile.getContents()));
		}
	}


	/** GRAVA ARQUIVO NO DISCO FISICO
	 * @param bytes
	 * @param arquivo */
	public void gravarArquivoNoDisco(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).fatal(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

		} catch (IOException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).fatal(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());

		}
	}


	/** DELETA ARQUIVO DO DISCO FISICO
	 * @param caminhoAbsolutoDaImagem */
	public void deletarArquivo(String caminhoAbsolutoComNomeArquivo) {
		File file = new File(caminhoAbsolutoComNomeArquivo);
		file.delete();
	}


	/** Utilizado pela classe @ViewScoped para obter as imagens
	 * @return */
	public UploadedFile getUploadFile() {
		return uploadFile;
	}


	/** RECEBE O UPLOAD DO @VIEWSCOPED setRealizouUpload(true): INDICA QUE O BOTAO DE UPLOAD FOI
	 * PRESSIONADO
	 * @param uploadFile */
	public void setUploadFile(UploadedFile uploadFile) {
		System.out.println("ENTROU setUploadFile ###########################");
		this.uploadFile = uploadFile;
	}


	/** RECEBE INFORMAÇÕES IMPORTANTES DO @VIEWSCOPED PARA PARAMETRIZAR MB @SESSIONSCOPED
	 * @param caminhoImagem
	 * @param idEntidade */
	public void parametrizarGerenciador(Integer idEntidade,
			String caminhoImagem, String nomeImagem, UploadedFile uploadCarregado) {
		
		this.idEntidade = idEntidade;
		this.caminhoAbsolutoArmazenarImagemEvento = caminhoImagem;
		this.nomeImagem = nomeImagem;		
		setUploadFile(uploadCarregado);	
		System.out.println("Parametrizou setRealizar uploda para mostrar imagem ");
	}


	

}
