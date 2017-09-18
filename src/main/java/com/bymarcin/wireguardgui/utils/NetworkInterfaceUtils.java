package com.bymarcin.wireguardgui.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.WindowsRegistry;
import com.sun.jna.platform.win32.Shell32;

public class NetworkInterfaceUtils {
	private static final String branch = "SYSTEM\\CurrentControlSet\\Control\\Network\\{4D36E972-E325-11CE-BFC1-08002BE10318}";
	private static final Pattern ip = Pattern.compile("IP Address:\\s+(?<ip>\\d+\\.\\d+\\.\\d+\\.\\d+)");
	private static final Pattern mask = Pattern.compile("Subnet Prefix:\\s+\\d+\\.\\d+\\.\\d+\\.\\d+\\/(?<mask>\\d+)");
	
	public static void setIP(String IP, String mask, String name) {
		String[] args = new String[]{"interface", "ip", "set", "address", "name=\"" + name + "\"", "source=static", "addr=" + IP, "mask=" + mask, "gateway=none"};
		String arg = Arrays.stream(args).collect(Collectors.joining(" "));
		Shell32.INSTANCE.ShellExecute(null, "runas", "netsh.exe", arg, null, 0);
	}
	
	public static String getIP(String interfaceName){
		String args2 = "netsh interface ip show config name=\"" + interfaceName + "\"";
		try {
			Process p = Runtime.getRuntime().exec(args2);
			ProcessIO io = new ProcessIO();
			StringBuilder sb = new StringBuilder();
			io.reader(p.getInputStream(),sb::append).join();
			p.waitFor();
			Matcher ipm = ip.matcher(sb.toString());
			Matcher maskm = mask.matcher(sb.toString());
			if(ipm.find() && maskm.find()){
				return ipm.group("ip") + "/" + maskm.group("mask");
			}else{
				return "";
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String convertMask(String num){
		int masknum = Integer.parseInt(num);
		long mask=0;
		
		for (int i = 0; i < masknum; i++) {
			mask =  mask | (1<<(31-i));
		}
		
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < 4; i++) {
			long part = ((mask>>((3-i)*8)) & 255);
			sb.append(part);
			if(i<3) {
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public static List<String> getInterfacesName() {
		
		WindowsRegistry reg = WindowsRegistry.getInstance();
		try {
			List<String> result = new LinkedList<>();
			List<String> keys = reg.readStringSubKeys(HKey.HKLM, branch);
			for (String key : keys) {
				String iBranch = String.format("%s\\%s\\Connection", branch, key);
				String name;
				try {
					name = reg.readString(HKey.HKLM, iBranch, "Name");
				} catch (Exception e) {
					name = null;
				}
				if (name != null) {
					result.add(name);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	private static final Pattern ipv4Pattern = Pattern.compile("^(?:(?:\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(?:\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])((:[0-5]?[0-9]{1,4})|:(6[0-5][0-5][0-3][0-5]))?$");
	
	public static boolean validateIP(String ipString) {
		if (ipString == null) {
			return false;
		}
		Matcher matcher = ipv4Pattern.matcher(ipString.trim());
		return matcher.find();
	}
}
