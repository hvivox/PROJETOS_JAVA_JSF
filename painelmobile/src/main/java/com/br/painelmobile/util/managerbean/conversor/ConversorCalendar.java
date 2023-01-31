package com.br.painelmobile.util.managerbean.conversor;

import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.br.painelmobile.util.cdi.produtor.CDIServiceLocator;
import com.br.painelmobile.util.manipularDados.ConversorData;

@FacesConverter(value = "conversorCalendar")
public class ConversorCalendar implements Converter {
	// inject a partir da versão 2.3 do jsf
	// @Inject
	private ConversorData conversorData;


	public ConversorCalendar() {
		conversorData = CDIServiceLocator.getBean(ConversorData.class);
	}


	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Calendar calendarioConvertido = null;

		if (value != null && !value.isEmpty()) {
			calendarioConvertido = conversorData.converterStringEmCalendar(value);
		}
		return calendarioConvertido;

	}


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String resultado = null;
		
		if (value != null) {
			resultado = conversorData.convertCalendarEmTexto((Calendar) value);
		}

		return resultado;
	}

}
