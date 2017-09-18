package com.bymarcin.wireguardgui.view;

import java.io.IOException;

import com.bymarcin.wireguardgui.ResourceManager;
import com.bymarcin.wireguardgui.controller.WireguardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WireGuardGui implements ITrayWindow<WireguardController> {
	Scene scene;
	WireguardController controller;
	
	public WireGuardGui() {
		FXMLLoader loader = new FXMLLoader(ResourceManager.wireGuardGui);
		try {
			Parent p = loader.load();
			scene = new Scene(p);
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void show(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}
	
	
	@Override
	public WireguardController controller() {
		return controller;
	}
	
}
