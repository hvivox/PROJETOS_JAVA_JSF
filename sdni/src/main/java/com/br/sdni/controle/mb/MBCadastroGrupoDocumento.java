package com.br.sdni.controle.mb;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.LogFactory;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.managerbean.diversos.FacesUtil;

@Named("mbCadastroGrupoDocumento")
@ViewScoped
public class MBCadastroGrupoDocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceGrupoDocumento serviceGrupoDocumento;	
	private GrupoDocumento grupoDocumento;
	
	private StatusEntidadeEnum[] listaEstatus = StatusEntidadeEnum.values();


	public MBCadastroGrupoDocumento() {
		limparFormulario();
		System.out.println("Instancia........................");
	}


	public void inicializar() {
		System.out.println("Inicializa mbCadastroGrupoDocumemto..........");
	}


	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public String salvar() {
		try {

			grupoDocumento = serviceGrupoDocumento.salvarOuAtualizar(grupoDocumento);
			limparFormulario();

			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			
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

		return "consulta-grupodocumento.xhtml?faces-redirect=true";
	}

	
	private void limparFormulario() {		
		grupoDocumento = new GrupoDocumento();
	}


	/**
	 * MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return
	 */
	public StatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public GrupoDocumento getGrupoDocumento() {
		if (grupoDocumento == null) {
			grupoDocumento = new GrupoDocumento();
		}

		return grupoDocumento;
	}


	public void setGrupoDocumento(GrupoDocumento grupoDocumento) {
		this.grupoDocumento = grupoDocumento;
	}



}
