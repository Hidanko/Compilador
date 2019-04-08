package br.com.nemeth;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Compilador extends Application {
	private static GridPane janelaPrincipal;
	private static Logger logger = LoggerFactory.getLogger(Compilador.class);

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
			logger.error("Falha ao carregar JavaFX", e1);
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
