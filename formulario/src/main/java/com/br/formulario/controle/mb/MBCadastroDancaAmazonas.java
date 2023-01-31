package com.br.formulario.controle.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.negocios.servico.ServiceDancaAmazonas;
import com.br.formulario.modelo.negocios.servico.ServiceModalidadeDancaAmazonas;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.DTO.DTODadosInscricao;
import com.br.formulario.modelo.persistencia.entidade.enums.SiglasEstados;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DancaAmazonas;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.Modalidade;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.manipularDados.ConversorData;

@Named("mbCadastroDancaAmazonas")
@ViewScoped
public class MBCadastroDancaAmazonas implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private static final String inicioInscricao = "05/05/2017";
	
	@Inject
	private ServiceDancaAmazonas serviceDancaAmazonas;
	
	@Inject
	private ServiceModalidadeDancaAmazonas serviceModalidadeDancaAmazonas;
	
	@Inject
	private MBEnvioConfirmacaoInscricao emailConfirmacaoInscricao;
	
	
	private DancaAmazonas dancaAmazonas;
	private Modalidade modalidade;
	private List<DTODadosInscricao> listaDTODadosInscricao;
	private StatusEntidadeEnum[] listaStatus = StatusEntidadeEnum.values();
	private SiglasEstados[] siglaEstado = SiglasEstados.values();
	private FiltroPesquisaPadrao filtro = new FiltroPesquisaPadrao();

	


	public MBCadastroDancaAmazonas() {
		limparFormulario();		
	}


	public void inicializar() {	

		if (FacesUtil.isNotPostBack()) {
			System.out.println("Não é postBack");
			consultarDadosInscricao();
			siglaEstado = SiglasEstados.values();
			
			modalidade = (Modalidade) FacesUtil.getFlash("modalidade");
			
			dancaAmazonas.setModalidade(modalidade);
		}
	}


	//CONSULTA OS DADOS DE INSCRIÇÃO DA TELA INICIAL
	public void consultarDadosInscricao() {
		try {
			// utilizado como filtro para pegar todas as categorias
			filtro.setTitulo("");
			filtro.setDtaInicioInscricao(inicioInscricao);
			listaDTODadosInscricao = serviceModalidadeDancaAmazonas.consultarDadosInscricao(filtro);		
			
		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "É necessário uma modalidade");
		}
	}


	

	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS @throws IOException
	 */
	public String salvar() {
		
		
		try {

			if (dancaAmazonas.getAceiteRegulamento() == false) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_WARN,
						"Marque a opção Aceitar Regulamento para concluir o cadastro");
				return null;
			}
			
			else {
				dancaAmazonas.setDtaInscricao(ConversorData.converteDateParaCalendar(new Date()));
				dancaAmazonas.setStatus(StatusEntidadeEnum.ATIVO);
				dancaAmazonas = serviceDancaAmazonas.salvarOuAtualizar(dancaAmazonas);
				
				FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso");
				emailConfirmacaoInscricao.enviarPedido(dancaAmazonas);
				limparFormulario();

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
		
		return "inscricao-confirmada?faces-redirect=true";

	}


	
	private void limparFormulario() {
		dancaAmazonas = new DancaAmazonas();
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


	public DancaAmazonas getDancaAmazonas() {
		if (dancaAmazonas == null) {
			dancaAmazonas = new DancaAmazonas();
		}

		return dancaAmazonas;
	}


	public void setDancaAmazonas(DancaAmazonas dancaAmazonas) {
		this.dancaAmazonas = dancaAmazonas;
	}

	

	public Modalidade getModalidade() {
		return modalidade;
	}


	public void setModalidade(Modalidade modalidade) {
		// Armazena a informação em um memoria paralela ao ViewScoped
		FacesUtil.setFlash("modalidade", modalidade);
		this.modalidade = modalidade;
	}


	public String getInicioInscricao() {
		return inicioInscricao;
	}


	public List<DTODadosInscricao> getListaDTODadosInscricao() {
		return listaDTODadosInscricao;
	}

}
