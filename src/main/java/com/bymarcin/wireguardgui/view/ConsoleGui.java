package com.bymarcin.wireguardgui.view;

import java.io.IOException;

import com.bymarcin.wireguardgui.controller.ConsoleController;
import com.bymarcin.wireguardgui.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConsoleGui implements ITrayWindow<ConsoleController> {
	Scene scene;
	ConsoleController controller;
	
	public ConsoleGui() {
		FXMLLoader loader = new FXMLLoader(ResourceManager.consoleGui);
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
	public ConsoleController controller() {
		return controller;
	}
}
