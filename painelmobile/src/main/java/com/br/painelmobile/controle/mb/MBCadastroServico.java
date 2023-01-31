package com.br.painelmobile.controle.mb;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaServico;
import com.br.painelmobile.modelo.negocios.servico.ServiceServico;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Servico;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbCadastroServico")
@ViewScoped
public class MBCadastroServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBGerImagem mbGerimagem;

	@Inject
	private ServiceServico serviceServico;

	@Inject
	private ServiceCategoriaServico serviceCategoriaServico;

	private Servico servico;
	private List<CategoriaServico> listaCategoria;
	private EstatusEntidadeEnum[] listaEstatus = EstatusEntidadeEnum.values();
	private static final String caminhoFisicoImagemServico = "C:/Program Files/PainelMobileImagem/servico";
	// redireciona para o caminho abasoluto da imagem
	private static final String linkSemNomeDaImagem = "http://www.sesc-am.com.br:8090/painelmobile/webservice/imagem/servico";
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();


	public MBCadastroServico() {
		limparFormulario();
		System.out.println("Instancia........................");
	}


	public void inicializar() {
		System.out.println("Inicializa mbCADASTROEVENTO..........");

		if (FacesUtil.isNotPostBack()) {
			limparImagem();
			consultarCategorias();

			// SE ESTIVER EM MODO DE EDIÇÃO IRÁ ENTRAR AQUI
			if ((servico instanceof Object) && servico != null) {

				System.out.println("ENVIOU!! ###############################");

				// CONFIGURA O GER IMAGEM PARA RECEBER EXIBIR UMA IMAGEM DO DISCO
				mbGerimagem.parametrizarGerenciador(servico.getId(), caminhoFisicoImagemServico,
						servico.getNomeImagem(), null);
			}
		}
	}


	public void consultarCategorias() {
		try {
			// utilizado como filtro para pegar todas as categorias
			filtro.setTitulo("");
			listaCategoria = serviceCategoriaServico.consultarPorFiltro(filtro);

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "É necessário uma categoria");
		}
	}


	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public void salvar() {
		try {

			// VERIFICAR SE HÁ UMA INSTANCIA VALIDA PARA VALIDAR O
			// servico.getId()
			if ((servico instanceof Object)) {
				// NAO HOUVE O UPLOAD
				if (!(mbGerimagem.getUploadFile() instanceof Object)) {

					// MODO EDITRAR - GRAVA AS INFORMAÇÕES
					if (servico.getId() != null && servico.getId() > 0) {
						servico = serviceServico.salvarOuAtualizar(servico);
						limparFormulario();
						limparImagem();
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
					// Salva a imagem no banco
					servico = serviceServico.salvarOuAtualizar(servico);
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
					caminhoFisicoImagemServico, uploadFile);

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
	 * @param caminhoAbsoImagemServico: recebe String neste formato ex:
	 * :C:\diretorio\
	 * @return Path nesse formato ex:C:\diretorio\imagem0239223.jpg
	 * @throws IOException
	 */
	public Path gerarCaminhoFisicoComNomeDoArquivo(String caminhoAbsoImagemServico,
			UploadedFile uploadFile) throws IOException {
		Path caminhoAbsoluto = Paths.get(caminhoFisicoImagemServico);
		String nomeArquivo = FilenameUtils.getBaseName(uploadFile.getFileName());
		String extensao = FilenameUtils.getExtension(uploadFile.getFileName());

		Path caminhoComNomeArquivo = Files.createTempFile(caminhoAbsoluto, nomeArquivo + "-", "."
				+ extensao);
		String novoNomeUpload = caminhoComNomeArquivo.getFileName().toString();

		// Seta informações sobre a imagem que está sendo gravada
		// estas informações serão salvas no banco de dados
		servico.setNomeImgOriginal(uploadFile.getFileName());
		servico.setNomeImagem(novoNomeUpload);
		servico.setUriImagem(linkSemNomeDaImagem + "/" + novoNomeUpload);

		return caminhoComNomeArquivo;
	}


	/** LIMPA O BEAN GER IMAGEM PREPARANDO-O PARA O PROXIMO */
	private void limparImagem() {
		// PARAMETRIZA GER IMAGEM PARA UM NOVO REGISTRO
		mbGerimagem.parametrizarGerenciador(0, caminhoFisicoImagemServico, "", null);
	}


	private void limparFormulario() {
		servico = new Servico();
	}


	/**
	 * MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return
	 */
	public EstatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public Servico getServico() {
		if (servico == null) {
			servico = new Servico();
		}

		return servico;
	}


	public void setServico(Servico servico) {
		this.servico = servico;
	}


	/**
	 * Listar as categorias no combo
	 * @return
	 */
	public List<CategoriaServico> getListaCategoria() {
		return listaCategoria;
	}

}
