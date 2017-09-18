package com.bymarcin.wireguardgui.service;

import java.io.Console;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import com.bymarcin.wireguardgui.model.PeerModel;
import com.bymarcin.wireguardgui.model.WGConfig;
import com.bymarcin.wireguardgui.utils.ProcessIO;

public class WGProcess {
	Process wg;
	ProcessIO io = new ProcessIO();
	private static final WGProcess wgProcess = new WGProcess();
	
	public static WGProcess getInstance() {
		return wgProcess;
	}
	
	private WGProcess(){
	
	}
	
	public void start(WGConfig config) {
		ProcessBuilder pb = new ProcessBuilder("wg.exe", "-f", config.getInterfaceName());
		try {
			wg = pb.start();
			io.inheritIO(wg, ConsoleLogger.getInstance()::append, ConsoleLogger.getInstance()::append);
			Thread.sleep(1500);
			ConsoleLogger.getInstance().append("load config");
			ConsoleLogger.getInstance().append("errno=" + loadConfig(config));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitFor() throws InterruptedException {
		if (wg != null) {
			wg.waitFor();
		}
	}
	
	public void stop() {
		if(wg==null) return;
		try {
			wg.destroy();
			wg.waitFor(5, TimeUnit.SECONDS);
			if (wg.isAlive()) {
				wg.destroyForcibly().waitFor();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int loadConfig(WGConfig config) {
		if (wg == null || !wg.isAlive()) {
			return -1;
		}
		try {
			RandomAccessFile pipe = new RandomAccessFile(String.format("\\\\.\\pipe\\wireguard-ipc-%s", config.getInterfaceName()), "rw");
			StringBuilder sb = new StringBuilder();
			sb.append("set=1\n");
			sb.append("private_key=").append(config.getPrivateKey()).append("\n");
			sb.append("listen_port=").append(config.getListenPort()).append("\n");
			sb.append("replace_peers=").append(config.isReplacePeers()).append("\n");
			for (PeerModel peer : config.getPeers()) {
				sb.append("public_key=").append(peer.getPublicKeyAsHex()).append("\n");
				sb.append("preshared_key=").append(peer.getPresharedKey()).append("\n");
				sb.append("replace_allowed_ips=").append(peer.isReplaceAllowedIP()).append("\n");
				sb.append("allowed_ip=").append(peer.getAllowedIP()).append("\n");
				sb.append("endpoint=").append(peer.getEndpoint()).append("\n");
				sb.append("persistent_keepalive_interval=").append(peer.getPersistentKeepaliveInterval()).append("\n");
			}
			sb.append("\n");
			sb.append("\n");
			pipe.write(sb.toString().getBytes());
			return parseResponse(pipe.readLine());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private int parseResponse(String response) {
		String[] part = response.split("=");
		if (part.length == 2 && part[0].equals("errno")) {
			try {
				return Integer.parseInt(part[1]);
			} catch (NumberFormatException ignored) {
			}
		}
		return -1;
	}
	
}
