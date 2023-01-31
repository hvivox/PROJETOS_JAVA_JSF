package com.br.sdni.modelo.persistencia.jpaUtil;

import org.hibernate.cfg.Configuration;

import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;



public class GerarTabela {

	/** @param args */
	public static void main(String[] args) {
		Configuration cfg = new Configuration();

		cfg.addAnnotatedClass(Documento.class);
		cfg.addAnnotatedClass(Usuario.class);
		cfg.addAnnotatedClass(Grupo.class);
		cfg.addAnnotatedClass(Permissao.class);
		
		// Roda o script de criação do esquema (schema).
		// param1 - script - imprime no console os comandos DDL
		// param2 - export - exporta o script para o banco de dados
		//SchemaExport se = new SchemaExport(cfg);
		//se.create(false, true);

		// ATUALIZA COLUNAS DE TABALAS JÁ CRIADAS ELA APENAS ACRESCENTA NOVAS
		// COLUNAS, NÃO DROPA COLUNA
		//
		//SchemaUpdate su = new SchemaUpdate(cfg);
		//su.execute(true, true);

		// faz a comparação entre as classes mapeadas e as classes geradas
		// SchemaValidator sv = new SchemaValidator(cfg);
		// sv.validate();

	}
}
