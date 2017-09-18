package com.bymarcin.wireguardgui.model;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class PeerModel {
	String endpoint;
	String publicKey;
	String presharedKey;
	String allowedIP;
	boolean replaceAllowedIP;
	int persistentKeepaliveInterval;
	
	public PeerModel() {
		endpoint = publicKey = "";
		presharedKey = "0000000000000000000000000000000000000000000000000000000000000000";
		allowedIP = "0.0.0.0/0";
		replaceAllowedIP = true;
		persistentKeepaliveInterval = 5;
	}
	
	public PeerModel(String endpoint, String publicKey, String presharedKey, String allowedIP, boolean replaceAllowedIP, int persistentKeepaliveInterval) {
		this.endpoint = endpoint;
		this.publicKey = publicKey;
		this.presharedKey = presharedKey;
		this.allowedIP = allowedIP;
		this.replaceAllowedIP = replaceAllowedIP;
		this.persistentKeepaliveInterval = persistentKeepaliveInterval;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof PeerModel && ((PeerModel) obj).getEndpoint().equals(getEndpoint()) && ((PeerModel) obj).getPublicKey().equals(getPublicKey()) && ((PeerModel) obj).getPresharedKey().equals(getPresharedKey()) && ((PeerModel) obj).getAllowedIP().equals(getAllowedIP()) && ((PeerModel) obj).isReplaceAllowedIP() == isReplaceAllowedIP() && ((PeerModel) obj).getPersistentKeepaliveInterval() == getPersistentKeepaliveInterval();
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public String getPresharedKey() {
		return presharedKey;
	}
	
	public void setPresharedKey(String presharedKey) {
		this.presharedKey = presharedKey;
	}
	
	public String getAllowedIP() {
		return allowedIP;
	}
	
	public void setAllowedIP(String allowedIP) {
		this.allowedIP = allowedIP;
	}
	
	public boolean isReplaceAllowedIP() {
		return replaceAllowedIP;
	}
	
	public void setReplaceAllowedIP(boolean replaceAllowedIP) {
		this.replaceAllowedIP = replaceAllowedIP;
	}
	
	public int getPersistentKeepaliveInterval() {
		return persistentKeepaliveInterval;
	}
	
	public void setPersistentKeepaliveInterval(int persistentKeepaliveInterval) {
		this.persistentKeepaliveInterval = persistentKeepaliveInterval;
	}
	
	public String getPublicKeyAsHex() {
		return Hex.encodeHexString(Base64.decodeBase64(getPublicKey()));
	}
	
}
