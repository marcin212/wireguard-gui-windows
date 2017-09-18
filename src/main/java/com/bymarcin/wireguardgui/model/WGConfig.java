package com.bymarcin.wireguardgui.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

public class WGConfig {
	String privateKey;
	String publicKey;
	List<PeerModel> peers;
	int listenPort;
	String interfaceName;
	boolean replacePeers;
	
	private WGConfig() {
		Curve25519KeyPair keyPair = Curve25519.getInstance(Curve25519.BEST).generateKeyPair();
		this.privateKey = Hex.encodeHexString(keyPair.getPrivateKey());
		this.publicKey = Hex.encodeHexString(keyPair.getPublicKey());
		this.listenPort = 51820;
		this.interfaceName = "";
		this.peers = Collections.EMPTY_LIST;
		this.replacePeers = true;
	}
	
	public static WGConfig load(File configFile) throws IOException {
		if (!configFile.exists()) {
			createConfig(configFile);
		}
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(new FileReader(configFile), WGConfig.class);
		
	}
	
	public void save(File file) throws IOException {
		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file, false);
		fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
		fw.close();
	}
	
	private static void createConfig(File file) throws IOException {
		new WGConfig().save(file);
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public List<PeerModel> getPeers() {
		return peers;
	}
	
	public void setPeers(List<PeerModel> peers) {
		this.peers = peers;
	}
	
	public int getListenPort() {
		return listenPort;
	}
	
	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	public boolean isReplacePeers() {
		return replacePeers;
	}
	
	public void setReplacePeers(boolean replacePeers) {
		this.replacePeers = replacePeers;
	}
}
