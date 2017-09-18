package com.bymarcin.wireguardgui.view;

import javafx.stage.Stage;

public interface ITrayWindow<T> {
	void show(Stage stage);
	T controller();
}
