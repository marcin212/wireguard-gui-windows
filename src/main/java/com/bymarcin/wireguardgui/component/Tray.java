package com.bymarcin.wireguardgui.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.bymarcin.wireguardgui.ResourceManager;
import com.bymarcin.wireguardgui.service.ConsoleLogger;
import javafx.application.Platform;

public class Tray {
	TrayIcon trayIcon;
	List<Runnable> onConnect = new LinkedList<>();
	List<Runnable> onDisconnect = new LinkedList<>();
	List<Runnable> onExit = new LinkedList<>();
	List<Runnable> onAbout = new LinkedList<>();
	List<Runnable> onConfiguration = new LinkedList<>();
	List<Runnable> onConsole = new LinkedList<>();
	MenuItem connectItem = new MenuItem("Connect");
	MenuItem disconnectItem = new MenuItem("Disconnect");
	MenuItem aboutItem = new MenuItem("About");
	MenuItem configurationItem = new MenuItem("Configuration");
	MenuItem exitItem = new MenuItem("Exit");
	MenuItem consoleItem = new MenuItem("Logs");
	private Image vpnOff;
	private Image vpnOn;
	
	public Tray() {
		if (!SystemTray.isSupported()) {
			ConsoleLogger.getInstance().append("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		try {
			vpnOn = new ImageIcon(ImageIO.read(ResourceManager.trayIconOn), "vpn icon on").getImage();
			vpnOff = new ImageIcon(ImageIO.read(ResourceManager.trayIconOff), "vpn icon off").getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		trayIcon = new TrayIcon(vpnOff);
		
		aboutItem.addActionListener(e -> Platform.runLater(() -> onAbout.forEach(Runnable::run)));
		configurationItem.addActionListener(e -> Platform.runLater(() -> onConfiguration.forEach(Runnable::run)));
		connectItem.addActionListener(e -> Platform.runLater(() -> onConnect.forEach(Runnable::run)));
		disconnectItem.addActionListener(e -> Platform.runLater(() -> onDisconnect.forEach(Runnable::run)));
		exitItem.addActionListener(e -> Platform.runLater(() -> onExit.forEach(Runnable::run)));
		consoleItem.addActionListener(e -> Platform.runLater(() -> onConsole.forEach(Runnable::run)));
		
		popup.add(configurationItem);
		popup.add(consoleItem);
		popup.add(connectItem);
		popup.add(disconnectItem);
		popup.addSeparator();
		popup.add(aboutItem);
		popup.add(exitItem);
		enableConnect(true);
		trayIcon.setPopupMenu(popup);
		
		try {
			final SystemTray tray = SystemTray.getSystemTray();
			tray.add(trayIcon);
		} catch (AWTException e) {
			ConsoleLogger.getInstance().append("TrayIcon could not be added.");
		}
	}
	
	public void addOnConnectListener(Runnable listener) {
		onConnect.add(listener);
	}
	
	public void addOnAboutListener(Runnable listener) {
		onAbout.add(listener);
	}
	
	public void addOnExitListener(Runnable listener) {
		onExit.add(listener);
	}
	
	public void addOnConfigurationListener(Runnable listener) {
		onConfiguration.add(listener);
	}
	
	public void addOnDisconnectListener(Runnable listener) {
		onDisconnect.add(listener);
	}
	
	public void addOnConsoleListener(Runnable listener) {
		onConsole.add(listener);
	}
	
	public void enableConnect(boolean enabled) {
		trayIcon.setImage(enabled?vpnOff:vpnOn);
		connectItem.setEnabled(enabled);
		disconnectItem.setEnabled(!enabled);
	}
	
}
