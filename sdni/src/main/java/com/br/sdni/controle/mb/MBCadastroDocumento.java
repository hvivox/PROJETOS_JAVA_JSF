package com.br.sdni.controle.mb;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.negocios.servico.ServiceDocumento;
import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.managerbean.diversos.FacesUtil;
import com.br.sdni.util.manipularDados.ConversorData;

@Named("mBCadastroDocumento")
@ViewScoped
public class MBCadastroDocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceDocumento serviceDocumento;
	@Inject
	private ServiceGrupoDocumento serviceGrupoDocumento;
	private UploadedFile uploadFile;
	private String exibirNomeArquivo;	
	private Documento documento;
	private List<GrupoDocumento> listaGrupoDocumento;
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	private StatusEntidadeEnum[] listaEstatus = StatusEntidadeEnum.values();
	//private static final String caminhoFisicoImagemDocumento = "C:/zend/Apache2/htdocs/DOCUMENTOS_sdni";
	private static final String caminhoFisicoImagemDocumento = "C:/xampp/htdocs/upload/sdni";
	// redireciona para o caminho abasoluto da imagem
	//private static final String linkSemNomeDaImagem = "http://localhost:8080/sdni/documento";
	private static final String linkSemNomeDaImagem = "http://192.168.5.124:8090/sdni/documento";
	
	public MBCadastroDocumento() {
		limparFormulario();
		System.out.println("Instancia........................");
	}


	public void inicializar() {
		System.out.println("Inicializa mbCadastroDocumento..........");

		if (FacesUtil.isNotPostBack()) {
			consultarGrupoDocumento();
		}
	}

	
	public void consultarGrupoDocumento() {
		
			listaGrupoDocumento = serviceGrupoDocumento.consultarGruposAtivosOrdemAlfabetica();

	}

	
	/** SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException */
	public String salvar() {
		try {
			documento.setDtaCadastro(ConversorData.converteDateParaCalendar(new Date()));

			// VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O documento.getId()
			if ((documento instanceof Object)) {
				// NAO HOUVE O UPLOAD
				if ( !(uploadFile instanceof Object) || uploadFile.getSize()<=0 ) {
					
					// MODO EDITRAR - GRAVA AS INFORMAÇÕES
					if (documento.getId() != null && documento.getId() > 0) {
						
						
						documento = serviceDocumento.salvarOuAtualizar(documento);
						limparFormulario();
						FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
						 
						  FacesContext.getCurrentInstance()
						      .getExternalContext()
						      .getFlash().setKeepMessages(true);				  
						
					}

					// MODO NOVO - NAO GRAVA AS INFORMAÇÕES POIS EXIGE UMA IMAGEM
					else {
						FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
								"Escolha um documento válido, para salvar");
						return "";
					}
				}

				// HOUVE UPLOAD - FUNCIONA NO MODO NOVO OU EDITAR
				else {
					gravarImagemNoDisco();
					// Salva a imagem no banco
					documento = serviceDocumento.salvarOuAtualizar(documento);
					limparFormulario();
					
					FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
					 
					  FacesContext.getCurrentInstance()
					      .getExternalContext()
					      .getFlash().setKeepMessages(true);				
				}				
			}

		} catch (campoObrigatorioNaoPreenchido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoJaExistenteException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoTransienteSendoPersistido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		}
		
		  return "consulta-documento.xhtml?faces-redirect=true";
	}

		
	

	public void gravarImagemNoDisco() {

		try {
			
			// PEGA AS INFORMAÇÕES DO UPLOAD CARREGADO
			UploadedFile uploadFile = this.uploadFile;
			
			// GERA UM CAMINHO FISICO DE ACORDO COM AS INFORMAÇÕES PASSADAS
			Path caminhoCompletoImagemComNomeArquivo = gerarCaminhoFisicoComNomeDoArquivo(
					caminhoFisicoImagemDocumento, uploadFile);

			// grava a imagem no disco fisico
			gravarArquivoNoDisco(uploadFile.getContents(),
					caminhoCompletoImagemComNomeArquivo.toString());

		} catch (IOException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).fatal(
					"FABRICA DE LOG - " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
					"Erro ao tentar gravar imagem, favor contate o administrador");
		}
	}


	


	/** CONCATENA CAMINHO ABSOLUTO E NOVO NOME DA IMAGEM SALVA
	 * @param caminhoAbsoImagemDocumento: recebe String neste formato ex: :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemDocumento,
			UploadedFile uploadFile) throws IOException {
		
		//inputStr = uploadFile.getInputstream();
		String nomeDocOriginal = uploadFile.getFileName();
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemDocumento);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile.getFileName());
			
		
		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		
		documento.setNomeDocOriginal(nomeDocOriginal);
		documento.setNomedoc(novoNomeUpload);		
		documento.setUridoc(linkSemNomeDaImagem + "/" + novoNomeUpload);

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
	

	private void limparFormulario() {
		System.out.println("Limpa o Evento....");
		documento = new Documento();
	}


	/** MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return */
	public StatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public Documento getDocumento() {		
		
		if (documento == null) {
			documento = new Documento();
		}
		return documento;
	}


	public void setDocumento(Documento documento) {
		
		this.documento = documento;
			
	}


	/** OBTERM O NOME DO ARQUIVO
	 * @return STRING com o nome do caminho
	 * @throws IOException */
	public String getExibirNomeArquivo() {
		this.exibirNomeArquivo = "";
		if (uploadFile != null) {
			this.exibirNomeArquivo = uploadFile.getFileName().concat(" -  ")
					.concat(String.valueOf(uploadFile.getSize())).concat(" Bytes");
		}

		return this.exibirNomeArquivo;
	}
	
	
	public UploadedFile getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(UploadedFile uploadFile) {
		
		this.uploadFile = uploadFile;
	}


	public List<GrupoDocumento> getListaGrupoDocumento() {
		return listaGrupoDocumento;
	}	
	

}
