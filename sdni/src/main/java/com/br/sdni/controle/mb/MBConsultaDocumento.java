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
import com.br.sdni.modelo.negocios.servico.ServiceDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaDocumento")
@ViewScoped
public class MBConsultaDocumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceDocumento servicoDocumento;
	private LazyDataModel<Documento> dataModelDocumento;
	private Documento documento;
	//private List<Documento> listaDeDocumentos;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaDocumento() {
		filtro = new FiltroPesquisaPadrao();
		
		
		//TREIXO UTLIZADO PARA IMPLEMENTAR O MÉTODO QUE CARREGA O DATATABLE E O POPULA
				dataModelDocumento = new LazyDataModel<Documento>() {
					private static final long serialVersionUID = 1L;					
					@Override
					public List<Documento> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
							Map<String, Object> filters) {
						
						filtro.setPrimeiroRegistro(first);
						filtro.setQuantidadeRegistros(pageSize);
						filtro.setPropriedadeOrdenacao(sortField);
						filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
						
						setRowCount(servicoDocumento.totalFiltrados(filtro));				
							
						return servicoDocumento.pesquisaPorFiltro(filtro);
					}
					
				};
		
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {			
			documento = new Documento();
	
		}
	}

	



	/**
	 * Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO:
	 * MOSTRA ATIVOS - FALSE MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do documento disparado ao clicar o botão
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


	public Documento getDocumento() {
		return documento;
	}


	public void setDocumento(Documento documento) {
		this.documento = documento;
	}


	/**
	 * METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA
	 * ENTIDADE
	 */
	public void inativarCadastro() {
		documento.setStatus(StatusEntidadeEnum.INATIVO);
		alterar();
	}

	
	
	public LazyDataModel<Documento> getDataModelDocumento() {
		return dataModelDocumento;
	}


	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do documento para inativo
			documento = servicoDocumento.salvarOuAtualizar(documento);
			documento = new Documento();// retirar estas linhas futuramente


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

}
