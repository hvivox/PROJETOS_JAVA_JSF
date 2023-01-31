package com.br.painelmobile.controle.mb;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.negocios.servico.ServiceImagem;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbCadastroImagem")
@ViewScoped
public class MBCadastroImagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBGerImagem mbGerimagem;
	@Inject
	private ServiceImagem serviceImagem;
	private Imagem imagem;
	private GaleriaImagem galeriaImagem;
	private EstatusEntidadeEnum[] listaEstatus = EstatusEntidadeEnum.values();
	private static final String caminhoFisicoImagemImagem = "C:/Program Files/PainelMobileImagem/imagemDaGaleria";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://www.sesc-am.com.br:8090/painelmobile/webservice/imagem/imagemDaGaleria";


	public MBCadastroImagem() {
		limparFormulario();
		
	}


	public void inicializar() {
		

		if (FacesUtil.isNotPostBack()) {
			// Limpa a imagem da tela. Não tirar este comando daqui....
			limparImagem();

			// SE ESTIVER EM MODO DE EDIÇÃO IRÁ ENTRAR AQUI
			if ((imagem instanceof Object) && imagem != null) {

				// CONFIGURA O GER IMAGEM PARA RECEBER EXIBIR UMA IMAGEM DO DISCO
				mbGerimagem.parametrizarGerenciador(imagem.getId(), caminhoFisicoImagemImagem,
						imagem.getNomeImagem(), null);
			}
		}
	}


	/** SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException */
	public void salvar() {
		try {

			// VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O imagem.getId()
			if ((imagem instanceof Object)) {
				// NAO HOUVE O UPLOAD
				if (!(mbGerimagem.getUploadFile() instanceof Object)) {

					// MODO EDITRAR - GRAVA AS INFORMAÇÕES
					if (imagem.getId() != null && imagem.getId() > 0) {
						imagem = serviceImagem.salvarOuAtualizar(imagem);
						limparFormulario();
						limparImagem();
						FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
					}

					// MODO NOVO - NAO GRAVA AS INFORMAÇÕES POIS EXIGE UMA IMAGEM
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
					limparImagem();
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


	/** CONCATENA CAMINHO ABSOLUTO E NOVO NOME DA IMAGEM SALVA
	 * @param caminhoAbsoImagemImagem: recebe String neste formato ex: :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException */
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
	private void limparImagem() {
		// PARAMETRIZA GER IMAGEM PARA UM NOVO REGISTRO
		mbGerimagem.parametrizarGerenciador(0, caminhoFisicoImagemImagem, "", null);
	}


	private void limparFormulario() {
		imagem = new Imagem();
	}


	/** MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return */
	public EstatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public Imagem getImagem() {
		if (imagem == null) {
			imagem = new Imagem();
		}

		return imagem;
	}


	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
		/* if(imagem != null){ this.listaEstatus = EstatusEntidadeEnum.values(); } */
	}


	//
	public GaleriaImagem getGaleriaImagem() {
		return galeriaImagem;
	}


	// SETA AS INFORMAÇÕES DA GALERIA SELECIONADA NA TABELA
	public void setGaleriaImagem(GaleriaImagem galeriaImagem) {
		this.galeriaImagem = galeriaImagem;
	}

}
