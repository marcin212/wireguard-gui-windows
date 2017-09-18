package com.bymarcin.wireguardgui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.bymarcin.wireguardgui.ResourceManager;
import com.bymarcin.wireguardgui.model.PeerModel;
import com.bymarcin.wireguardgui.service.ConfigProvider;
import com.bymarcin.wireguardgui.utils.NetworkInterfaceUtils;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WireguardController implements Initializable {
	@FXML
	JFXTextArea publicKey;
	@FXML
	JFXTextField listenPort;
	@FXML
	JFXListView peerList;
	@FXML
	JFXComboBox networkInterface;
	@FXML
	Pane mainPane;
	@FXML
	JFXCheckBox replacePeers;
	@FXML
	JFXTextField ip;
	
	PeerConfigurationController peerConfigurationController;
	Scene peerPane;
	
	@FXML
	void addPeer() {
		Stage stage = new Stage();
		stage.setTitle("Peer Configuration");
		stage.setResizable(false);
		stage.setScene(peerPane);
		stage.initModality(Modality.APPLICATION_MODAL);
		peerConfigurationController.loadModel(new PeerModel());
		stage.showAndWait();
		if (peerConfigurationController.getItem() != null) {
			Peer p = new Peer(peerConfigurationController.getItem());
			peerList.getItems().add(p);
		}
	}
	
	@FXML
	void removePeer() {
		ObservableList<Peer> peers = peerList.getSelectionModel().getSelectedItems();
		peerList.getItems().removeAll(peers);
	}
	
	@FXML
	void Cancel() {
		mainPane.getScene().getWindow().hide();
	}
	
	@FXML
	void Ok() {
		ConfigProvider.getInstance().getConfig().setInterfaceName(networkInterface.getValue().toString());
		ConfigProvider.getInstance().getConfig().setListenPort(Integer.parseInt(listenPort.getText()));
		ConfigProvider.getInstance().getConfig().setPeers((List<PeerModel>) peerList.getItems().stream().map(e -> ((Peer) e).model).collect(Collectors.toList()));
		ConfigProvider.getInstance().getConfig().setReplacePeers(replacePeers.isSelected());
		ConfigProvider.getInstance().save();
		mainPane.getScene().getWindow().hide();
	}
	
	@FXML
	void edit(MouseEvent actionEvent) {
		if (actionEvent.getClickCount() < 2) {
			return;
		}
		Peer peer = (Peer) peerList.getSelectionModel().getSelectedItem();
		if (peer == null) {
			return;
		}
		peerConfigurationController.loadModel(peer.model);
		addPeer();
		if (peerConfigurationController.getItem() != null) {
			peerList.getItems().remove(peer);
		}
	}
	
	@FXML
	void setIP() {
		String iptext = ip.getText();
		String[] ipParts = iptext.split("/");
		if (ipParts.length == 2) {
			NetworkInterfaceUtils.setIP(ipParts[0], NetworkInterfaceUtils.convertMask(ipParts[1]), networkInterface.getSelectionModel().getSelectedItem().toString());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FXMLLoader loader = new FXMLLoader(ResourceManager.configGui);
			peerPane = new Scene(loader.load());
			peerConfigurationController = loader.getController();
			networkInterface.getItems().addAll(NetworkInterfaceUtils.getInterfacesName());
			networkInterface.getSelectionModel().select(ConfigProvider.getInstance().getConfig().getInterfaceName());
			listenPort.setText(String.valueOf(ConfigProvider.getInstance().getConfig().getListenPort()));
			try {
				publicKey.setText(Base64.encodeBase64String(Hex.decodeHex(ConfigProvider.getInstance().getConfig().getPublicKey().toCharArray())));
			} catch (DecoderException e) {
				e.printStackTrace();
			}
			peerList.getItems().addAll(ConfigProvider.getInstance().getConfig().getPeers().stream().map(Peer::new).collect(Collectors.toList()));
			replacePeers.setSelected(ConfigProvider.getInstance().getConfig().isReplacePeers());
			ip.setText(NetworkInterfaceUtils.getIP(ConfigProvider.getInstance().getConfig().getInterfaceName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Peer {
		PeerModel model;
		
		public Peer(PeerModel model) {
			this.model = model;
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof Peer && ((Peer) obj).model.equals(model);
		}
		
		public String getEndpoint() {
			return model.getEndpoint();
		}
		
		public String getPublicKey() {
			return model.getPublicKey();
		}
		
		@Override
		public String toString() {
			return String.format("%s\t\t\t%s", getEndpoint(), getPublicKey());
		}
	}
}
