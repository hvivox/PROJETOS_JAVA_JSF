package com.br.formulario.controle.mb.teste;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.util.managerbean.diversos.FacesUtil;

@Named("mbTeste")
@ViewScoped
public class MBTeste implements Serializable {

	private static final long serialVersionUID = 1L;

	private AmazonasArtes amazonasArtes;

	private UploadedFile uploadFile;
	private String exibirNomeArquivo;
	private static final String caminhoFisicoImagemDocumento = "C:/zend/Apache2/htdocs/UPLOAD_JAVA/FORMULARIO";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://localhost:8080/formulario/documento";
	private String nomeUpload;
	private FileUploadEvent event;
	private UploadedFile uploadFile2;
	private String nome;
	private InputStream inputStr = null;


	public MBTeste() {
		limparFormulario();
	}


	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			System.out.println("Não é postBack");
		}
	}


	public void autoUpload(FileUploadEvent event) {	
		this.event = event;
		this.nomeUpload = event.getFile().getFileName();
		this.uploadFile2 = event.getFile();
		
		try {	
			inputStr = uploadFile2.getInputstream();
		} catch (IOException e) {
			// log error
		}
		
		setNomeUpload(nomeUpload);
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName()
				+ " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		caminhoExterno();
		
		
	}


	public void verifcaDado() {
		try {
			FileUtils.copyInputStreamToFile(inputStr, new File("c:/hermogenes.xls"));
			System.out.println(inputStr.getClass().getName().toString()
					+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		} catch (IOException e) {

		}
	}


	public void caminhoExterno() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String directory = externalContext.getInitParameter("uploadDirectory");
		System.out.println("caminho externo @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@: " + directory);

	}



	public String getNomeUpload() {
		return nomeUpload;
	}


	public void setNomeUpload(String nomeUpload) {
		this.nomeUpload = nomeUpload;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	/** SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException */
	public String salvar() {
		try {
			verifcaDado();
			// VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O amazonasArtes.getId()
			if ((amazonasArtes instanceof Object)) {

				// NAO HOUVE O UPLOAD
				if (!(uploadFile2 instanceof Object) || uploadFile2.getSize() <= 0) {

					FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
							"Selecione a ficha técnica para salvar e concluir a inscrição!");
					return "";
				}
				
				// HOUVE UPLOAD - FUNCIONA NO MODO NOVO OU EDITAR
				else {

					gravarImagemNoDisco();
					// Salva a imagem no banco

					limparFormulario();

					FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");

				}
				
				
			}
		} catch (campoObrigatorioNaoPreenchido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		}

		return "relacao-modalidade-amazonas-artes.xhtml?faces-redirect=true";
	}


	public void gravarImagemNoDisco() {

		try {
			// PEGA AS INFORMAÇÕES DO UPLOAD CARREGADO
			UploadedFile uploadFile2 = this.uploadFile2;

			// GERA UM CAMINHO FISICO DE ACORDO COM AS INFORMAÇÕES PASSADAS
			Path caminhoCompletoImagemComNomeArquivo = gerarCaminhoFisicoComNomeDoArquivo(
					caminhoFisicoImagemDocumento, uploadFile2);

			// grava a imagem no disco fisico

			gravarArquivoNoDisco(uploadFile2.getContents(),
					caminhoCompletoImagemComNomeArquivo.toString());

		} catch (IOException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).fatal(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
					"Erro ao tentar gravar imagem, favor contate o administrador");
		}
	}


	/** CONCATENA CAMINHO ABSOLUTO E NOVO NOME DA IMAGEM SALVA
	 * @param caminhoAbsoImagemDocumento: recebe String neste formato ex: :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemDocumento,
			UploadedFile uploadFile2) throws IOException {
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemDocumento);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile2.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile2.getFileName());

		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		amazonasArtes.setNomeOriginalDoc(uploadFile2.getFileName());
		amazonasArtes.setNomeDoc(novoNomeUpload);
		amazonasArtes.setUri(linkSemNomeDaImagem + "/" + novoNomeUpload);

		return caminhoComNomeArquivo;
	}


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


	/** OBTERM O NOME DO ARQUIVO
	 * @return STRING com o nome do caminho
	 * @throws IOException */
	public String getExibirNomeArquivo() {
		this.exibirNomeArquivo = "";
		if (uploadFile2 != null) {
			this.exibirNomeArquivo = uploadFile2.getFileName().concat(" -  ")
					.concat(String.valueOf(uploadFile2.getSize())).concat(" Bytes");
		}

		return this.exibirNomeArquivo;
	}


	private void limparFormulario() {
		amazonasArtes = new AmazonasArtes();
	}


	public AmazonasArtes getAmazonasArtes() {
		if (amazonasArtes == null) {
			amazonasArtes = new AmazonasArtes();
		}

		return amazonasArtes;
	}


	public void setAmazonasArtes(AmazonasArtes amazonasArtes) {
		this.amazonasArtes = amazonasArtes;
	}


	public UploadedFile getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}
