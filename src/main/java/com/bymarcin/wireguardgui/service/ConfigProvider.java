package com.bymarcin.wireguardgui.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.bymarcin.wireguardgui.model.WGConfig;

public class ConfigProvider {
	private WGConfig config;
	private static final ConfigProvider configProvider = new ConfigProvider();
	private Path saveFile = Paths.get(System.getProperty("user.home"), ".wggui","config.json");
	private ConfigProvider(){
		try {
			config = WGConfig.load(saveFile.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ConfigProvider getInstance(){
		return configProvider;
	}
	
	public WGConfig getConfig() {
		return config;
	}
	
	public void save(){
		try {
			config.save(saveFile.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
