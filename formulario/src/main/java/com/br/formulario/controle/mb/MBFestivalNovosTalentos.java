package com.br.formulario.controle.mb;

import java.io.IOException;
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
import com.br.formulario.modelo.negocios.servico.ServiceFestivalNovosTalentos;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.FestivalNovosTalentos;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.manipularDados.ConversorData;


@Named("mbFestivalNovosTalentos")
@ViewScoped
public class MBFestivalNovosTalentos implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String inicioInscricao = "16/10/2017";

	@Inject
	private ServiceFestivalNovosTalentos serviceFestivalNovosTalentos;

	
	@Inject
	private MBEnvioConfirmacaoInscricao emailConfirmacaoInscricao;

	private FestivalNovosTalentos festivalNovosTalentos;
	private List<FestivalNovosTalentos> listaDadosInscricaoNovosTalentos;
	
	private StatusEntidadeEnum[] listaStatus = StatusEntidadeEnum.values();
	private SiglasEstados[] siglaEstado = SiglasEstados.values();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();
	

	public MBFestivalNovosTalentos() {
		limparFormulario();
	}


	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			System.out.println("Não é postBack");
			
			siglaEstado = SiglasEstados.values();
		}
	}


	// CONSULTA OS DADOS DE INSCRIÇÃO DA TELA INICIAL
/*	public void consultarDadosInscricao() {
		try {
			// utilizado como filtro para pegar todas as categorias
			filtro.setTitulo("");
			filtro.setDtaInicioInscricao(inicioInscricao);
			listaDadosInscricaoNovosTalentos = serviceFestivalNovosTalentos.consultarPorFiltro(filtro);

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "É necessário uma escolher uma opção");
		}
	}
*/

	

	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public String salvar() {
		try {

			festivalNovosTalentos.setDtaInscricao(ConversorData.converteDateParaCalendar(new Date()));
			festivalNovosTalentos.setStatus(StatusEntidadeEnum.ATIVO);

			if (festivalNovosTalentos.getAceiteRegulamento() == false) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
						"Marque a opção Aceitar Regulamento para concluir o cadastro");
				return null;
			}
			
			else{
				festivalNovosTalentos = serviceFestivalNovosTalentos.salvarOuAtualizar(festivalNovosTalentos);
				emailConfirmacaoInscricao.enviarPedidoFestivalNovosTalentos(festivalNovosTalentos);
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

	
	private void limparFormulario() {
		festivalNovosTalentos = new FestivalNovosTalentos();
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


	public FestivalNovosTalentos getFestivalNovosTalentos() {
		if (festivalNovosTalentos == null) {
			festivalNovosTalentos = new FestivalNovosTalentos();
		}

		return festivalNovosTalentos;
	}


	public void setFestivalNovosTalentos(FestivalNovosTalentos festivalNovosTalentos) {
		this.festivalNovosTalentos = festivalNovosTalentos;
	}


	public String getInicioInscricao() {
		return inicioInscricao;
	}


	
}
