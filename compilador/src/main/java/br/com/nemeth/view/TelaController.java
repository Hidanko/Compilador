package br.com.nemeth.view;

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

        Lexico lex = new Lexico();
        Sintatico sin = new Sintatico();
        Semantico sem = new Semantico();
        
        lex.setInput(textInput.getText());
        
        try{
            sin.parse(lex, sem);
        }
        catch (LexicalError e) {
            System.out.println(e.getMessage());
        }
        catch (SyntaticError e) {
            System.out.println(e.getMessage());
        }
        catch (SemanticError e) {
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

}
