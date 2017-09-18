package com.bymarcin.wireguardgui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ConsoleController implements Initializable {
	@FXML
	TextArea textArea;
	
	
	public void onLine(String s) {
		textArea.appendText(s);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
