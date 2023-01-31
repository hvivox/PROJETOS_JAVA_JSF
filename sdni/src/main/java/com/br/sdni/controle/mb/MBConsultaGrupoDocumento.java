package com.br.sdni.controle.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;
import com.br.sdni.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaGrupoDocumento")
@ViewScoped
public class MBConsultaGrupoDocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceGrupoDocumento servicoGrupoDocumento;
	private GrupoDocumento grupoDocumento;
	private LazyDataModel<GrupoDocumento> dataModelGrupoDocumento;	
	private FiltroPesquisaPadrao filtro;


	public MBConsultaGrupoDocumento() {
		filtro = new FiltroPesquisaPadrao();
		
		//TREIXO UTLIZADO PARA IMPLEMENTAR O MÉTODO QUE CARREGA O DATATABLE E O POPULA
		dataModelGrupoDocumento = new LazyDataModel<GrupoDocumento>() {
			private static final long serialVersionUID = 1L;			
			@Override
			public List<GrupoDocumento> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				setRowCount(servicoGrupoDocumento.quantidadeFiltrados(filtro));				
					
				return servicoGrupoDocumento.filtrados(filtro);
			}
			
		};
	}


	public void inicializar() {
		
		if (FacesUtil.isNotPostBack()) {			
			grupoDocumento = new GrupoDocumento();
			
			
		}
		//dataModelGrupoDocumento = new LazyGrupoDocumentoDataModelNAO_UTILIZO(servicoGrupoDocumento, filtro);
	}

	


	/**
	 * Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO:
	 * MOSTRA ATIVOS - FALSE MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do grupoDocumento disparado ao clicar o botão
	 */
	public void verificarCheckBox(ValueChangeEvent e) {
		
		if (e.getNewValue().equals(false)) {
			filtro.setMostraInativos(false);
		} else if (e.getNewValue().equals(true)) {
			filtro.setMostraInativos(true);
		}

	}




	/**
	 * utilizado para bindar com os campos titulo e checkbox e fazer um filtro e
	 * pesquisa baseando-se nos dados contidos nele
	 * @return
	 */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public GrupoDocumento getGrupoDocumento() {
		return grupoDocumento;
	}


	public void setGrupoDocumento(GrupoDocumento grupoDocumento) {
		this.grupoDocumento = grupoDocumento;
	}


	/**
	 * METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA
	 * ENTIDADE
	 */
	public void inativarCadastro() {
		grupoDocumento.setStatus(StatusEntidadeEnum.INATIVO);
		alterar();
	}


	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do grupoDocumento para inativo
			grupoDocumento = servicoGrupoDocumento.salvarOuAtualizar(grupoDocumento);
			grupoDocumento = new GrupoDocumento();// retirar estas linhas futuramente

			// exibe os produtos filtrados
			//pesquisarPorFiltro();

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


	public LazyDataModel<GrupoDocumento> getDataModelGrupoDocumento() {
		return dataModelGrupoDocumento;
	}


	

	
	
	
	

}
