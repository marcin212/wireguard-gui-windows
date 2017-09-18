package com.bymarcin.wireguardgui.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ProcessIO {
	
	public void inheritIO(Process p, Consumer<String> outReader, Consumer<String> errReader) throws IOException {
		readError(p, errReader);
		readOutput(p, outReader);
	}
	
	public Thread reader(InputStream inputStream, Consumer<String> reader){
		Thread t = new Thread(() -> {
			try {
				BufferedReader errorStream = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = errorStream.readLine()) != null) {
					reader.accept(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		t.start();
		return t;
	}
	
	void readError(Process p, Consumer<String> reader) throws IOException {
		new Thread(() -> {
			try {
				BufferedReader errorStream = error(p);
				String line = null;
				while ((line = errorStream.readLine()) != null) {
					reader.accept(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	void readOutput(Process p, Consumer<String> reader) throws IOException {
		new Thread(() -> {
			try {
				BufferedReader outputStream = output(p);
				String line = null;
				while ((line = outputStream.readLine()) != null) {
					reader.accept(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	BufferedReader output(Process process) {
		return new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
	
	BufferedReader error(Process process) {
		return new BufferedReader(new InputStreamReader(process.getErrorStream()));
	}
	
}
