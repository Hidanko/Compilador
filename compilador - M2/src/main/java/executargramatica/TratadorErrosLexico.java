package executargramatica;

import java.awt.TextArea;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

/**
 *
 * @author noschang
 */
public class TratadorErrosLexico implements ANTLRErrorListener
{
    private final javafx.scene.control.TextArea modelo;

    public TratadorErrosLexico(javafx.scene.control.TextArea modeloLista)
    {
        this.modelo = modeloLista;
    }
    
    
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        Lexer lexer = (Lexer) recognizer;
        String mensagem = "Erro sintatico na linha " + line + ", coluna " + charPositionInLine + ": " + msg;
        
    	modelo.setText(modelo.getText() + '\n' + mensagem);
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs)
    {

    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs)
    {

    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs)
    {

    }    
}
