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
import com.br.sdni.modelo.negocios.servico.ServiceUsuario;
import com.br.sdni.modelo.persistencia.dao.filter.UsuarioFilter;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.sdni.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaUsuario")
@ViewScoped
public class MBConsultaUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ServiceUsuario servicoUsuario;	
	private UsuarioFilter filtro;
	private LazyDataModel<Usuario> dataModelUsuario;
	
	private Usuario usuarioSelecionado;
	
	public MBConsultaUsuario() {
		filtro = new UsuarioFilter();
		
		//TREIXO UTLIZADO PARA IMPLEMENTAR O MÉTODO QUE CARREGA O DATATABLE E O POPULA
		dataModelUsuario = new LazyDataModel<Usuario>() {
			private static final long serialVersionUID = 1L;					
			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				setRowCount(servicoUsuario.totalFiltrados(filtro));				
					
				return servicoUsuario.pesquisaPorFiltro(filtro);
			}
			
		};
		
	}
	
	
	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {			
			usuarioSelecionado = new Usuario();
	
		}
	}

	
	
	
	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do grupoDocumento para inativo
			usuarioSelecionado = servicoUsuario.salvarOuAtualizar(usuarioSelecionado);
			usuarioSelecionado = new Usuario();// retirar estas linhas futuramente

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
	
	
	/**
	 * METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA
	 * ENTIDADE
	 */
	public void inativarCadastro() {
		usuarioSelecionado.setEstatus(StatusEntidadeEnum.INATIVO);
		alterar();
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

	

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}



	public LazyDataModel<Usuario> getDataModelUsuario() {
		return dataModelUsuario;
	}
	
	
	
	
	
}