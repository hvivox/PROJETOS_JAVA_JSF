package com.br.formulario.controle.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.logging.LogFactory;
import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.negocios.servico.ServicePromocaoIdoso;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PromocaoIdoso;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.manipularDados.ConversorData;

@Named("mbPromocaoIdoso")
@ViewScoped
public class MBPromocaoIdoso implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String inicioInscricao = "30/09/2018";

	@Inject
	private ServicePromocaoIdoso servicePromocaoIdoso;

	@Inject
	private MBEnvioConfirmacaoInscricao emailConfirmacaoInscricao;

	private PromocaoIdoso promocaoIdoso;
	private List<PromocaoIdoso> listaDadosInscricaoPromocaoIdoso;
	private StatusEntidadeEnum[] listaStatus = StatusEntidadeEnum.values();
	private SiglasEstados[] siglaEstado = SiglasEstados.values();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();

	public MBPromocaoIdoso() {
		limparFormulario();
	}

	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			siglaEstado = SiglasEstados.values();
		}
	}

	
	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * 
	 * @throws IOException
	 */
	public String salvar() {
		try {

			promocaoIdoso.setDtaInscricao(ConversorData.converteDateParaCalendar(new Date()));
			promocaoIdoso.setStatus(StatusEntidadeEnum.ATIVO);

			promocaoIdoso = servicePromocaoIdoso.salvarOuAtualizar(promocaoIdoso);
			//emailConfirmacaoInscricao.enviarEmailPromocaoIdoso(promocaoIdoso);
			
			limparFormulario();
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");

		} catch (campoObrigatorioNaoPreenchido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME)
					.warn("FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME)
					.warn("FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoJaExistenteException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME)
					.warn("FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		} catch (ObjetoTransienteSendoPersistido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME)
					.warn("FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
		}

		return "inscricao-confirmada?faces-redirect=true";
	}

	
	
	private void limparFormulario() {
		promocaoIdoso = new PromocaoIdoso();
	}

	/**
	 * MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * 
	 * @return
	 */
	public StatusEntidadeEnum[] getListaStatus() {
		return listaStatus;
	}

	public SiglasEstados[] getSiglaEstado() {
		return siglaEstado;
	}

	public PromocaoIdoso getPromocaoIdoso() {
		if (promocaoIdoso == null) {
			promocaoIdoso = new PromocaoIdoso();
		}

		return promocaoIdoso;
	}

	public void setPromocaoIdoso(PromocaoIdoso promocaoIdoso) {
		this.promocaoIdoso = promocaoIdoso;
	}

	public String getInicioInscricao() {
		return inicioInscricao;
	}

}
