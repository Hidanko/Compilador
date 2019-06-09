package br.com.nemeth.gals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.com.nemeth.view.TelaController;

public class Semantico implements Constants {
	List<Symbol> tabela;
	String tipo;
	Stack<String> escopos;
	int param;
	int scopeCounter;
	TelaController tela;
	TipoContexto tipoContexto;
    String asb_data = ".data" + "\n";
    String asb_text = ".text \n";
    
	public Semantico(TelaController tela) {
		this.param = 0;
		this.scopeCounter = 0;
		this.tabela = new ArrayList<>();
		this.escopos = new Stack<>();
		escopos.push("global");
		this.tela = tela;
	}
    public String getAssembly(List<Symbol> TabelaSimbolos) {
        for (Symbol T : TabelaSimbolos) {
            if (!T.isFunc()) {
                if (T.isVet()) {
                    asb_data += "\t" + T.getId() + " : 0";
                    for (int i = 0; i < T.getTamanho() - 1; i++) {
                        asb_data += ",0";
                    }
                    asb_data += "\n";
                } else {
                    asb_data += "\t" + T.getId() + " : 0\n";
                }

            }
        }
        asb_data += "\n";
        asb_text += "\tHLT\t0";
        return(asb_data + asb_text);

    }

    public void gera_cod(String a, String b) {
        asb_text += "\t" + a + "\t" + b + "\n";
    }
	

