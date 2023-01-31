package com.br.painelmobile.util.managerbean.diversos;

import javax.faces.event.ActionEvent;


/**
 * Classe contém somente os métodos padroes que normalmente os MBs 
 * irão utilizar para assessoria e manipulação da informação
 * @author hermogenes.silva
 *
 */
public abstract class GenericoBean {

	
	public abstract void inicializar();
	
	public abstract String doSalvar();
	
	public abstract String doLimpar();
	
	public abstract void doPesquisar(ActionEvent event);

	public abstract String showNovo();

	public abstract String showEditar();

	public abstract String showDetalhar();

	public abstract void doExcluir(ActionEvent event);




}
