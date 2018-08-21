package fr.voydstack.antykacraft.utils;

import java.util.logging.Logger;

public class LogHandler {
	private Logger logger;
	
	public LogHandler(Logger logger) {
		setLogger(logger);
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
