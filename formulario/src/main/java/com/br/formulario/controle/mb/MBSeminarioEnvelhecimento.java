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
import com.br.formulario.modelo.negocios.servico.ServiceSeminarioEnvelhecimento;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.SeminarioEnvelhecimento;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.manipularDados.ConversorData;


@Named("mbSeminarioEnvelhecimento")
@ViewScoped
public class MBSeminarioEnvelhecimento implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String inicioInscricao = "30/09/2018";
	private Integer qtdCadastrados;
	
	
	@Inject
	private ServiceSeminarioEnvelhecimento serviceSeminarioEnvelhecimento;

	@Inject
	private MBEnvioConfirmacaoInscricao emailConfirmacaoInscricao;

	private SeminarioEnvelhecimento seminarioEnvelhecimento;
	private List<SeminarioEnvelhecimento> listaDadosInscricaoSeminarioEnvelhecimento;
	private StatusEntidadeEnum[] listaStatus = StatusEntidadeEnum.values();
	private SiglasEstados[] siglaEstado = SiglasEstados.values();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();

	public MBSeminarioEnvelhecimento() {
		limparFormulario();
	}

	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			siglaEstado = SiglasEstados.values();
			qtdCadastrados = totalInscritos();
		}
	}

	
	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * 
	 * @throws IOException
	 */
	public String salvar() {
		try {

			seminarioEnvelhecimento.setDtaInscricao(ConversorData.converteDateParaCalendar(new Date()));
			seminarioEnvelhecimento.setStatus(StatusEntidadeEnum.ATIVO);

			seminarioEnvelhecimento = serviceSeminarioEnvelhecimento.salvarOuAtualizar(seminarioEnvelhecimento);
			//emailConfirmacaoInscricao.enviarEmailSeminarioEnvelhecimento(seminarioEnvelhecimento);
			
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

	
	public int totalInscritos() {
		try {
			return  serviceSeminarioEnvelhecimento.totalCadastrados();
		} catch (ObjetoNaoEncontradoException e) {
			return 221;
		}
		
	}
	
	
	private void limparFormulario() {
		seminarioEnvelhecimento = new SeminarioEnvelhecimento();
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

	public SeminarioEnvelhecimento getSeminarioEnvelhecimento() {
		if (seminarioEnvelhecimento == null) {
			seminarioEnvelhecimento = new SeminarioEnvelhecimento();
		}

		return seminarioEnvelhecimento;
	}

	public void setSeminarioEnvelhecimento(SeminarioEnvelhecimento seminarioEnvelhecimento) {
		this.seminarioEnvelhecimento = seminarioEnvelhecimento;
	}

	public String getInicioInscricao() {
		return inicioInscricao;
	}

	
	
	public Integer getQtdCadastrados() {
		return qtdCadastrados;
	}

	public void setQtdCadastrados(Integer qtdCadastrados) {		
		this.qtdCadastrados = qtdCadastrados;
	}

	
	
	
	
	
	
	
	
}
