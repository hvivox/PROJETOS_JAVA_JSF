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
import com.br.painelmobile.modelo.negocios.servico.ServiceCardapio;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.util.managerbean.diversos.FacesUtil;



@Named("mbConsultaCardapio")
@ViewScoped
public class MBConsultaCardapio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceCardapio servicoCardapio;
	private Cardapio cardapio;
	private List<Cardapio> listaDeCardapios;
	private FiltroPesquisaPadrao filtro;


	public MBConsultaCardapio() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaDeCardapios = new ArrayList<Cardapio>();
			cardapio = new Cardapio();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaDeCardapios != null) {
				listaDeCardapios.removeAll(listaDeCardapios);
				listaDeCardapios = servicoCardapio.consultarPorFiltro(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}


	/** Verificar que o check Mostrar inativos foi marcado ou NÃO DESMARCADO: MOSTRA ATIVOS - FALSE
	 * MARCADO: MOSTRA INATIVOS - TRUE
	 * @param e: informações do cardapio disparado ao clicar o botão */
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
	public List<Cardapio> getListaDeCardapios() {
		return listaDeCardapios;
	}


	/** utilizado para bindar com os campos titulo e checkbox e fazer um filtro e pesquisa
	 * baseando-se nos dados contidos nele
	 * @return */
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}


	public Cardapio getCardapio() {
		return cardapio;
	}


	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}


	/** METODO UTILIZADO PELO BOTÃO E INATIVAÇÃO PARA ALTERAR O STATUS DA ENTIDADE */
	public void inativarCadastro() {
		cardapio.setEstatus(EstatusEntidadeEnum.INATIVO);
		alterar();
	}


	/** ALTERA OS DADOS DA ENTIDADE, METODO CHAMADO PELO inativar() */
	public void alterar() {

		try {
			// altera o status do cardapio para inativo
			cardapio = servicoCardapio.salvarOuAtualizar(cardapio);
			cardapio = new Cardapio();// retirar estas linhas futuramente

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
