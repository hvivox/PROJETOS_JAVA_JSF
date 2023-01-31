package com.br.sdni.util.cdi.produtor;


import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.commons.logging.LogFactory;


public class ProducerLoggerFactory {

	private InjectionPoint ip;

	public ProducerLoggerFactory() {
		
	}
	
	@Inject
	private ProducerLoggerFactory(InjectionPoint ip) {
		this.ip = ip;
	}
	
	@Produces
	public Logger getLogger(){	
		return (Logger) LogFactory.getLog("Fábrica de log: "+ip.getMember().getDeclaringClass());
	}
}
