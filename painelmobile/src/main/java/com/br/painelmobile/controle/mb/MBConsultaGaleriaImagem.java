package com.br.painelmobile.controle.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.negocios.servico.ServiceGaleriaImagem;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaGaleriaImagem")
@ViewScoped
public class MBConsultaGaleriaImagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceGaleriaImagem servicoGaleriaImagem;
	private GaleriaImagem galeriaImagem;
	private List<Imagem> listaImagem;
	private List<GaleriaImagem> listaDeGaleriaImagems;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaGaleriaImagem() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaDeGaleriaImagems = new ArrayList<GaleriaImagem>();
			galeriaImagem = new GaleriaImagem();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaDeGaleriaImagems != null) {
				listaDeGaleriaImagems.removeAll(listaDeGaleriaImagems);
				listaDeGaleriaImagems = servicoGaleriaImagem.consultarPorFiltro(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	/** Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO: MOSTRA ATIVOS - FALSE
	 * MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do galeriaImagem disparado ao clicar o botão */
	public void verificarCheckBox(ValueChangeEvent e) {
		
		if (e.getNewValue().equals(false)) {

			filtro.setMostraInitivos(false);
		}
		else if (e.getNewValue().equals(true)) {
			filtro.setMostraInitivos(true);
		}

	}


	
	public void listarImagensDaGaleria(){
		try {
			galeriaImagem = servicoGaleriaImagem.consultarPorObjeto(galeriaImagem);
		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		} catch (EntityIdNuloException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}	
	
	
	
	/** LISTA UTILIZADA PELO DATATABLE PARA EXIBIR AS INFORMAÇÕES
	 * @return */
	public List<GaleriaImagem> getListaDeGaleriaImagems() {
		return listaDeGaleriaImagems;
	}


	/** utilizado para bindar com os campos titulo e checkbox e fazer um filtro e pesquisa
	 * baseando-se nos dados contidos nele
	 * @return */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public GaleriaImagem getGaleriaImagem() {
		return galeriaImagem;
	}


	public void setGaleriaImagem(GaleriaImagem galeriaImagem) {	
		this.galeriaImagem = galeriaImagem;
	}


	/** METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA ENTIDADE */
	public void inativarCadastro() {
		galeriaImagem.setEstatus(EstatusEntidadeEnum.INATIVO);
		alterar();
	}


	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do galeriaImagem para inativo
			galeriaImagem = servicoGaleriaImagem.salvarOuAtualizar(galeriaImagem);
			galeriaImagem = new GaleriaImagem();// retirar estas linhas futuramente

			// exibe os produtos filtrados
			pesquisarPorFiltro();

			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "alterar", "");

		} catch (campoObrigatorioNaoPreenchido e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());

		} catch (ObjetoJaExistenteException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());

		} catch (ObjetoTransienteSendoPersistido e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	public List<Imagem> getListaImagem() {
		return listaImagem;
	}


	
	
	
	

}
