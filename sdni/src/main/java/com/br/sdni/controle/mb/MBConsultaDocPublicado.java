package com.br.sdni.controle.mb;

import java.awt.Desktop;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaDocPublicado")
@ViewScoped
public class MBConsultaDocPublicado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceGrupoDocumento serviceGrupoDocumento;
	private List<GrupoDocumento> listaEstruraDocumento = new ArrayList<GrupoDocumento>();

	private FiltroPesquisaPadrao filtroPesquisa;


	public MBConsultaDocPublicado() {
		filtroPesquisa = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaEstruraDocumento = pesquisarEstruturaDocumentos();

		}
	}


	public List<GrupoDocumento> pesquisarEstruturaDocumentos() {

		if (listaEstruraDocumento != null) {

			filtroPesquisa.setTitulo("");
			listaEstruraDocumento.removeAll(listaEstruraDocumento);

			listaEstruraDocumento = serviceGrupoDocumento.consultarGruposAtivosOrdemAlfabetica();
		}

		return listaEstruraDocumento;

	}


	/**
	 * Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO:
	 * MOSTRA ATIVOS - FALSE MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do documento disparado ao clicar o botão
	 */
	public void verificarCheckBox(ValueChangeEvent e) {

		if (e.getNewValue().equals(false)) {

			filtroPesquisa.setMostraInativos(false);
		} else if (e.getNewValue().equals(true)) {
			filtroPesquisa.setMostraInativos(true);
		}
	}


	/**
	 * utilizado para bindar com os campos titulo e checkbox e fazer um
	 * filtroPesquisa e pesquisa baseando-se nos dados contidos nele
	 * @return
	 */
	public FiltroPesquisaPadrao getFiltro() {
		return filtroPesquisa;
	}


	public List<GrupoDocumento> getListaEstruraDocumento() {
		return listaEstruraDocumento;
	}


	public void setListaEstruraDocumento(List<GrupoDocumento> listaEstruraDocumento) {
		this.listaEstruraDocumento = listaEstruraDocumento;
	}


	/**
	 * metodo para abrir janela no computador do servidor atavés do managerBean
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {

		Desktop desktop = null;
		desktop = Desktop.getDesktop();
		URI uri = null;
		try {
			uri = new URI(((Documento) event.getObject()).getUridoc());
			desktop.browse(uri);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
	}

}
