//// Generated from /home/noschang/NetBeansProjects/ExecutarGramatica/src/executargramatica/Linguagem.g4 by ANTLR 4.1
//package executargramatica;
//import org.antlr.v4.runtime.atn.*;
//import org.antlr.v4.runtime.dfa.DFA;
//import org.antlr.v4.runtime.*;
//import org.antlr.v4.runtime.misc.*;
//import org.antlr.v4.runtime.tree.*;
//import java.util.List;
//import java.util.Iterator;
//import java.util.ArrayList;
//
//@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
//public class LinguagemParser extends Parser {
//	protected static final DFA[] _decisionToDFA;
//	protected static final PredictionContextCache _sharedContextCache =
//		new PredictionContextCache();
////	public static final int
////		OPERADOR=1, INTEIRO=2, NL=3, ESPACO=4;
////	public static final String[] tokenNames = {
////		"<INVALID>", "OPERADOR", "INTEIRO", "NL", "ESPACO"
////	};
////	public static final int
////		RULE_parse = 0, RULE_inicio = 1, RULE_soma = 2;
////	public static final String[] ruleNames = {
////		"parse", "inicio", "soma"
////	};
//
//	@Override
//	public String getGrammarFileName() { return "Linguagem.g4"; }
//
////	@Override
////	public String[] getTokenNames() { return tokenNames; }
//
////	@Override
////	public String[] getRuleNames() { return ruleNames; }
//
//	@Override
//	public ATN getATN() { return _ATN; }
//
//	public LinguagemParser(TokenStream input) {
//		super(input);
//		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
//	}
//	public static class ParseContext extends ParserRuleContext {
//		public InicioContext inicio() {
//			return getRuleContext(InicioContext.class,0);
//		}
//		public ParseContext(ParserRuleContext parent, int invokingState) {
//			super(parent, invokingState);
//		}
////		@Override public int getRuleIndex() { return RULE_parse; }
//		@Override
//		public void enterRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).enterParse(this);
//		}
//		@Override
//		public void exitRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).exitParse(this);
//		}
//	}
//
//	public final ParseContext parse() throws RecognitionException {
//		ParseContext _localctx = new ParseContext(_ctx, getState());
////		enterRule(_localctx, 0, RULE_parse);
//		try {
//			enterOuterAlt(_localctx, 1);
//			{
//			setState(6); inicio();
//			}
//		}
//		catch (RecognitionException re) {
//			_localctx.exception = re;
//			_errHandler.reportError(this, re);
//			_errHandler.recover(this, re);
//		}
//		finally {
//			exitRule();
//		}
//		return _localctx;
//	}
//
//	public static class InicioContext extends ParserRuleContext {
//		public List<TerminalNode> NL() { return getTokens(LinguagemParser.NL); }
//		public List<SomaContext> soma() {
//			return getRuleContexts(SomaContext.class);
//		}
//		public TerminalNode NL(int i) {
//			return getToken(LinguagemParser.NL, i);
//		}
//		public SomaContext soma(int i) {
//			return getRuleContext(SomaContext.class,i);
//		}
//		public InicioContext(ParserRuleContext parent, int invokingState) {
//			super(parent, invokingState);
//		}
//		@Override public int getRuleIndex() { return RULE_inicio; }
//		@Override
//		public void enterRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).enterInicio(this);
//		}
//		@Override
//		public void exitRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).exitInicio(this);
//		}
//	}
//
//	public final InicioContext inicio() throws RecognitionException {
//		InicioContext _localctx = new InicioContext(_ctx, getState());
//		enterRule(_localctx, 2, RULE_inicio);
//		int _la;
//		try {
//			enterOuterAlt(_localctx, 1);
//			{
//			setState(13);
//			_errHandler.sync(this);
//			_la = _input.LA(1);
//			while (_la==INTEIRO) {
//				{
//				{
//				setState(8); soma();
//				setState(9); match(NL);
//				}
//				}
//				setState(15);
//				_errHandler.sync(this);
//				_la = _input.LA(1);
//			}
//			}
//		}
//		catch (RecognitionException re) {
//			_localctx.exception = re;
//			_errHandler.reportError(this, re);
//			_errHandler.recover(this, re);
//		}
//		finally {
//			exitRule();
//		}
//		return _localctx;
//	}
//
//	public static class SomaContext extends ParserRuleContext {
//		public Token INTEIRO;
//		public Token OPERADOR;
//		public TerminalNode OPERADOR(int i) {
//			return getToken(LinguagemParser.OPERADOR, i);
//		}
//		public TerminalNode INTEIRO(int i) {
//			return getToken(LinguagemParser.INTEIRO, i);
//		}
//		public List<TerminalNode> INTEIRO() { return getTokens(LinguagemParser.INTEIRO); }
//		public List<TerminalNode> OPERADOR() { return getTokens(LinguagemParser.OPERADOR); }
//		public SomaContext(ParserRuleContext parent, int invokingState) {
//			super(parent, invokingState);
//		}
//		@Override public int getRuleIndex() { return RULE_soma; }
//		@Override
//		public void enterRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).enterSoma(this);
//		}
//		@Override
//		public void exitRule(ParseTreeListener listener) {
//			if ( listener instanceof LinguagemListener ) ((LinguagemListener)listener).exitSoma(this);
//		}
//	}
//
//	public final SomaContext soma() throws RecognitionException {
//		SomaContext _localctx = new SomaContext(_ctx, getState());
//		enterRule(_localctx, 4, RULE_soma);
//		int _la;
//		try {
//			enterOuterAlt(_localctx, 1);
//			{
//
//			            List<String> dados = new ArrayList<String>();
//			        
//			setState(17); ((SomaContext)_localctx).INTEIRO = match(INTEIRO);
//			 
//			            dados.add((((SomaContext)_localctx).INTEIRO!=null?((SomaContext)_localctx).INTEIRO.getText():null)); 
//			        
//			setState(23); 
//			_errHandler.sync(this);
//			_la = _input.LA(1);
//			do {
//				{
//				{
//				setState(19); ((SomaContext)_localctx).OPERADOR = match(OPERADOR);
//				 
//				                dados.add((((SomaContext)_localctx).OPERADOR!=null?((SomaContext)_localctx).OPERADOR.getText():null)); 
//				            
//				setState(21); ((SomaContext)_localctx).INTEIRO = match(INTEIRO);
//				 
//				                dados.add((((SomaContext)_localctx).INTEIRO!=null?((SomaContext)_localctx).INTEIRO.getText():null)); 
//				            
//				}
//				}
//				setState(25); 
//				_errHandler.sync(this);
//				_la = _input.LA(1);
//			} while ( _la==OPERADOR );
//
//			            Integer resultado = Integer.parseInt(dados.get(0));
//			            
//			            dados.remove(0);
//			            
//			            do
//			            {
//			                String operador = dados.get(0);
//			                int num = Integer.parseInt(dados.get(1));
//			                
//			                if (operador.equals("+"))
//			                {
//			                    resultado = resultado + num;
//			                }                 
//			                else if (operador.equals("*"))
//			                {
//			                    resultado = resultado * num;
//			                }
//			                
//			                dados.remove(0);
//			                dados.remove(0);
//			            }
//			            while (!dados.isEmpty());
//			            
//			            System.out.println("Resultado = " + resultado);
//			        
//			}
//		}
//		catch (RecognitionException re) {
//			_localctx.exception = re;
//			_errHandler.reportError(this, re);
//			_errHandler.recover(this, re);
//		}
//		finally {
//			exitRule();
//		}
//		return _localctx;
//	}
//
//	public static final String _serializedATN =
//		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\6 \4\2\t\2\4\3\t"+
//		"\3\4\4\t\4\3\2\3\2\3\3\3\3\3\3\7\3\16\n\3\f\3\16\3\21\13\3\3\4\3\4\3\4"+
//		"\3\4\3\4\3\4\3\4\6\4\32\n\4\r\4\16\4\33\3\4\3\4\3\4\2\5\2\4\6\2\2\36\2"+
//		"\b\3\2\2\2\4\17\3\2\2\2\6\22\3\2\2\2\b\t\5\4\3\2\t\3\3\2\2\2\n\13\5\6"+
//		"\4\2\13\f\7\5\2\2\f\16\3\2\2\2\r\n\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2"+
//		"\17\20\3\2\2\2\20\5\3\2\2\2\21\17\3\2\2\2\22\23\b\4\1\2\23\24\7\4\2\2"+
//		"\24\31\b\4\1\2\25\26\7\3\2\2\26\27\b\4\1\2\27\30\7\4\2\2\30\32\b\4\1\2"+
//		"\31\25\3\2\2\2\32\33\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\35\3\2\2\2"+
//		"\35\36\b\4\1\2\36\7\3\2\2\2\4\17\33";
//	public static final ATN _ATN =
//		ATNSimulator.deserialize(_serializedATN.toCharArray());
//	static {
//		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
//		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
//			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
//		}
//	}
//}