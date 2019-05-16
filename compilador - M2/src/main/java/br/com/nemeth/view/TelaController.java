package br.com.nemeth.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import br.com.nemeth.gals.LexicalError;
import br.com.nemeth.gals.Lexico;
import br.com.nemeth.gals.SemanticError;
import br.com.nemeth.gals.Semantico;
import br.com.nemeth.gals.Sintatico;
import br.com.nemeth.gals.Symbol;
import br.com.nemeth.gals.SyntaticError;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaController {

	@FXML
	private TextArea textLog;

	@FXML
	private Button buttonCompilar;

	@FXML
	private Button buttonTabela;
	@FXML
	private Label labelStatus;

	@FXML
	private Text textStatus;

	@FXML
	private TextArea textInput;

	@FXML
	private TextArea textOutput;

	@FXML
	private TableView<Symbol> tabVariaveis;

	@FXML
	private TableColumn<Symbol, String> tabId;

	@FXML
	private TableColumn<Symbol, String> tabTipo;

	@FXML
	private TableColumn<Symbol, String> tabIni;

	@FXML
	private TableColumn<Symbol, String> tabUsada;

	@FXML
	private TableColumn<Symbol, String> tabEscopo;

	@FXML
	private TableColumn<Symbol, String> tabParam;

	@FXML
	private TableColumn<Symbol, String> tabPos;

	@FXML
	private TableColumn<Symbol, String> tabVet;

	@FXML
	private TableColumn<Symbol, String> tabFunc;

	private List<Symbol> variaveis = new ArrayList<Symbol>();

	@FXML
	public void initialize() {

		tabId.setCellValueFactory(new PropertyValueFactory<Symbol, String>("id"));
		tabTipo.setCellValueFactory(new PropertyValueFactory<Symbol, String>("tipo"));
		tabIni.setCellValueFactory(new PropertyValueFactory<Symbol, String>("ini"));
		tabUsada.setCellValueFactory(new PropertyValueFactory<Symbol, String>("usada"));
		tabEscopo.setCellValueFactory(new PropertyValueFactory<Symbol, String>("escopo"));
		tabParam.setCellValueFactory(new PropertyValueFactory<Symbol, String>("param"));
		tabPos.setCellValueFactory(new PropertyValueFactory<Symbol, String>("pos"));
		tabVet.setCellValueFactory(new PropertyValueFactory<Symbol, String>("vet"));
		tabFunc.setCellValueFactory(new PropertyValueFactory<Symbol, String>("func"));

		tabVariaveis.setEditable(false);
	}

	/*
	 * A��o ao pressionar o bot�o Compilar
	 */
	@FXML
	void compilar(ActionEvent event) {
		executar(textInput.getText());
	}

	private void executar(String texto) {
		variaveis.clear();
		tabVariaveis.setItems(FXCollections.observableArrayList(variaveis));
		System.out.println(texto);
		textLog.setText("");
		textOutput.setText("");
		Lexico lex = new Lexico();
		Sintatico sin = new Sintatico();
		Semantico sem = new Semantico(this);

		lex.setInput(texto);

		try {
			sin.parse(lex, sem);
			textLog.setText("Compilado com sucesso");
			textStatus.setText("OK");
		} catch (LexicalError e) {
			textLog.setText(textLog.getText() + "\n" + e.getMessage());
			textStatus.setText("Encontrou Erros");
			System.out.println(e.getMessage());
		} catch (SyntaticError e) {
			textLog.setText(textLog.getText() + "\n" + e.getMessage());
			textStatus.setText("Encontrou Erros");
			System.out.println(e.getMessage());
		} catch (SemanticError e) {
			textLog.setText(textLog.getText() + "\n" + e.getMessage());
			textStatus.setText("Encontrou Erros");
			System.out.println(e.getMessage());
		}

	}

	public String getInput() {
		return textInput.getText();
	}

	public void setOutput(String output) {
		textOutput.setText(output);
	}

	public void addLinhaLog(String linha) {
		textLog.setText(textLog.getText() + "\n" + linha);
	}

	public void reescreverLog(String log) {
		textLog.setText(log);
	}

	public void limpaOutput() {
		textOutput.setText("");
	}

	public void limpaLog() {
		textLog.setText("");
	}

	public void limpaStatus() {
		textStatus.setText("");
	}

	public void alteraStatus(String status) {
		textStatus.setText(status);
	}

	public void ler() throws IOException {
		JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int i = file.showSaveDialog(null);
		if (i == 1) {

			// executar(texto);
		} else {
			File arquivo = file.getSelectedFile();
			InputStream is = new FileInputStream(arquivo);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			executar(sb.toString());
			buf.close();
		}

//		loader.setLocation(Compilador.class.getResource("view/tabela/tabela.fxml"));
	}

	@FXML
	void abrirTabela(ActionEvent event) throws Exception {

//		Parent root = FXMLLoader.load(TelaController.class.getResource("view/tabela/tabela.fxml"));

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tabela/tabela.fxml"));
		System.out.println(fxmlLoader.getLocation().getPath());
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));
		stage.getScene().getStylesheets()
				.add(getClass().getClassLoader().getResource("css/bootstrap.css").toExternalForm());
		stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/icone.png")));
		stage.setResizable(false);
		stage.show();
	}

	public void inserirToken(String token) {
		try {
			textOutput.setText(textOutput.getText() + "\n" + token);
		} catch (Exception e) {
			System.out.println("null");
		}
	}

	public void adicionarSymbol(Symbol s, String escopo) {
		boolean achou = false;
		for (int i = 0; i < variaveis.size(); i++) {
			if (variaveis.get(i).getId().equals(s.getId()) && variaveis.get(i).getEscopo().equals(escopo)) {
				achou = true;
				variaveis.set(i, s);
			}
		}
		
		if (!achou) {
			variaveis.add(s);
		}
		
		tabVariaveis.setItems(FXCollections.observableArrayList(variaveis));
	}

}
