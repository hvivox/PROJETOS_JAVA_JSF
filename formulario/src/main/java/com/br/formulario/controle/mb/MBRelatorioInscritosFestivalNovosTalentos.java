package com.br.formulario.controle.mb;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.hibernate.Session;

import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.servico.ServiceFestivalNovosTalentos;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.FestivalNovosTalentos;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosFestivalNovosTalentos")
@RequestScoped
public class MBRelatorioInscritosFestivalNovosTalentos implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServiceFestivalNovosTalentos servicoFestivalNovosTalentos;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<FestivalNovosTalentos> listaInscritosFestivalNovosTalentos = new ArrayList<FestivalNovosTalentos>();
	private FestivalNovosTalentos festivalNovosTalentos;
	//private List<String> listaAnosInscricao;
	
	
	
	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosFestivalNovosTalentos = new ArrayList<FestivalNovosTalentos>();
			festivalNovosTalentos = new FestivalNovosTalentos();
			
		}
	}

	



	
	
	public List<String> listarDatasInscricao() {
		Calendar dataFinal=Calendar.getInstance();
		Integer anoAtual = dataFinal.get(Calendar.YEAR);
		List<String> listaAnos=new ArrayList<String>();
		
		for(Integer ano=2017; ano<=anoAtual;ano++){
			listaAnos.add( Integer.toString(ano) );
		}
		
		return listaAnos;
	}
	
	public List<FestivalNovosTalentos>  pesquisarPorFiltro() {
		try {
			if (listaInscritosFestivalNovosTalentos != null) {
				listaInscritosFestivalNovosTalentos.removeAll(listaInscritosFestivalNovosTalentos);
				listaInscritosFestivalNovosTalentos = servicoFestivalNovosTalentos.consultarPorAno(filtro);
				
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
		return listaInscritosFestivalNovosTalentos;
		
	}
	
	
	
	public void pesquisarFestivalPorAno() {
		try {
			if (listaInscritosFestivalNovosTalentos != null) {
				
				listaInscritosFestivalNovosTalentos.removeAll(listaInscritosFestivalNovosTalentos);
				LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(filtro.getAno()+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				
				
				listaInscritosFestivalNovosTalentos = servicoFestivalNovosTalentos.consultarPorAno(filtro);
				
			
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosFestivalNovosTalentos.jasper",
				this.response, parametros, "Inscritos_festival_novos_talentos.pdf");
		
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


	public List<FestivalNovosTalentos> getListaInscritosFestivalNovosTalentos() {
		
		return listaInscritosFestivalNovosTalentos;
	}


	public void setListaInscritosFestivalNovosTalentos(List<FestivalNovosTalentos> listaInscritosFestivalNovosTalentos) {
		this.listaInscritosFestivalNovosTalentos = listaInscritosFestivalNovosTalentos;
	}


	public FestivalNovosTalentos getFestivalNovosTalentos() {
		return festivalNovosTalentos;
	}


	public void setFestivalNovosTalentos(FestivalNovosTalentos festivalNovosTalentos) {
		this.festivalNovosTalentos = festivalNovosTalentos;
	}
	
	
	
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}
	
	public void setFiltro(FiltroPesquisaPadrao filtro) {
		this.filtro = filtro;
	}

	
	
	


	public MBRelatorioInscritosFestivalNovosTalentos() {
		filtro = new FiltroPesquisaPadrao();
	}

}