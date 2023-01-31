package com.br.painelmobile.controle.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.negocios.servico.ServiceEvento;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;

@Named("mbConsultaEvento")
@ViewScoped
public class MBConsultaEvento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceEvento servicoEvento;
	private Evento evento;
	private List<Evento> listaDeEventos;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaEvento() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaDeEventos = new ArrayList<Evento>();
			evento = new Evento();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaDeEventos != null) {
				listaDeEventos.removeAll(listaDeEventos);
				listaDeEventos = servicoEvento.consultarPorFiltro(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	/** Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO: MOSTRA ATIVOS - FALSE
	 * MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do evento disparado ao clicar o botão */
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
	public List<Evento> getListaDeEventos() {
		return listaDeEventos;
	}


	/** utilizado para bindar com os campos titulo e checkbox e fazer um filtro e pesquisa
	 * baseando-se nos dados contidos nele
	 * @return */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public Evento getEvento() {
		return evento;
	}


	public void setEvento(Evento evento) {
		this.evento = evento;
	}


	/** METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA ENTIDADE */
	public void inativarCadastro() {
		evento.setEstatus(EstatusEntidadeEnum.INATIVO);
		alterar();
	}


	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do evento para inativo
			evento = servicoEvento.salvarOuAtualizar(evento);
			evento = new Evento();// retirar estas linhas futuramente

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

}
