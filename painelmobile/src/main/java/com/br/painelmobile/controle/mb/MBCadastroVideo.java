package com.br.painelmobile.controle.mb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.negocios.servico.ServiceVideo;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbCadastroVideo")
@ViewScoped
public class MBCadastroVideo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceVideo serviceVideo;
	private Video video;
	private EstatusEntidadeEnum[] listaEstatus = EstatusEntidadeEnum.values();


	public MBCadastroVideo() {
		limparFormulario();
		System.out.println("Instancia........................");
	}


	public void inicializar() {
		System.out.println("Inicializa mbCADASTROEVENTO..........");

		if (FacesUtil.isNotPostBack()) {					
			
		}
	}


	/**
	 * SALVA A INFORMAÇÃO NO BANCO DE DADOS
	 * @throws IOException
	 */
	public void salvar() {
		try {

			//video.setDtaInclusaoVideo(Calendar.getInstance());
			video = serviceVideo.salvarOuAtualizar(video);
			limparFormulario();
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");
			

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


	private void limparFormulario() {
		video = new Video();
		video.setDtaInclusaoVideo(Calendar.getInstance());
	}


	/**
	 * MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return
	 */
	public EstatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}


	public Video getVideo() {
		if (video == null) {
			video = new Video();
			video.setDtaInclusaoVideo(Calendar.getInstance());
		}

		return video;
	}


	public void setVideo(Video video) {
		System.out.println("chama o SETvideo..................");
		this.video = video;
		
	}
	
	
	

}
