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
import com.br.painelmobile.modelo.negocios.servico.ServiceCategoriaServico;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaCategoriaServico")
@ViewScoped
public class MBConsultaCategoriaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceCategoriaServico servicoCategoriaServico;
	private CategoriaServico categoriaServico;
	private List<CategoriaServico> listaDeCategoriaServicos;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaCategoriaServico() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaDeCategoriaServicos = new ArrayList<CategoriaServico>();
			categoriaServico = new CategoriaServico();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaDeCategoriaServicos != null) {
				listaDeCategoriaServicos.removeAll(listaDeCategoriaServicos);
				listaDeCategoriaServicos = servicoCategoriaServico.consultarPorFiltro(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	/** Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO: MOSTRA ATIVOS - FALSE
	 * MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do categoriaServico disparado ao clicar o botão */
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
	public List<CategoriaServico> getListaDeCategoriaServicos() {
		return listaDeCategoriaServicos;
	}


	/** utilizado para bindar com os campos titulo e checkbox e fazer um filtro e pesquisa
	 * baseando-se nos dados contidos nele
	 * @return */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}


	public void setCategoriaServico(CategoriaServico categoriaServico) {
		this.categoriaServico = categoriaServico;
	}


	

}
