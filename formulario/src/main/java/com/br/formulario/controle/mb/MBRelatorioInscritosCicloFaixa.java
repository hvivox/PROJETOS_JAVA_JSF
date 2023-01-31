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
import com.br.formulario.modelo.negocios.servico.ServiceCicloFaixa;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.CicloFaixa;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosCicloFaixa")
@RequestScoped
public class MBRelatorioInscritosCicloFaixa implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServiceCicloFaixa servicoCicloFaixa;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<CicloFaixa> listaInscritosCicloFaixa = new ArrayList<CicloFaixa>();
	private CicloFaixa cicloFaixa;
	
	
	
	public MBRelatorioInscritosCicloFaixa() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosCicloFaixa = new ArrayList<CicloFaixa>();
			cicloFaixa = new CicloFaixa();			
		}
	}


	public List<CicloFaixa>  pesquisarPorFiltro() {
		try {
			if (listaInscritosCicloFaixa != null) {
				listaInscritosCicloFaixa.removeAll(listaInscritosCicloFaixa);
				listaInscritosCicloFaixa = servicoCicloFaixa.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
		return listaInscritosCicloFaixa;
		
	}
	
	
	
	public void pesquisarPorAno() {
		try {
			if (listaInscritosCicloFaixa != null) {
				listaInscritosCicloFaixa.removeAll(listaInscritosCicloFaixa);
				listaInscritosCicloFaixa = servicoCicloFaixa.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosCicloFaixa.jasper",
				this.response, parametros, "Inscritos_Ciclo_Faixa.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "A execução do relatório não retornou dados.");
			
		}		
		
	}

	
	
	//@NotNull(message="Preencha o campo data início corretamente")
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	//@NotNull(message="Preencha o campo data fim corretamente")
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<CicloFaixa> getListaInscritosCicloFaixa() {
		
		return listaInscritosCicloFaixa;
	}


	public void setListaInscritosCicloFaixa(List<CicloFaixa> listaInscritosCicloFaixa) {
		this.listaInscritosCicloFaixa = listaInscritosCicloFaixa;
	}


	public CicloFaixa getCicloFaixa() {
		return cicloFaixa;
	}


	public void setCicloFaixa(CicloFaixa cicloFaixa) {
		this.cicloFaixa = cicloFaixa;
	}
	
	
	
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}
	
	public void setFiltro(FiltroPesquisaPadrao filtro) {
		this.filtro = filtro;
	}

	
		

}