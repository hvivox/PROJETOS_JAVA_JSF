package com.br.formulario.controle.mb;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.negocios.servico.ServiceAmazonasArtes;
import com.br.formulario.modelo.negocios.servico.ServiceModAmArtes;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.DTO.DTODadosInscricaoAmArtes;
import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.ModAmArtes;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.manipularDados.ConversorData;

@Named("mbAmazonasArtes")
@ViewScoped
public class MBAmazonasArtes implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String inicioInscricao = "19/05/2017";

	@Inject
	private ServiceAmazonasArtes serviceAmazonasArtes;

	@Inject
	private ServiceModAmArtes serviceModAmArtesAmazonasArtes;

	@Inject
	private MBEnvioConfirmacaoInscricao emailConfirmacaoInscricao;

	private AmazonasArtes amazonasArtes;
	private ModAmArtes modAmArtes;
	private List<DTODadosInscricaoAmArtes> listaDTODadosInscricaoAmArtes;
	private DTODadosInscricaoAmArtes dtoDadosInscricaoAmArtes;
	private StatusEntidadeEnum[] listaStatus = StatusEntidadeEnum.values();
	private SiglasEstados[] siglaEstado = SiglasEstados.values();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	private String exibirNomeArquivo;
	private static final String caminhoFisicoImagemDocumento = "C:/xampp/htdocs/portalinstitucional/wp-content/uploads/java-upload/formulario";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://www.sesc-am.com.br:8090/formulario/documento";
	private UploadedFile uploadFile;
	private String nomeUpload;
	private InputStream inputStr = null;

	public MBAmazonasArtes() {
		limparFormulario();
	}


	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			System.out.println("Não é postBack");
			consultarDadosInscricao();
			siglaEstado = SiglasEstados.values();

			modAmArtes = (ModAmArtes) FacesUtil.getFlash("modAmArtes");
			amazonasArtes.setModAmArtes(modAmArtes);
		}
	}


	// CONSULTA OS DADOS DE INSCRIÇÃO DA TELA INICIAL
	public void consultarDadosInscricao() {
		try {
			// utilizado como filtro para pegar todas as categorias
			filtro.setTitulo("");
			filtro.setDtaInicioInscricao(inicioInscricao);
			listaDTODadosInscricaoAmArtes = serviceModAmArtesAmazonasArtes
					.consultarDadosInscricao(filtro);

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "É necessário uma modAmArtes");
		}
	}


	public void autoUpload(FileUploadEvent event) {
		// this.event = event;
		//this.nomeUpload = event.getFile().getFileName();
		//this.uploadFile = event.getFile();
		
		try {
			this.nomeUpload = event.getFile().getFileName();
			this.uploadFile = event.getFile();
			inputStr = uploadFile.getInputstream();
		} catch (IOException e) {
			// log error
		}	
	}


	public String getNomeUpload() {
		return nomeUpload;
	}


	public void setNomeUpload(String nomeUpload) {
		this.nomeUpload = nomeUpload;
	}


	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public String salvar() {
		try {

			amazonasArtes.setDtaInscricao(ConversorData.converteDateParaCalendar(new Date()));
			amazonasArtes.setStatus(StatusEntidadeEnum.ATIVO);

			if (amazonasArtes.getAceiteRegulamento() == false) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
						"Marque a opção Aceitar Regulamento para concluir o cadastro");
				return null;
			}

			// NAO HOUVE O UPLOAD
			if (!(uploadFile instanceof Object) || uploadFile.getSize() <= 0) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
						"Encaminhe a ficha técnica para salvar e concluir a inscrição!");
				return "";
			}

			// HOUVE UPLOAD - FUNCIONA NO MODO NOVO OU EDITAR
			else {
				gravarImagemNoDisco();
				// Salva a imagem no banco
				amazonasArtes = serviceAmazonasArtes.salvarOuAtualizar(amazonasArtes);
				emailConfirmacaoInscricao.enviarPedidoAmazonasArtes(amazonasArtes);
				limparFormulario();
				FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
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

		return "inscricao-confirmada?faces-redirect=true";
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
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
					"Erro ao tentar gravar imagem, favor contate o administrador");
		}
	}


	/**
	 * CONCATENA CAMINHO ABSOLUTO E NOVO NOME DA IMAGEM SALVA
	 * @param caminhoAbsoImagemDocumento: recebe String neste formato ex:
	 * :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException
	 */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemDocumento,
			UploadedFile uploadFile) throws IOException {
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemDocumento);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile.getFileName());

		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		amazonasArtes.setNomeOriginalDoc(uploadFile.getFileName());
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


	/**
	 * OBTERM O NOME DO ARQUIVO
	 * @return STRING com o nome do caminho
	 * @throws IOException
	 */
	public String getExibirNomeArquivo() {
		this.exibirNomeArquivo = "";
		if (uploadFile != null) {
			this.exibirNomeArquivo = uploadFile.getFileName().concat(" -  ")
					.concat(String.valueOf(uploadFile.getSize())).concat(" Bytes");
		}

		return this.exibirNomeArquivo;
	}


	private void limparFormulario() {
		amazonasArtes = new AmazonasArtes();
	}


	/**
	 * MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return
	 */
	public StatusEntidadeEnum[] getListaStatus() {
		return listaStatus;
	}


	public SiglasEstados[] getSiglaEstado() {
		return siglaEstado;
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


	public ModAmArtes getModAmArtes() {
		return modAmArtes;
	}


	public void setModAmArtes(ModAmArtes modAmArtes) {
		// Armazena a informação em um memoria paralela ao ViewScoped
		FacesUtil.setFlash("modAmArtes", modAmArtes);
		this.modAmArtes = modAmArtes;
	}


	public String getInicioInscricao() {
		return inicioInscricao;
	}


	public List<DTODadosInscricaoAmArtes> getListaDTODadosInscricao() {
		return listaDTODadosInscricaoAmArtes;
	}


	public UploadedFile getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}



	//utilizado para evitar erros ao selecionar a linha e clicar no botão de inscrição
	public DTODadosInscricaoAmArtes getDtoDadosInscricaoAmArtes() {
		return dtoDadosInscricaoAmArtes;
	}
	public void setDtoDadosInscricaoAmArtes(DTODadosInscricaoAmArtes dtoDadosInscricaoAmArtes) {
		this.dtoDadosInscricaoAmArtes = dtoDadosInscricaoAmArtes;
	}

	
	
	
	
	
	
}
