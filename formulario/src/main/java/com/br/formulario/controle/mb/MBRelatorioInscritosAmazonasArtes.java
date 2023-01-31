package com.br.formulario.controle.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.servico.ServiceAmazonasArtes;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosAmazonasArtes")
@RequestScoped
public class MBRelatorioInscritosAmazonasArtes implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServiceAmazonasArtes servicoAmazonasArtes;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<AmazonasArtes> listaInscritosAmazonasArtes = new ArrayList<AmazonasArtes>();
	private AmazonasArtes amazonasArtes;
	
	
	
	public MBRelatorioInscritosAmazonasArtes() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosAmazonasArtes = new ArrayList<AmazonasArtes>();
			amazonasArtes = new AmazonasArtes();
			// carrega a tabela com informaçãos ao rendenrizar a tela
			pesquisarPorFiltro();
		}
	}


	public void pesquisarPorFiltro() {
		try {
			if (listaInscritosAmazonasArtes!= null) {
				listaInscritosAmazonasArtes.removeAll(listaInscritosAmazonasArtes);
				listaInscritosAmazonasArtes = servicoAmazonasArtes.consultarTodos();
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosAmazonasArtes.jasper",
				this.response, parametros, "Inscritos_Amazonia_Artes.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "A execução do relatório não retornou dados.");
			
		}
	}

	
	
	@NotNull(message="Preencha o campo data início corretamente")
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull(message="Preencha o campo data fim corretamente")
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<AmazonasArtes> getListaInscritosAmazonasArtes() {
		return listaInscritosAmazonasArtes;
	}


	public void setListaInscritosAmazonasArtes(List<AmazonasArtes> listaInscritosAmazonasArtes) {
		this.listaInscritosAmazonasArtes = listaInscritosAmazonasArtes;
	}


	public AmazonasArtes getAmazonasArtes() {
		return amazonasArtes;
	}


	public void setAmazonasArtes(AmazonasArtes amazonasArtes) {
		this.amazonasArtes = amazonasArtes;
	}
	
	
	
	
	
	
	
	

}