/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executargramatica;

import java.awt.TextArea;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

/**
 *
 * @author noschang
 */
public class TratadorErrosSintatico implements ANTLRErrorListener {
	private final javafx.scene.control.TextArea modelo;

	public TratadorErrosSintatico(javafx.scene.control.TextArea modeloLista) {
		this.modelo = modeloLista;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		Parser parser = (Parser) recognizer;

		List<String> stack = (parser).getRuleInvocationStack();
		Collections.reverse(stack);

		String mensagem = "Erro sintatico na linha " + line + ", coluna " + charPositionInLine + ": Regras: " + stack
				+ ", Símbolo: " + offendingSymbol + ": " + msg;
		modelo.setText(modelo.getText() + '\n' +  mensagem);
	}

	@Override
	public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {

	}

	@Override
	public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {

	}

	@Override
	public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {

	}
}
