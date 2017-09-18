package com.bymarcin.wireguardgui.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.bymarcin.wireguardgui.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;

public class AboutController implements Initializable{
	@FXML
	Hyperlink sourceLink;
	@FXML
	Hyperlink issuesLink;
	@FXML
	Hyperlink iconCreditsLink;
	
	@FXML
	Pane mainPane;
	
	@FXML
	void close() {
		mainPane.getScene().getWindow().hide();
	}
	
	@FXML
	void onIssueUrl(){
		if(Desktop.isDesktopSupported())
		{
			try {
				Desktop.getDesktop().browse(ResourceManager.issuesURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	void onSourceUrl(){
		if(Desktop.isDesktopSupported())
		{
			try {
				Desktop.getDesktop().browse(ResourceManager.sourceURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	void onIconCreditsUrl(){
		if(Desktop.isDesktopSupported())
		{
			try {
				Desktop.getDesktop().browse(ResourceManager.iconCreditsURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sourceLink.setText(ResourceManager.sourceURL.toString());
		issuesLink.setText(ResourceManager.issuesURL.toString());
		iconCreditsLink.setText(ResourceManager.iconCreditsURL.toString());
	}
}
