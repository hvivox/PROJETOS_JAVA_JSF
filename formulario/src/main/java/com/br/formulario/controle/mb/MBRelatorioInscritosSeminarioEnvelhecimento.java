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
import com.br.formulario.modelo.negocios.servico.ServiceSeminarioEnvelhecimento;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.SeminarioEnvelhecimento;
import com.br.formulario.util.managerbean.diversos.FacesUtil;
import com.br.formulario.util.report.ExecutorRelatorio;



@Named("mbRelatorioInscritosSeminarioEnvelhecimento")
@RequestScoped
public class MBRelatorioInscritosSeminarioEnvelhecimento implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private ServiceSeminarioEnvelhecimento servicoSeminarioEnvelhecimento;
	
	private Date dataInicio;
	private Date dataFim;
	private FiltroPesquisaPadrao filtro;
	private List<SeminarioEnvelhecimento> listaInscritosSeminarioEnvelhecimento = new ArrayList<SeminarioEnvelhecimento>();
	private SeminarioEnvelhecimento seminarioEnvelhecimento;
	
	
	
	public MBRelatorioInscritosSeminarioEnvelhecimento() {
		filtro = new FiltroPesquisaPadrao();
	}


	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			listaInscritosSeminarioEnvelhecimento = new ArrayList<SeminarioEnvelhecimento>();
			seminarioEnvelhecimento = new SeminarioEnvelhecimento();			
		}
	}


	public List<SeminarioEnvelhecimento>  pesquisarPorFiltro() {
		try {
			if (listaInscritosSeminarioEnvelhecimento != null) {
				listaInscritosSeminarioEnvelhecimento.removeAll(listaInscritosSeminarioEnvelhecimento);
				listaInscritosSeminarioEnvelhecimento = servicoSeminarioEnvelhecimento.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
		return listaInscritosSeminarioEnvelhecimento;
		
	}
	
	
	
	public void pesquisarPorAno() {
		try {
			if (listaInscritosSeminarioEnvelhecimento != null) {
				listaInscritosSeminarioEnvelhecimento.removeAll(listaInscritosSeminarioEnvelhecimento);
				listaInscritosSeminarioEnvelhecimento = servicoSeminarioEnvelhecimento.consultarPorAno(filtro);
			}

		} catch (ObjetoNaoEncontradoException e) {
			FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, e.getMessage());
		}
	}
	
	
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		/*parametros.put("dataInicio", this.dataInicio);
		parametros.put("dataFim", this.dataFim);*/
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritosSeminarioEnvelhecimento.jasper",
				this.response, parametros, "Inscritos_seminario_envelhecimento.pdf");
		
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


	public List<SeminarioEnvelhecimento> getListaInscritosSeminarioEnvelhecimento() {
		
		return listaInscritosSeminarioEnvelhecimento;
	}


	public void setListaInscritosSeminarioEnvelhecimento(List<SeminarioEnvelhecimento> listaInscritosSeminarioEnvelhecimento) {
		this.listaInscritosSeminarioEnvelhecimento = listaInscritosSeminarioEnvelhecimento;
	}


	public SeminarioEnvelhecimento getSeminarioEnvelhecimento() {
		return seminarioEnvelhecimento;
	}


	public void setSeminarioEnvelhecimento(SeminarioEnvelhecimento seminarioEnvelhecimento) {
		this.seminarioEnvelhecimento = seminarioEnvelhecimento;
	}
	
	
	
	public FiltroPesquisaPadrao getFiltro() {
		return filtro;
	}
	
	public void setFiltro(FiltroPesquisaPadrao filtro) {
		this.filtro = filtro;
	}

	
		

}