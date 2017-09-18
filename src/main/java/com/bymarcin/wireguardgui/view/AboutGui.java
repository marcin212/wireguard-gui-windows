package com.bymarcin.wireguardgui.view;

import java.io.IOException;

import com.bymarcin.wireguardgui.ResourceManager;
import com.bymarcin.wireguardgui.controller.AboutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutGui implements ITrayWindow<AboutController> {
	Scene scene;
	AboutController controller;
	
	public AboutGui() {
		FXMLLoader loader = new FXMLLoader(ResourceManager.aboutGui);
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
	public AboutController controller() {
		return controller;
	}
	

}
