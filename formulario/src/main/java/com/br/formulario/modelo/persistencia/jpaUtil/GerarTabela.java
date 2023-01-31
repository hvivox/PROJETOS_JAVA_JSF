package com.br.formulario.modelo.persistencia.jpaUtil;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.br.formulario.modelo.persistencia.entidade.mapeadas.CicloFaixa;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DifusaoDanca;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.FestivalNovosTalentos;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PortaAberta;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PromocaoIdoso;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.SeminarioEnvelhecimento;


public class GerarTabela {

	/** @param args */
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		
		cfg.addAnnotatedClass(PromocaoIdoso.class);
		//cfg.addAnnotatedClass(PortaAberta.class);
		//cfg.addAnnotatedClass(FestivalNovosTalentos.class);
		//cfg.addAnnotatedClass(ModAmArtes.class);
		//cfg.addAnnotatedClass(AmazonasArtes.class);
		cfg.addAnnotatedClass(SeminarioEnvelhecimento.class);
		// Roda o script de criação do esquema (schema).
		// param1 - script - imprime no console os comandos DDL
		// param2 - export - exporta o script para o banco de dados
		SchemaExport se = new SchemaExport(cfg);
		se.create(false, true);
		
		// ATUALIZA COLUNAS DE TABALAS JÁ CRIADAS ELA APENAS ACRESCENTA NOVAS
		// COLUNAS, NÃO DROPA COLUNA
		//SchemaUpdate su = new SchemaUpdate(cfg);
		//su.execute(true, true);

		// faz a comparação entre as classes mapeadas e as classes geradas
		// SchemaValidator sv = new SchemaValidator(cfg);
		// sv.validate();

	}
}
