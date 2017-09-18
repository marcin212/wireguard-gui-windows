package com.bymarcin.wireguardgui;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceManager {
	public static final URL configGui = ResourceManager.class.getResource("/ConfigGui.fxml");
	public static final URL wireGuardGui = ResourceManager.class.getResource("/WireguardGui.fxml");
	public static final URL consoleGui = ResourceManager.class.getResource("/Console.fxml");
	public static final URL aboutGui = ResourceManager.class.getResource("/About.fxml");
	public static final URI iconCreditsURL;
	public static final URI sourceURL;
	public static final URI issuesURL;
	public static final InputStream trayIconOff = ResourceManager.class.getResourceAsStream("/icons/vpn-off.gif");
	public static final InputStream trayIconOn = ResourceManager.class.getResourceAsStream("/icons/vpn-on.gif");
	
	
	static {
		URI tempURL;
		try {
			tempURL = new URI("https://icons8.com/icon/17412/VPN");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			tempURL = null;
		}
		iconCreditsURL = tempURL;
		try {
			tempURL = new URI("https://github.com/marcin212/wireguard-gui-windows");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			tempURL = null;
		}
		sourceURL = tempURL;
		
		try {
			tempURL = new URI("https://github.com/marcin212/wireguard-gui-windows/issues");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			tempURL = null;
		}
		issuesURL = tempURL;
	}
}
