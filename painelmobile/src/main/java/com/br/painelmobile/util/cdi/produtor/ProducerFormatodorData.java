package com.br.painelmobile.util.cdi.produtor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.enterprise.inject.Produces;

import com.br.painelmobile.util.cdi.qualifier.DataAno;
import com.br.painelmobile.util.cdi.qualifier.DataMesPorExtenso;
import com.br.painelmobile.util.cdi.qualifier.DataTexto;

public class ProducerFormatodorData {
	
	@Produces @DataTexto
	public DateFormat get() {
		return new SimpleDateFormat("dd/MM/yyyy",new Locale("pt", "BR"));				
	}
	
	
	@Produces @DataMesPorExtenso
	public DateFormat getMesPorExtenso() {
		return new SimpleDateFormat("MMMM",new Locale("pt", "BR"));				
	}
	
	
	@Produces @DataAno
	public DateFormat getAno() {
		return new SimpleDateFormat("Y",new Locale("pt", "BR"));				
	}
	
	
}

