package br.com.nemeth;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Compilador extends Application {
	private static GridPane janelaPrincipal;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Compilador.class.getResource("view/tela.fxml"));

		try {
			janelaPrincipal = loader.<GridPane>load();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Falha ao carregar interface da aplica��o");
		}
		Scene scene = new Scene(janelaPrincipal);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("css/bootstrap.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Compilador");
		stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/icone.png")));
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.setResizable(false);
		stage.show();

	}
}
