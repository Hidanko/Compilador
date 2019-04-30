package br.com.nemeth.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

import br.com.nemeth.gals.LexicalError;
import br.com.nemeth.gals.Lexico;
import br.com.nemeth.gals.SemanticError;
import br.com.nemeth.gals.Semantico;
import br.com.nemeth.gals.Sintatico;
import br.com.nemeth.gals.SyntaticError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TelaController {

	@FXML
	private TextArea textLog;

	@FXML
	private Button buttonCompilar;

	@FXML
	private Label labelStatus;

	@FXML
	private Text textStatus;

	@FXML
	private TextArea textInput;

	@FXML
	private TextArea textOutput;

	/*
	 * A��o ao pressionar o bot�o Compilar
	 */
	@FXML
	void compilar(ActionEvent event) {
		executar(textInput.getText());
	}

	private void executar(String texto) {
		System.out.println(texto);
		textLog.setText("");

		Lexico lex = new Lexico();
		Sintatico sin = new Sintatico();
		Semantico sem = new Semantico();

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
		}

	}
}
