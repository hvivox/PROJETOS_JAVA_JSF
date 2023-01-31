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
import com.br.formulario.modelo.negocios.servico.ServiceDifusaoDanca;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DifusaoDanca;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosDifusaoDanca")
@RequestScoped
public class MBRelatorioInscritosDifusaoDanca implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServiceDifusaoDanca servicoDifusaoDanca;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<DifusaoDanca> listaInscritosDifusaoDanca = new ArrayList<DifusaoDanca>();
	private DifusaoDanca difusaoDanca;
	
	
	
	public MBRelatorioInscritosDifusaoDanca() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosDifusaoDanca = new ArrayList<DifusaoDanca>();
			difusaoDanca = new DifusaoDanca();			
		}
	}


	public List<DifusaoDanca>  pesquisarPorFiltro() {
		try {
			if (listaInscritosDifusaoDanca != null) {
				listaInscritosDifusaoDanca.removeAll(listaInscritosDifusaoDanca);
				listaInscritosDifusaoDanca = servicoDifusaoDanca.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
		return listaInscritosDifusaoDanca;
		
	}
	
	
	
	public void pesquisarPorAno() {
		try {
			if (listaInscritosDifusaoDanca != null) {
				listaInscritosDifusaoDanca.removeAll(listaInscritosDifusaoDanca);
				listaInscritosDifusaoDanca = servicoDifusaoDanca.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosDifusaoDanca.jasper",
				this.response, parametros, "Inscritos_difusao_danca.pdf");
		
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


	public List<DifusaoDanca> getListaInscritosDifusaoDanca() {
		
		return listaInscritosDifusaoDanca;
	}


	public void setListaInscritosDifusaoDanca(List<DifusaoDanca> listaInscritosDifusaoDanca) {
		this.listaInscritosDifusaoDanca = listaInscritosDifusaoDanca;
	}


	public DifusaoDanca getDifusaoDanca() {
		return difusaoDanca;
	}


	public void setDifusaoDanca(DifusaoDanca difusaoDanca) {
		this.difusaoDanca = difusaoDanca;
	}
	
	
	
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}
	
	public void setFiltro(FiltroPesquisaPadrao filtro) {
		this.filtro = filtro;
	}

	
		

}