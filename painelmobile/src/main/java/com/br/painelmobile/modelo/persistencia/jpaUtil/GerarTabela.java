package com.br.painelmobile.modelo.persistencia.jpaUtil;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaServico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Grupo;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Mensagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Parceiro;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Selecao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Servico;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Unidade;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Usuario;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Video;

public class GerarTabela {

	/** @param args */
	public static void main(String[] args) {
		Configuration cfg = new Configuration();

		cfg.addAnnotatedClass(Cardapio.class);
		cfg.addAnnotatedClass(CategoriaParceiro.class);
		cfg.addAnnotatedClass(Evento.class);
		cfg.addAnnotatedClass(GaleriaImagem.class);
		cfg.addAnnotatedClass(Imagem.class);
		cfg.addAnnotatedClass(Mensagem.class);
		cfg.addAnnotatedClass(Parceiro.class);
		cfg.addAnnotatedClass(Selecao.class);
		cfg.addAnnotatedClass(CategoriaServico.class);
		cfg.addAnnotatedClass(Servico.class);
		cfg.addAnnotatedClass(Unidade.class);
		cfg.addAnnotatedClass(Video.class);
		cfg.addAnnotatedClass(Usuario.class);
		cfg.addAnnotatedClass(Grupo.class);

		// Roda o script de criação do esquema (schema).
		// param1 - script - imprime no console os comandos DDL
		// param2 - export - exporta o script para o banco de dados
		// SchemaExport se = new SchemaExport(cfg);
		// se.create(false, true);

		// ATUALIZA COLUNAS DE TABALAS JÁ CRIADAS ELA APENAS ACRESCENTA NOVAS
		// COLUNAS, NÃO DROPA COLUNA
		SchemaUpdate su = new SchemaUpdate(cfg);
		su.execute(true, true);

		// faz a comparação entre as classes mapeadas e as classes geradas
		// SchemaValidator sv = new SchemaValidator(cfg);
		// sv.validate();

	}
}
