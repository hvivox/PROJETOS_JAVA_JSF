package com.br.painelmobile.util.managerbean.listener;

import java.util.logging.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.LogFactory;

public class OuvinteFases implements PhaseListener {

	private static final long serialVersionUID = -3244481149755416825L;

	@Override
	public void afterPhase(PhaseEvent event) {
		LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).info(event.getPhaseId()+" ENCERROU");
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).info(event.getPhaseId()+" INICIOU");			
	}

	@Override
	public PhaseId getPhaseId() {
		
		return PhaseId.ANY_PHASE;
	}

}
