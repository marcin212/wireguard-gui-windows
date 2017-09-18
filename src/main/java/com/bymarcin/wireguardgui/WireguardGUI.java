package com.bymarcin.wireguardgui;

import com.bymarcin.wireguardgui.component.Tray;
import com.bymarcin.wireguardgui.service.ConfigProvider;
import com.bymarcin.wireguardgui.service.WGProcess;
import com.bymarcin.wireguardgui.view.AboutGui;
import com.bymarcin.wireguardgui.view.ConsoleGui;
import com.bymarcin.wireguardgui.view.ITrayWindow;
import com.bymarcin.wireguardgui.view.WireGuardGui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class WireguardGUI extends Application {
	private static ITrayWindow wireGuardGui;
	private static ITrayWindow consoleGui;
	private static ITrayWindow aboutGui;
	private static Tray tray;
	private static Stage primary;
	
	private Stage getNewStage() {
		Stage s = new Stage();
		s.setResizable(false);
		s.setTitle("Wireguard GUI");
		return s;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		WireguardGUI.primary = primaryStage;
		Platform.setImplicitExit(false);
		tray = new Tray();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Wireguard GUI");
		wireGuardGui = new WireGuardGui();
		consoleGui = new ConsoleGui();
		aboutGui = new AboutGui();
		tray.addOnConfigurationListener(() -> wireGuardGui.show(primaryStage));
		tray.addOnAboutListener(() -> aboutGui.show(primaryStage));
		tray.addOnConsoleListener(() -> consoleGui.show(primaryStage));
		tray.addOnDisconnectListener(WGProcess.getInstance()::stop);
		tray.addOnConnectListener(() -> {
			tray.enableConnect(false);
			WGProcess.getInstance().start(ConfigProvider.getInstance().getConfig());
			new Thread(() -> {
				try {
					WGProcess.getInstance().waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> tray.enableConnect(true));
			}).start();
		});
		tray.addOnExitListener(()->{
			WGProcess.getInstance().stop();
			Platform.exit();
			System.exit(0);
		});
	}
	
	public static ITrayWindow getConsoleGui() {
		return consoleGui;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
