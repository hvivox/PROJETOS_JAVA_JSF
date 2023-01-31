package com.br.painelmobile.modelo.negocios.excecao;

public class ObjetoJaExistenteException extends Exception {

	private static final long serialVersionUID = -4967940379806496290L;
	// Constante com a mensagem de entidade j� cadastrado.

	/**
	 * O entityId da entidade j� existente no cadastro.
	 */
	private Long entityId;

	private String nomeEntidade;
	private static final String  MSG = "Entidade já cadastrada";
	/**
	 * O construtor da classe. Inicializa a mensagem da super-classe com uma
	 * mensagem padr�o de entidade j� cadastrado e inicializa o entityId cujo
	 * entidade j� existe no cadastro.
	 * 
	 * @param entityId
	 *            o entityId da entidade j� existente no cadastro.
	 */
	public ObjetoJaExistenteException(Long entityId) {

		super(MSG);
		this.entityId = entityId;
	}

	/**
	 * Retorna o c�digo da entidade j� existente no cadastro.
	 * 
	 * @return Long o c�digo do entidade j� existente no cadastro.
	 */
	public Long getEntityId() {
		return entityId;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	@Override
	public String getMessage() {
		String mensagem = super.getMessage();
		if (nomeEntidade != null) {
			mensagem = mensagem + "(" + nomeEntidade + ":" + entityId + ").";
		} else {
			mensagem = mensagem + "(Id " + entityId + ").";
		}
		return mensagem;
	}
}
