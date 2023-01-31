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
import com.br.formulario.modelo.negocios.servico.ServicePortaAberta;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PortaAberta;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosPortaAberta")
@RequestScoped
public class MBRelatorioInscritosPortaAberta implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServicePortaAberta servicoPortaAberta;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<PortaAberta> listaInscritosPortaAberta = new ArrayList<PortaAberta>();
	private PortaAberta portaAberta;
	
	
	
	public MBRelatorioInscritosPortaAberta() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosPortaAberta = new ArrayList<PortaAberta>();
			portaAberta = new PortaAberta();			
		}
	}


	public List<PortaAberta>  pesquisarPorFiltro() {
		try {
			if (listaInscritosPortaAberta != null) {
				listaInscritosPortaAberta.removeAll(listaInscritosPortaAberta);
				listaInscritosPortaAberta = servicoPortaAberta.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
		return listaInscritosPortaAberta;
		
	}
	
	
	
	public void pesquisarPorAno() {
		try {
			if (listaInscritosPortaAberta != null) {
				listaInscritosPortaAberta.removeAll(listaInscritosPortaAberta);
				listaInscritosPortaAberta = servicoPortaAberta.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosPortaAberta.jasper",
				this.response, parametros, "Inscritos_porta_aberta.pdf");
		
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


	public List<PortaAberta> getListaInscritosPortaAberta() {
		
		return listaInscritosPortaAberta;
	}


	public void setListaInscritosPortaAberta(List<PortaAberta> listaInscritosPortaAberta) {
		this.listaInscritosPortaAberta = listaInscritosPortaAberta;
	}


	public PortaAberta getPortaAberta() {
		return portaAberta;
	}


	public void setPortaAberta(PortaAberta portaAberta) {
		this.portaAberta = portaAberta;
	}
	
	
	
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}
	
	public void setFiltro(FiltroPesquisaPadrao filtro) {
		this.filtro = filtro;
	}

	
		

}