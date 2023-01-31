package com.br.painelmobile.controle.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;

import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.negocios.servico.ServiceImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbImagem")
@ViewScoped
public class MBImagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Imagem imagem;
	private GaleriaImagem galeriaImagem;

	@Inject
	private MBGerImagem mbGerimagem;
	@Inject
	private ServiceImagem serviceImagem;

	private List<Imagem> listaImagem = new ArrayList<Imagem>();
	private static final String caminhoFisicoImagemEvento = "C:/Program Files/PainelMobileImagem/imagemDaGaleria";
	private static final String caminhoFisicoImagemImagem = "C:/Program Files/PainelMobileImagem/imagemDaGaleria";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://www.sesc-am.com.br:8090/painelmobile/webservice/imagem/imagemDaGaleria";


	public MBImagem() {
		limparFormulario();
	}


	public void inicializar() {
		if (galeriaImagem != null) {
			listarImagensDaGaleria();
		}

		if (FacesUtil.isNotPostBack()) {
			// Limpa a imagem da tela. Não tirar este comando daqui....
			limparMBGerImagem();

		}
	}


	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public void salvar() {
		try {

			// VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O
			// imagem.getId()
			if ((imagem instanceof Object)) {
				// NAO HOUVE O UPLOAD
				if (!(mbGerimagem.getUploadFile() instanceof Object)) {

					// MODO EDITRAR - GRAVA AS INFORMAÇÕES
					if (imagem.getId() != null && imagem.getId() > 0) {
						imagem = serviceImagem.salvarOuAtualizar(imagem);
						limparFormulario();
						limparMBGerImagem();
						FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
					}

					// MODO NOVO - NAO GRAVA AS INFORMAÇÕES POIS EXIGE UMA
					// IMAGEM
					else {
						FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
								"Escolha uma imagem válida, para salvar");
					}
				}

				// HOUVE UPLOAD - FUNCIONA NO MODO NOVO OU EDITAR
				else {
					gravarImagemNoDisco();
					imagem.setGaleria(galeriaImagem);

					// Salva a imagem no banco
					imagem = serviceImagem.salvarOuAtualizar(imagem);
					limparFormulario();
					limparMBGerImagem();
					FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
				}
			}

		} catch (campoObrigatorioNaoPreenchido e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).warning(
					"FABRICA DE LOG -: " + e.getMessage());
		} catch (ObjetoNaoEncontradoException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).warning(
					"FABRICA DE LOG -: " + e.getMessage());
		} catch (ObjetoJaExistenteException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).warning(
					"FABRICA DE LOG -: " + e.getMessage());
		} catch (ObjetoTransienteSendoPersistido e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).warning(
					"FABRICA DE LOG -: " + e.getMessage());
		}
	}


	public void gravarImagemNoDisco() {

		try {
			// PEGA AS INFORMAÇÕES DO UPLOAD CARREGADO
			UploadedFile uploadFile = mbGerimagem.getUploadFile();

			// GERA UM CAMINHO FISICO DE ACORDO COM AS INFORMAÇÕES PASSADAS
			Path caminhoCompletoImagemComNomeArquivo = gerarCaminhoFisicoComNomeDoArquivo(
					caminhoFisicoImagemImagem, uploadFile);

			// grava a imagem no disco fisico
			mbGerimagem.gravarArquivoNoDisco(uploadFile.getContents(),
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
	 * @param caminhoAbsoImagemImagem: recebe String neste formato ex:
	 * :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException
	 */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemImagem,
			UploadedFile uploadFile) throws IOException {
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemImagem);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile.getFileName());

		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		imagem.setNomeImgOriginal(uploadFile.getFileName());
		imagem.setNomeImagem(novoNomeUpload);
		imagem.setUriImagem(linkSemNomeDaImagem + "/" + novoNomeUpload);

		return caminhoComNomeArquivo;
	}


	/** LIMPA O BEAN GER IMAGEM PREPARANDO-O PARA O PROXIMO */
	private void limparMBGerImagem() {
		// PARAMETRIZA GER IMAGEM PARA UM NOVO REGISTRO
		mbGerimagem.parametrizarGerenciador(0, caminhoFisicoImagemImagem, "", null);
	}


	private void limparFormulario() {
		imagem = new Imagem();
	}


	/*
	 * ########################################################################
	 * #####################################################################
	 * #######################################################################
	 * METODOS DA VISUALIZACAO DA GALERIA
	 */

	
	
	/**
	 * LISTA AS IMAGENS NA GALERIA
	 */
	private void listarImagensDaGaleria() {

		try {
			setListaImagem(serviceImagem.consultarImagensPorGaleria(galeriaImagem.getId()));

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
					"Não há imagens para esta galeria");
		}

	}


	public void excluirImagem(ActionEvent event) {
		try {

			Integer idImagem = Integer.parseInt(FacesUtil.getRequestParameter("idImagem"));
			String nomeImagem = FacesUtil.getRequestParameter("nomeImagem");

			// exclui imagem do banco
			serviceImagem.excluirPorId(idImagem);
			// exclui imagem do disco
			deletarArquivo(caminhoFisicoImagemEvento + "/" + nomeImagem);

			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nome da imagem excluída: " + caminhoFisicoImagemEvento + "/" + nomeImagem);

			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "excluir", "");

		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			e.printStackTrace();
		} catch (EntityIdNuloException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * DELETA ARQUIVO DO DISCO FISICO
	 * @param caminhoAbsolutoDaImagem
	 */
	public void deletarArquivo(String caminhoAbsolutoComNomeArquivo) {
		File file = new File(caminhoAbsolutoComNomeArquivo);
		file.delete();
	}


	public GaleriaImagem getGaleriaImagem() {
		return galeriaImagem;
	}


	public void setGaleriaImagem(GaleriaImagem galeriaImagem) {
		this.galeriaImagem = galeriaImagem;
	}


	public Imagem getImagem() {
		return imagem;
	}


	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}


	/**
	 * utilizado para carregar a galeria de imagens
	 * @return
	 */
	public List<Imagem> getListaImagem() {
		return listaImagem;
	}


	public void setListaImagem(List<Imagem> listaImagem) {
		this.listaImagem = listaImagem;
	}

}
