package com.bymarcin.wireguardgui.service;

import com.bymarcin.wireguardgui.WireguardGUI;
import com.bymarcin.wireguardgui.controller.ConsoleController;

public class ConsoleLogger {
	private static final ConsoleLogger consoleLogger = new ConsoleLogger();
	private ConsoleController controller;
	private ConsoleLogger() {
		controller = (ConsoleController) WireguardGUI.getConsoleGui().controller();
	}
	
	public static ConsoleLogger getInstance() {
		return consoleLogger;
	}
	
	public void append(String s){
		controller.onLine(s + "\n");
	}
}
