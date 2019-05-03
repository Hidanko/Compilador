package br.com.nemeth.view.tabela;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Tabela {

	@FXML
	private TableView<TabelaLinha> tabela;

	@FXML
	private TableColumn<TabelaLinha, String> tabX;

	@FXML
	private TableColumn<TabelaLinha, String> tabInt;

	@FXML
	private TableColumn<TabelaLinha, String> tabFloat;

	@FXML
	private TableColumn<TabelaLinha, String> tabChar;

	@FXML
	private TableColumn<TabelaLinha, String> tabString;

	@FXML
	private TableColumn<TabelaLinha, String> tabBoolean;

//	  static int atribTable [][]={/* INT FLO CHA STR BOO  */
//              /*INT*/ {OK_,WAR,ERR,ERR,ERR},
//              /*FLO*/ {OK_,OK_,ERR,ERR,ERR},
//              /*CHA*/ {ERR,ERR,OK_,ERR,WAR},
//              /*STR*/ {ERR,ERR,ERR, OK_, ERR},
//              /*BOO*/ {ERR,ERR,ERR,ERR,OK_}
//                   };

//	private String nome;
//	private String inT;
//	private String flo;
//	private String cha;
//	private String string;
//	private String boo;

	@FXML
	void initialize() {
		System.out.println("Iniciando");

		tabX.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("nome"));
		tabX.setStyle("font-weight: bold;");
		tabInt.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("inT"));
		tabFloat.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("flo"));
		tabString.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("string"));
		tabChar.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("cha"));
		tabBoolean.setCellValueFactory(new PropertyValueFactory<TabelaLinha, String>("boo"));
		
		
		List<TabelaLinha> tab = new ArrayList<TabelaLinha>();
		tab.add(new TabelaLinha("INT", "OK", "WARNING", "ERROR", "ERROR", "ERROR"));
		tab.add(new TabelaLinha("FLOAT", "OK", "OK", "ERROR", "ERROR", "ERROR"));
		tab.add(new TabelaLinha("CHAR", "ERROR", "ERROR", "OK", "ERROR", "WARNING"));
		tab.add(new TabelaLinha("STRING", "ERROR", "ERROR", "ERROR", "OK", "ERROR"));
		tab.add(new TabelaLinha("BOOLEAN", "ERROR", "ERROR", "ERROR", "ERROR", "OK"));

		tabela.setEditable(false);
		tabela.setItems(FXCollections.observableArrayList(tab));
		System.out.println(tabela.getItems().size());
	}
}
