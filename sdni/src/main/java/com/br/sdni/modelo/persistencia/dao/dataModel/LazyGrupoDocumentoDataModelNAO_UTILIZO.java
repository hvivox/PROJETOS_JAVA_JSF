package com.br.sdni.modelo.persistencia.dao.dataModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import com.br.sdni.modelo.negocios.servico.ServiceGrupoDocumento;
import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;

/** @author hvivox CLASSE CHAMADA TODA VEZ QUE O DATATABLE É RENDENRIZADO */
public class LazyGrupoDocumentoDataModelNAO_UTILIZO extends LazyDataModel<GrupoDocumento>
		implements
		SelectableDataModel<GrupoDocumento>, Serializable {

	private static final long serialVersionUID = 1L;

	private FiltroPesquisaPadrao filtro;
	private ServiceGrupoDocumento servicoGrupoDocumento;


	public LazyGrupoDocumentoDataModelNAO_UTILIZO(ServiceGrupoDocumento servicoGrupoDocumento,
			FiltroPesquisaPadrao filtro) {		
		this.servicoGrupoDocumento = servicoGrupoDocumento;
		this.filtro = filtro;
	}


	@Override
	public List<GrupoDocumento> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setPropriedadeOrdenacao(sortField);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));

		setRowCount(servicoGrupoDocumento.quantidadeFiltrados(filtro));

		return servicoGrupoDocumento.filtrados(filtro);

	}

}
