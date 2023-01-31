package com.br.painelmobile.controle.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaCategoriaParceiro")
@ViewScoped
public class MBConsultaCategoriaParceiro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceCategoriaParceiro servicoCategoriaParceiro;
	private CategoriaParceiro categoriaParceiro;
	private List<CategoriaParceiro> listaDeCategoriaParceiros;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaCategoriaParceiro() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaDeCategoriaParceiros = new ArrayList<CategoriaParceiro>();
			categoriaParceiro = new CategoriaParceiro();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaDeCategoriaParceiros != null) {
				listaDeCategoriaParceiros.removeAll(listaDeCategoriaParceiros);
				listaDeCategoriaParceiros = servicoCategoriaParceiro.consultarPorFiltro(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	/** Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO: MOSTRA ATIVOS - FALSE
	 * MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do categoriaParceiro disparado ao clicar o botão */
	public void verificarCheckBox(ValueChangeEvent e) {
		
		if (e.getNewValue().equals(false)) {

			filtro.setMostraInitivos(false);
		}
		else if (e.getNewValue().equals(true)) {
			filtro.setMostraInitivos(true);
		}

	}


	/** LISTA UTILIZADA PELO DATATABLE PARA EXIBIR AS INFORMAÇÕES
	 * @return */
	public List<CategoriaParceiro> getListaDeCategoriaParceiros() {
		return listaDeCategoriaParceiros;
	}


	/** utilizado para bindar com os campos titulo e checkbox e fazer um filtro e pesquisa
	 * baseando-se nos dados contidos nele
	 * @return */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public CategoriaParceiro getCategoriaParceiro() {
		return categoriaParceiro;
	}


	public void setCategoriaParceiro(CategoriaParceiro categoriaParceiro) {
		this.categoriaParceiro = categoriaParceiro;
	}


	

}
