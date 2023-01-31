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
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbCadastroCategoriaParceiro")
@ViewScoped
public class MBCadastroCategoriaParceiro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MBGerImagem mbGerimagem;
	@Inject
	private ServiceCategoriaParceiro serviceCategoriaParceiro;
	private CategoriaParceiro categoriaParceiro;
	private EstatusEntidadeEnum[] listaEstatus = EstatusEntidadeEnum.values();
	private static final String caminhoFisicoImagemCategoriaParceiro = "C:/Program Files/PainelMobileImagem/categoriaParceiro";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://www.sesc-am.com.br:8090/painelmobile/webservice/imagem/categoriaParceiro";


	public MBCadastroCategoriaParceiro() {
		limparFormulario();
		System.out.println("Instancia........................");
	}


	public void inicializar() {
		System.out.println("Inicializa mbCADASTROEVENTO..........");

		if (FacesUtil.isNotPostBack()) {
			// Limpa a imagem da tela. Não tirar este comando daqui....
			limparImagem();

			// SE ESTIVER EM MODO DE EDIÇÃO IRÁ ENTRAR AQUI
			if ((categoriaParceiro instanceof Object) && categoriaParceiro != null) {
				
				System.out.println("ENVIOU!! ###############################");

				// CONFIGURA O GER IMAGEM PARA RECEBER EXIBIR UMA IMAGEM DO DISCO
				mbGerimagem.parametrizarGerenciador(categoriaParceiro.getId(),
						caminhoFisicoImagemCategoriaParceiro, categoriaParceiro.getNomeImagem(), null);
			}
		}
	}


	/** SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException */
	public void salvar() {
		try {
			
			//VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O categoriaParceiro.getId()
			if ((categoriaParceiro instanceof Object)) {
				// NAO HOUVE O UPLOAD
				if (!(mbGerimagem.getUploadFile() instanceof Object)) {
					
					// MODO EDITRAR - GRAVA AS INFORMAÇÕES
					if (categoriaParceiro.getId() != null && categoriaParceiro.getId() > 0) {
						categoriaParceiro = serviceCategoriaParceiro.salvarOuAtualizar(categoriaParceiro);
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
						// Salva a imagem no banco
						categoriaParceiro = serviceCategoriaParceiro.salvarOuAtualizar(categoriaParceiro);
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
			//PEGA AS INFORMAÇÕES DO UPLOAD CARREGADO
			UploadedFile uploadFile = mbGerimagem.getUploadFile();

			//GERA UM CAMINHO FISICO DE ACORDO COM AS INFORMAÇÕES PASSADAS
			Path caminhoCompletoImagemComNomeArquivo = gerarCaminhoFisicoComNomeDoArquivo(
			caminhoFisicoImagemCategoriaParceiro, uploadFile);
			
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
	 * @param caminhoAbsoImagemCategoriaParceiro: recebe String neste formato ex: :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemCategoriaParceiro,
			UploadedFile uploadFile) throws IOException {
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemCategoriaParceiro);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile.getFileName());

		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		categoriaParceiro.setNomeImgOriginal(uploadFile.getFileName());
		categoriaParceiro.setNomeImagem(novoNomeUpload);
		categoriaParceiro.setUriImagem(linkSemNomeDaImagem + "/" + novoNomeUpload);

		return caminhoComNomeArquivo;
	}


	/** LIMPA O BEAN GER IMAGEM PREPARANDO-O PARA O PROXIMO */
	private void limparImagem() {
		// PARAMETRIZA GER IMAGEM PARA UM NOVO REGISTRO
		mbGerimagem.parametrizarGerenciador(0, caminhoFisicoImagemCategoriaParceiro, "", null);
	}


	private void limparFormulario() {
		categoriaParceiro = new CategoriaParceiro();
	}


	/** MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return */
	public EstatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public CategoriaParceiro getCategoriaParceiro() {
		if (categoriaParceiro == null) {
			categoriaParceiro = new CategoriaParceiro();
		}

		return categoriaParceiro;
	}


	public void setCategoriaParceiro(CategoriaParceiro categoriaParceiro) {
		System.out.println("chama o SETcategoriaParceiro..................");
		this.categoriaParceiro = categoriaParceiro;
		/* if(categoriaParceiro != null){ this.listaEstatus = EstatusEntidadeEnum.values(); } */
	}

}
