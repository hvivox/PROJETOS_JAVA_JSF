package com.br.sdni.controle.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DualListModel;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.negocios.servico.ServiceGrupo;
import com.br.sdni.modelo.negocios.servico.ServicePermissao;
import com.br.sdni.modelo.negocios.servico.ServiceUsuario;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.sdni.util.managerbean.diversos.FacesUtil;
import com.br.sdni.util.security.GenerateHashPasswordUtil;

@Named("mbCadastroUsuario")
@ViewScoped
public class MBCadastroUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private String senhaUsuarioBD = null;
	private Usuario usuario;
	private Grupo grupo;
	private DualListModel<Grupo> listaGrupoDualList;
	private DualListModel<Permissao> listaPermissoesDualList;
	private StatusEntidadeEnum[] listaEstatus = StatusEntidadeEnum.values();
	private List<Grupo> listaTodosGrupos;
	
	@Inject
	private ServiceUsuario servicoUsuario;
	
	@Inject
	private ServiceGrupo servicoGrupo;
	
	@Inject
	private ServicePermissao servicoPermissao;
		
	
	public void inicializar() {		
		
		if (usuario == null) {
			limpar();
		}
		
		preencherPickListGrupoDoUsusario();
		preencherPickListPermissoesDoGrupo();
		
	}
	
	
	public void preencherPickListGrupoDoUsusario() {
		if (usuario == null) {
			limpar();
		}
		else {
			List<Grupo> listaGrupo = servicoGrupo.todos();
			listaGrupo.removeAll(usuario.getGrupos());
			listaGrupoDualList = new DualListModel<>(listaGrupo, new ArrayList<>( usuario.getGrupos() ) );			
		}
	}
	
	
	
	public void preencherPickListPermissoesDoGrupo() {
		//AO CARREGAR A TELA O COMBO PERMISSOES NAO TEM UM VALOR SETADO ENTÃO É POSTO O PRIMEITO ITEM DA LISTA
		if(grupo == null ) {
			listaTodosGrupos = servicoGrupo.todos();		
			
			//PEGA O PRIMEIRO ELEMENTO DA LISTA QUE CORRESPONDE AO 
			if(listaTodosGrupos.size() > 0) {
				grupo = listaTodosGrupos.get(0);			
			}			
		}		
		
		//SE ENCONTRAR UM GRUPO PREENCHIDO, SE NÃO DEIXA O PICKLIST VAZIO		
		List<Permissao> listaPermissoes = servicoPermissao.todos();
		listaPermissoes.removeAll( grupo.getPermisoes() );
		listaPermissoesDualList = new DualListModel<>(listaPermissoes, new ArrayList<>( grupo.getPermisoes() ));			
    }
	
	
	
	public void limpar() {
		this.usuario = new Usuario();
		listaGrupoDualList =  new DualListModel<>( servicoGrupo.todos(), new ArrayList<>() );
	}
	
	
	public void limparPickListPermissao() {
		listaPermissoesDualList =  new DualListModel<>( servicoPermissao.todos(), new ArrayList<>() );
	}
	
	
	//SALVAR USUÁRIO E GRUPO DE ACESSO ASSOCIADO A ELE
	public String salvar() {
		String senhaDigitadaNaTela = usuario.getSenha();
		
		try {			
			//GARANTE QUE A SENHA DO NOVO USUÁRIO NÃO SEJA BRANCA
			if( !StringUtils.isNotBlank(senhaUsuarioBD) && senhaDigitadaNaTela.isEmpty() ) {
				FacesUtil.addMessage(FacesMessage.SEVERITY_WARN, "É necessáro informar a senha para o usuário novo");
				return "";
			}
			
			
			//MODO EDITAR -  NÃO ALTEROU A SENHA (LEMBRANDO QUE O CAMPO FICA EM BRANCO)
			else if( StringUtils.isNotBlank(senhaUsuarioBD) && senhaDigitadaNaTela.isEmpty() ) {
				usuario.setSenha(senhaUsuarioBD); //SALVA A SENHA ATUAL DO BANCO SEM ALTERAR				
				usuario.setGrupos(listaGrupoDualList.getTarget());
				servicoUsuario.salvarOuAtualizar(usuario);
				
				limpar();				
				FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");				 
				  FacesContext.getCurrentInstance()
				      .getExternalContext()
				      .getFlash().setKeepMessages(true);					
			}
			
			
			//MODO EDITAR - SE DIGITAR NOVA SENHA 
			else {			
			String senhaCriptogragada = GenerateHashPasswordUtil.generateHash(usuario.getSenha());			
			
			usuario.setSenha(senhaCriptogragada);			
			usuario.setGrupos(listaGrupoDualList.getTarget());
			servicoUsuario.salvarOuAtualizar(usuario);
			limpar();
			
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "salvar_alterar", "");			 
			  FacesContext.getCurrentInstance()
			      .getExternalContext()
			      .getFlash().setKeepMessages(true);	
			}
			  
		} catch (campoObrigatorioNaoPreenchido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoJaExistenteException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoTransienteSendoPersistido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		}		
		 return "consulta-usuario.xhtml?faces-redirect=true";
	}
	
	
	//SALVA AS PERMISSÕES QUE ESTÃO ASSOCIADAS AOS GRUPOS
	public void salvarPermissao() {

		try {
			
			grupo.setPermisoes(listaPermissoesDualList.getTarget());			
			servicoGrupo.salvarOuAtualizar(grupo);			
			limparPickListPermissao();			
			
			FacesUtil.addMessage(FacesMessage.SEVERITY_INFO, "Permissões associadas com sucesso!");
		} catch (campoObrigatorioNaoPreenchido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoNaoEncontradoException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoJaExistenteException e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		} catch (ObjetoTransienteSendoPersistido e) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"FABRICA DE LOG -: " + e.getCause() + "\n Mensagem Erro: " + e.getMessage());
			FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu o erro" + e.getCause());
		}
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		if(usuario != null) {
			this.senhaUsuarioBD = usuario.getSenha(); 
			
		}
		
		this.usuario = usuario;
	}

	
	public Grupo getGrupo() {
		return grupo;
	}


	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	public boolean isEditando() {
		return usuario != null && usuario.getId() == null;
	}


	public DualListModel<Grupo> getListaGrupoDualList() {
		return listaGrupoDualList;
	}


	public void setListaGrupoDualList(DualListModel<Grupo> listaGrupos) {
		this.listaGrupoDualList = listaGrupos;
	}	
	
	
	
	public DualListModel<Permissao> getListaPermissoesDualList() {
		return listaPermissoesDualList;
	}


	public void setListaPermissoesDualList(DualListModel<Permissao> listaPermissoes) {
		this.listaPermissoesDualList = listaPermissoes;
	}


	public List<Grupo> getListaTodosGrupos() {		
		return listaTodosGrupos;
	}

	public void setListaTodosGrupos(List<Grupo> listaTodosGrupos) {
		this.listaTodosGrupos = listaTodosGrupos;
	}
	
	
	/** MOSTRA UMA ARRAY COM OS ENUM DA REFERIDA CLASSE
	 * @return */
	public StatusEntidadeEnum[] getListaEstatus() {
		return listaEstatus;
	}
}
