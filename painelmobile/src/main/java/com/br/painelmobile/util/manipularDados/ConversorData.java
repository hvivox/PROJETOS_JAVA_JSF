package com.br.painelmobile.util.manipularDados;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.inject.Inject;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.util.cdi.qualifier.DataAno;
import com.br.painelmobile.util.cdi.qualifier.DataMesPorExtenso;
import com.br.painelmobile.util.cdi.qualifier.DataTexto;

public class ConversorData {
	//private static  Locale localeBrasil = new Locale("pt","br");//portugues do localeBrasil
	@Inject
	@DataTexto
	private DateFormat dataTexto;
	
	@Inject
	@DataMesPorExtenso
	private DateFormat dataMesPorExtenso;
	
	@Inject
	@DataAno
	private DateFormat dataAno;
	
	
	/**
	 * @return Converte a data em texto ex:12/12/2016. Pegando a data atual
	 */
	public String convertCalendarEmTexto(Calendar dataCalendar){
		//Calendar c = Calendar.getInstance();		
		//c.set(2016, 02, 30);
		String dataString = dataTexto.format(dataCalendar.getTime());
		//System.out.println("data = "+dataCalendar);	
		
		return dataString;
	}
	
	
	
	/**
	 * @return retorna o mes por extenso da data Exemplo: (Fevereiro) 
	 * seta os valores da data de forma simples
	 */
	public String getMesPorExtenson(Calendar calendar){				
		String mes = dataMesPorExtenso.format(calendar.getTime());		
		return mes;
	}
		
	
	
	/**
	 * @param calendar: data a ser convertida
	 * @return String com este formato yyyy Ex: 2016
	 */
	public String getAnoPorExtenson(Calendar calendar){			
		Calendar anoString = calendar;		
		//SimpleDateFormat formatDate = new SimpleDateFormat("Y");
		String ano = dataAno.format(anoString.getTime());
		//System.out.println("Ano = "+ano);
		return ano;
	}
	
	
	public Calendar converterStringEmCalendar(String dataString){
		  try {
				//String data = "16/07/2008";
				//SimpleDateFormat formatoBrasil = new SimpleDateFormat("dd/MM/yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dataTexto.parse(dataString));
				
				
				return calendar;
				
			} catch (ParseException e) {
				LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
						e.getCause() + "\n Mensagem Erro: " + e.getMessage());				
				return null;
			}
	}
	
	
	public static Calendar converteDateParaCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}
	
	
	
	/**
	 * Converte para o formato calendar
	 * @return Seta informações nas constantes da data
	 */
	public static Calendar converteEmCalendar(int dia, int mes, int ano){
		Calendar dataInputada = Calendar.getInstance();
		dataInputada.set(Calendar.DAY_OF_MONTH, dia);
		dataInputada.set(Calendar.MONTH, mes-1);//para o sistma o mês começa em 0 por isso tem que diminir um -1
		dataInputada.set(Calendar.YEAR, ano);		
		return dataInputada;
	}
	
	
	/**
	 * @return Exibe o formato de um gregorian calendar
	 */
	public void exibiFormatoGregorian(){
		Calendar gc = new GregorianCalendar();
		System.out.println("Gregorian Calendar: "+ gc);			
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1986, 02, 30);
		ConversorData converteData = new ConversorData();		
		converteData.getMesPorExtenson(calendar);		
	}
	
	
	
}