	public void executeAction(int action, Token token) throws SemanticError {
		switch (action) {
		/**
		 * case 1 até 8 trata do escopo e da inserção dos simbolos nas tabelas
		 */
		// pega o tipo da vari�vel/vetor/fun��o/parametro na declara��o para logo ap�s
		// inserir
		case 1:
			tipo = token.getLexeme();
			tipoContexto = TipoContexto.FUNCAO;
			break;
		case 2: // insere uma vari�vel na tabela
			if (escopos.size() > 1) {
				if (!inserirTabela(
						new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, false, false)))
					tipoContexto = TipoContexto.INICIALIZACAO;
				dizerSeFoiInstanciado(token);
				// tela.inserirToken("vari�vel " + token.getLexeme() + " ja existe");
			} else
				tela.inserirToken("vari�vel " + token.getLexeme() + " n�o pode ser declarada fora de fun��o");

			break;
		case 3: // insere um vetor na tabela
			if (escopos.size() > 1) {
				if (!inserirTabela(
						new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, true, false)))
					tipoContexto = TipoContexto.INICIALIZACAO;
				dizerSeFoiInstanciado(token);
				// tela.inserirToken("vetor " + token.getLexeme() + " j� existe");
			} else
				tela.inserirToken("vetor " + token.getLexeme() + " n�o pode ser declarada fora de fun��o");
			break;
		case 4: // insere uma fun��o na tabela e muda o escopo atual caso a inser��o tenha
				// sucesso;
			if (escopos.peek().equals("global")) {
				if (inserirTabela(
						new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, false, true))) {
					escopos.push(token.getLexeme());
					// tela.inserirToken("funcao " + token.getLexeme() + " foi inserida com
					// sucesso");
				} else
					tela.inserirToken("funcao " + token.getLexeme() + " j� existe");
			} else
				tela.inserirToken("fun��o s� pode ser declarada em escopo global");
			break;

		// inseri um parametro de fun��o na tabela obs: adicionei <type> antes da fun��o
		case 5: // e mudei a regra de produ��o <param> na gramatica para facilitar a inser��o na
				// tabela;
			if (!inserirTabela(
					new Symbol(token.getLexeme(), tipo, true, false, escopos.peek(), true, ++param, false, false)))
				// tela.inserirToken("parametro " + token.getLexeme() + " foi inserido com
				// sucesso");
				// else
				tela.inserirToken("parametro " + token.getLexeme() + " j� existe");
			break;
		case 6: // quando fechar os parenteses dos parametros da função, zera o contador da
				// posição do parametro
			dizerSeFoiInstanciado(token);
			param = 0;
			break;
		case 7: // quando fecha chaves, atualiza o escopo;
			for (Symbol simbolo : tabela) {
				if (!simbolo.isUsada() && simbolo.getEscopo().equals(escopos.peek())
						&& !simbolo.getId().equals("main")) {
					tela.inserirToken("WARN - " + simbolo.getId() + " nao usado");
				}
			}

			escopos.pop();
			break;
		case 8: // muda o escopo para desvio condicional e laços de repetição
				// adicionei o scope counter pra diferenciar o nome, porque no caso de existirem
				// desvios condicionais ou
				// laços de repetição aninhados, o nome do escopo não muda;
			if (escopos.size() > 1)
				escopos.push("scope " + scopeCounter++);
			else
				escopos.push("");
			tela.inserirToken(token.getLexeme() + " n�o pode ser usado fora de fun��o");
			break;

		/**
		 * 
		 */
		case 9: // verifica se uma variável sendo utilizada já foi declarada, e seta ela como
				// inicializada;
			if (escopos.size() > 1) {
				if (!buscaIdEscopo(action, token.getLexeme()))
					// tela.inserirToken("var " + token.getLexeme() + " existe e foi setada como
					// inicializada");
					// else
					tela.inserirToken("Aviso! vari�vel " + token.getLexeme() + " n�o foi declarada");
			} else
				tela.inserirToken("express�es e atribui��es n�o podem ocorrer fora de fun��o");

			break;
		case 10: // verifica se uma variável sendo utilizada já foi declarada e seta ela como
					// usada;
			if (escopos.size() > 1) {
				if (!buscaIdEscopo(action, token.getLexeme()))

					// tela.inserirToken("var " + token.getLexeme() + " existe e foi setada como
					// usada");
					// else
					tela.inserirToken("Aviso! vari�vel " + token.getLexeme() + " n�o foi declarada");
			} else
				tela.inserirToken("express�es e atribui��es n�o podem ocorrer fora de fun��o");

			break;
		case 11: // checa se uma fun��o sendo utilizada j� foi declarada;
			if (!buscaIdEscopo(action, token.getLexeme()))
				// tela.inserirToken("Fun��o " + token.getLexeme() + " existe e foi setada como
				// usada");
				// else
				tela.inserirToken("Aviso! fun��o " + token.getLexeme() + " n�o foi declarada");
			break;

		case 15:
			inserirAssembly();
			// fecha contexto
			break;
		case 16:
			tipoContexto = TipoContexto.LEITURA;
			break;
		case 17:
			tipoContexto = TipoContexto.ESCRITA;
			// cout
			break;
		}
		for (Symbol simbolo : tabela) {
			simbolo.printSymbol();
		}
		System.out.println("\n\n");
	}

	private void inserirAssembly() {
		switch (tipoContexto) {
		case INICIALIZACAO:

			break;

		default:
			break;
		}
	}

	/**
	 * checa unicidade de variável, levando em conta o escopo fiz de maneira que
	 * funções podem ter o mesmo nome de variáveis mas as variáveis, os vetores e os
	 * parâmetros não podem ter o mesmo nome
	 */
	public boolean inserirTabela(Symbol novoSimbolo) {
		boolean flag = false;

		for (String escopo : escopos) {
			// tela.inserirToken("escopo sendo checado: "+escopo);
			for (Symbol simbolo : tabela) {
				if (simbolo.getId().equals(novoSimbolo.getId()) && simbolo.isFunc() == novoSimbolo.isFunc()
						&& simbolo.getEscopo().equals(escopo)) {
					flag = true;
				}
			}
		}

		if (!flag) {
			tabela.add(novoSimbolo);
			// novoSimbolo.printSymbol();
			tela.adicionarSymbol(novoSimbolo, escopos.peek());

			return true;
		} else {
			return false;
		}
	}

	// verifica se uma variável ou função foi declarada antes de ser usada;
	// Action serve como um filtro para funções e variáveis
	public boolean buscaIdEscopo(int action, String id) {

		for (String escopo : escopos) {
			for (Symbol simbolo : tabela) {
				// checagem para variáveis
				if ((action == 9 || action == 10) && simbolo.getId().equals(id) && simbolo.isFunc() == false
						&& simbolo.getEscopo().equals(escopo)) {

					if (action == 9)
						simbolo.setIni(true);
					else if (action == 10)
						simbolo.setUsada(true);
					if (simbolo.isIni() != true)
						tela.inserirToken(id + " esta sendo utilizado sem ser inicializado");

					return true;

				} else // checagem para função
				if (action == 11 && simbolo.getId().equals(id) && simbolo.isFunc() == true
						&& simbolo.getEscopo().equals(escopo)) {
					if (id.equals(escopos.peek()))
						return false;
					simbolo.setUsada(true);
					return true;
				}
			}
		}
		return false;
	}

	private void dizerSeFoiInstanciado(Token token) {
		boolean flag = true;
		for (Symbol s : tabela) {
			if (token.getLexeme() == s.getId() && s.getEscopo().equals(escopos.peek())) {
				flag = false;
			}
		}
		if (flag) {
			System.out.println(token.getLexeme() + " utilizado sem estar instanciado");
		}

	}
}