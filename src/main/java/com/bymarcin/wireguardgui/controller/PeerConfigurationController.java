package com.bymarcin.wireguardgui.controller;


import com.bymarcin.wireguardgui.model.PeerModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PeerConfigurationController {
	@FXML
	JFXTextField endpoint;
	@FXML
	JFXTextField publicKey;
	@FXML
	JFXTextField presharedKey;
	@FXML
	JFXTextField allowedIP;
	@FXML
	JFXCheckBox replaceAllowedIP;
	@FXML
	JFXSlider persistentKeepaliveInterval;
	@FXML
	JFXButton cancel;
	
	boolean canceled = false;
	
	public PeerModel getItem() {
		if (canceled) {
			return null;
		}
		return new PeerModel(endpoint.getText(), publicKey.getText(), presharedKey.getText(), allowedIP.getText(), replaceAllowedIP.isSelected(), (int) persistentKeepaliveInterval.getValue());
	}
	
	@FXML
	void save() {
		canceled = false;
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void cancel() {
		canceled = true;
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
	
	public void loadModel(PeerModel model) {
		endpoint.setText(model.getEndpoint());
		publicKey.setText(model.getPublicKey());
		presharedKey.setText(model.getPresharedKey());
		allowedIP.setText(model.getAllowedIP());
		replaceAllowedIP.setSelected(model.isReplaceAllowedIP());
		persistentKeepaliveInterval.setValue(model.getPersistentKeepaliveInterval());
	}
	
}
