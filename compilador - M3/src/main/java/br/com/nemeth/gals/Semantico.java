package br.com.nemeth.gals;

import static br.com.nemeth.gals.SemanticTable.ERR;
import static br.com.nemeth.gals.SemanticTable.OK_;
import static br.com.nemeth.gals.SemanticTable.WAR;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import br.com.nemeth.view.TelaController;

public class Semantico implements Constants {
	List<Symbol> tabela;
	Integer tip;
	Stack<String> escopos;
	int param;
	int scopeCounter;
	TelaController tela;
	TipoContexto tipoContexto;
	String asb_data = ".data" + "\n";
	String asb_text = ".text \n";

	int pos = 0, indiceVariavel = 0, idEscopo = 0;
	String temporarios[] = { null, null, null, null };
	boolean flagExp = false, enquanto = false;
	String varIO = "";
	String nome_id_atrib = "";
	String opr = "", oprl = "", numExp = null, idExp = null;
	Stack<Integer> escoposFuncao = new Stack<Integer>();
	Stack expressao = new Stack();
	LinkedList<String> escopoAninhamento = new LinkedList<String>();

	Integer tipo = null, funcaoAtiva = null, idChamFuncao = null;
	boolean vetorDeclaracao = false, vetorParâmetro = false;

	public Semantico(TelaController tela) {
		this.param = 0;
		this.scopeCounter = 0;
		this.tela = tela;
		this.tabela = new ArrayList<>();
		this.escopos = new Stack<>();
		escopos.push("global");
	}

	public String getAssembly() {
		for (Symbol s : tabela) {
			if (!s.isVet()) {
				if (s.isVet()) {
					asb_data += "\t" + s.getId() + " : 0";
					for (int i = 0; i < s.getTamanho() - 1; i++) {
						asb_data += ",0";
					}
					asb_data += "\n";
				} else {
					asb_data += "\t" + s.getId() + " : 0\n";
				}

			}
		}
		asb_data += "\n";
		asb_text += "\tHLT\t0";
		return (asb_data + asb_text);

	}

	public void gera_cod(String a, String b) {
		asb_text += "\t" + a + "\t" + b + "\n";
	}

	public String verify_temp(Boolean alocar, String var) throws SemanticError {
		if (alocar) {
			for (int i = 0; i < temporarios.length; i++) {
				if (temporarios[i] == null) {
					temporarios[i] = var;
					return "temp" + (i + 1);
				}
			}
			throw new SemanticError("Erro ao alocar variavel em registrador temporário");
		} else {
			for (int i = 0; i < temporarios.length; i++) {
				temporarios[i] = null;
			}
			return "";
		}

	}

	public void executacaGeracaoAssembly(int a, String token) throws SemanticError {
		switch (a) {
		case 35:
			gera_cod("LD", "$in_port");
			gera_cod("STO", token);
			break;
		case 36:
			flagExp = true;
			opr = token;
			break;
		case 37:
			if (!flagExp) {
				gera_cod("LD", token);
			} else {
				if ("+".equals(opr)) {
					gera_cod("ADD", token);
				} else if ("-".equals(opr)) {
					gera_cod("SUB", token);
				} else if ("<<".equals(opr)) {
					gera_cod("SLL", token);

				} else if (">>".equals(opr)) {
					gera_cod("SRL", token);
				}
				flagExp = false;
			}
			idExp = token;
			break;
		case 38:
			if (!flagExp) {
				gera_cod("LDI", token);
			} else {
				if ("+".equals(opr)) {
					gera_cod("ADDI", token);
				} else if ("-".equals(opr)) {
					gera_cod("SUBI", token);
				} else if ("<<".equals(opr)) {
					gera_cod("SLL", token);

				} else if (">>".equals(opr)) {
					gera_cod("SRL", token);
				}
				flagExp = false;
			}
			numExp = token;
			break;
		case 39:
			if (numExp != null && idExp == null) {

				gera_cod("STO", verify_temp(true, numExp));
				numExp = null;
			} else {

				gera_cod("STO", verify_temp(true, idExp));
				idExp = null;
			}
			oprl = token;

			break;
		case 40:
			gera_cod("STO", nome_id_atrib);
			nome_id_atrib = null;
			break;
		case 41:
			gera_cod("LD", token);
			gera_cod("STO", "$out_port");
			break;
		case 42:
			gera_cod("LDI", token);
			gera_cod("STO", "$out_port");
			break;
		case 43:
			varIO = token;
			detectaVariavelAtribuicaoDireta(token);
			break;
		case 44:
			gera_cod("LDI", token);
			gera_cod("STO", "$indr");
			gera_cod("LD", "$in_port");
			gera_cod("STOV", varIO);
			break;
		case 45:
			varIO = token;
			break;
		case 46:
			gera_cod("LDI", token);
			gera_cod("STO", "$indr");
			gera_cod("LDV", varIO);
			gera_cod("STO", "$out_port");
			break;
		case 47:
			int temp = 1;
			gera_cod("STO", verify_temp(true, token));
			for (String t : temporarios) {
				if (t != null) {
					gera_cod("LD", "temp" + temp);
					gera_cod("SUB", "temp" + (temp + 1));
					break;
				}
				temp++;
			}
			break;
		case 48:
			if (!enquanto) {
				escopoAninhamento.push(("R" + (escopoAninhamento.size() + 1)));
				if (oprl.equals("<")) {
					gera_cod("BGE", (String) escopoAninhamento.peek());
				} else if (oprl.equals(">")) {
					gera_cod("BLE", (String) escopoAninhamento.peek());
				} else if (oprl.equals("==")) {
					gera_cod("BNE", (String) escopoAninhamento.peekFirst());
				} else if (oprl.equals("!=")) {
					gera_cod("BEQ", (String) escopoAninhamento.peek());
				} else if (oprl.equals(">=")) {
					gera_cod("BLT", (String) escopoAninhamento.peek());
				} else if (oprl.equals("<=")) {
					gera_cod("BGT", (String) escopoAninhamento.peek());
				}
			} else {
				escopoAninhamento.push((String) ("R" + (escopoAninhamento.size() + 1)));
				if (oprl.equals("<")) {
					gera_cod("BLT", (String) escopoAninhamento.peekLast());
				} else if (oprl.equals(">")) {
					gera_cod("BGT", (String) escopoAninhamento.peekLast());
				} else if (oprl.equals("==")) {
					gera_cod("BEQ", (String) escopoAninhamento.peekFirst());
				} else if (oprl.equals("!=")) {
					gera_cod("BNE", (String) escopoAninhamento.peekLast());
				} else if (oprl.equals(">=")) {
					gera_cod("BGE", (String) escopoAninhamento.peekLast());
				} else if (oprl.equals("<=")) {
					gera_cod("BLE", (String) escopoAninhamento.peekLast());
				}
			}
			break;
		case 49:
			gera_cod("", "\n" + (String) escopoAninhamento.poll());
			break;
		case 50:
			escopoAninhamento.push((String) ("R" + (escopoAninhamento.size() + 1)));
			gera_cod("JMP", (String) escopoAninhamento.peekFirst());
			gera_cod("", "\n" + (String) escopoAninhamento.pollLast() + ":");
			break;
		case 51:
			gera_cod("", "\n" + (String) escopoAninhamento.poll() + ":");
			break;
		case 52:
			escopoAninhamento.push((String) ("R" + (escopoAninhamento.size() + 1)));

			gera_cod("", "\n" + (String) escopoAninhamento.peekFirst() + ":");
			enquanto = false;
			break;
		case 53:
			gera_cod("JMP", (String) escopoAninhamento.pollLast());
			gera_cod("", "\n" + (String) escopoAninhamento.poll());
			enquanto = false;
			break;
		case 54:
			escopoAninhamento.push((String) ("R" + (escopoAninhamento.size() + 1)));
			gera_cod("", "\n" + (String) escopoAninhamento.peekFirst() + ":");
			enquanto = true;
			break;
		case 55:

			if (oprl.equals("<")) {
				gera_cod("BLT", (String) escopoAninhamento.peekLast());
			} else if (oprl.equals(">")) {
				gera_cod("BGT", (String) escopoAninhamento.peekLast());
			} else if (oprl.equals("==")) {
				gera_cod("BEQ", (String) escopoAninhamento.peekLast());
			} else if (oprl.equals("!=")) {
				gera_cod("BNE", (String) escopoAninhamento.peekLast());
			} else if (oprl.equals(">=")) {
				gera_cod("BGE", (String) escopoAninhamento.peekLast());
			} else if (oprl.equals("<=")) {
				gera_cod("BLE", (String) escopoAninhamento.peekLast());
			}
			break;
		}
	}

	public void executeAction(int action, Token token) throws SemanticError {
		switch (action) {

		case 0:
			setEscopos(token);
			break;
		case 1:
			switch (token.getLexeme()) {
			case "inteiro":
				tip = br.com.nemeth.gals.SemanticTable.INT;
				break;
			case "real":
				tip = br.com.nemeth.gals.SemanticTable.FLO;
				break;
			case "caracter":
				tip = br.com.nemeth.gals.SemanticTable.CHA;
				break;
			case "cadeia":
				tip = br.com.nemeth.gals.SemanticTable.STR;
				break;
			case "logico":
				tip = br.com.nemeth.gals.SemanticTable.BOO;
				break;
			default:
				break;
			}
			break;
		case 2:
			setParametro(token);
			break;

		case 3:
			setDeclararVariavel(token);
			break;
		case 4:
			setDeclararBiblio(token);
			break;
		case 5:
			setFuncao(token);
			break;
		case 6:
			detectaVariavelExpressao(token);
			break;
		case 7:
			detectaVariavelAtribuicaoDireta(token.getLexeme());
			break;
		case 8:
			chamadaDeFunc(token);
			break;
		case 9:
			idParametroDeChamadaDeFuncao(token);
			break;
		case 10:
			idVetor(token);
			break;
		case 11:
			this.vetorDeclaracao = true;
			idVetor(token);
			break;
		case 12:
			atribuicao(token);
			nome_id_atrib = token.getLexeme();
			break;
		case 13:
			idVariavelAutoAtribuicao(token);
			break;
		case 14:
			idSwitchCase(token);
			break;
		case 15:
			expressao.add(5);
			break;
		case 16:
			expressao.add(5);
			break;
		case 17:
			expressao.add(6);
			break;
		case 18:
			expressao.add(6);
			break;
		case 19:
			expressao.add(6);
			break;
		case 20:
			expressao.add(4);
			break;
		case 21:
			expressao.add(1);
			break;
		case 22:
			switch (token.getLexeme()) {
			case "+":
				expressao.add(0);
				break;
			case "-":
				expressao.add(1);
				break;
			}
			break;
		case 23:
			switch (token.getLexeme()) {
			case "*":
				expressao.add(2);
				break;
			case "/":
				expressao.add(3);
				break;
			case "%":
				expressao.add(3);
				break;
			}
			break;
		case 24:
			expressao.add(1);
			break;
		case 25:
			expressao.add(0);
			break;
		case 26:
			expressao.add(1);
			break;
		case 27:
			expressao.add(2);
			break;
		case 28:
			expressao.add(3);
			break;
		case 29:
			expressao.add(4);
			break;
		case 30:
			if (buscaTipoFuncao() == -1) {
				throw new SemanticError("Tipo void não pode estar presente numa expressão aritimética",
						token.getPosition());
			}
			expressao.add(buscaTipoFuncao());
			idChamFuncao = null;
			break;
		case 32:
			expressao.add(-4);
			break;
		case 33:
			expressao.add(-2);
			break;
		case 34:
			tabela.get(tabela.size() - 1).setTamanho(Integer.parseInt(token.getLexeme()));
			break;
		case 35:
			System.out.println("inicio");
			setFuncao(token);
			break;
		case 101:
			verificaVariaveis(token);
			gera_cod("RETURN", "0");
			break;
		case 100:
			//validaExpressao(token);
			break;
		case 99:
			this.vetorParâmetro = true;
			break;
		case 98:
			pos = 0;
			funcaoAtiva = null;
			break;
		case 97:
			tip = null;
			vetorDeclaracao = false;
		case 96:
			if (pos < tabela.get(funcaoAtiva).getTamanho()) {
				throw new SemanticError("Faltam parâmetros a serem declarados na função", token.getPosition());
			}
			pos = 0;
			idChamFuncao = funcaoAtiva;
			funcaoAtiva = null;
		case 102:
			getAssembly();
			break;
		default:
			executacaGeracaoAssembly(action, token.getLexeme());
			break;

		}

	}

	public void setEscopos(Token t) throws SemanticError {
		escoposFuncao.push(idEscopo);
		idEscopo++;
	}

	// verifica e insere os parametros dentro da tabela de simbolos
	public void setParametro(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() == escoposFuncao.peek() + 1) {
				throw new SemanticError("Não é possivel inserir novamente um parâmetro com o nome '" + i.getId() + "'",
						t.getPosition());
			}
		}
		Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek() + 1);
		pos++;
		sim.setParam(true);
		sim.setPos(pos);
		sim.setTipo(tip);
		sim.setFuncao(funcaoAtiva);
		tabela.add(sim);
		tela.adicionarSymbol(sim, "" + sim.getEscopo());
		tabela.get(funcaoAtiva).getParametro().add(new Parametro(tabela.size() - 1, tip));
		tip = null;
	}

	// verifica e insere as variáveis declaradas
	public void setDeclararVariavel(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek()) {
				throw new SemanticError("Não é possivel criar duas variáveis com o mesmo nome '" + i.getId()
						+ "' dentro do mesmo escopo", t.getPosition());
			}
		}
		Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek());
		sim.setTipo(tip);
		tabela.add(sim);
		tela.adicionarSymbol(sim, "" + sim.getEscopo());
	}

	// verifica e insere biblioteca
	public void setDeclararBiblio(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek() && i.isBiblio()) {
				throw new SemanticError("Biblioteca '" + i.getId() + "' em duplicidade", t.getPosition());
			}
		}
		Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek());
		sim.setBiblio(true);
		tabela.add(sim);
		tela.adicionarSymbol(sim, "" + sim.getEscopo());
	}

	// verifica e insera função na tabela de simbolos
	public void setFuncao(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.isFunc()) {
				throw new SemanticError("Função '" + i.getId() + "' possui mais de uma declaração", t.getPosition());
			}
		}
		String nome = t.getLexeme();
		if (!nome.equals("inicio")) {
			gera_cod("JMP", "_inicio");
		}
		gera_cod("ROT", "_" + nome);
		Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek());
		sim.setTipo(tip);
		sim.setFunc(true);
		tip = null;
		tabela.add(sim);
		tela.adicionarSymbol(sim, "" + sim.getEscopo());
		funcaoAtiva = tabela.size() - 1;
	}

	// vwerifica utilização de ID em espressão e insere o tipo na pilha de
	// expressão
	public void detectaVariavelExpressao(Token t) throws SemanticError {
		Symbol sim = null;
		Integer in = -1, inn = null;
		for (Symbol i : tabela) {
			in++;
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek()) {
				if (!i.isUsada()) {
					tela.addLinhaLog("Varialvel '" + i.getId() + "' está sendo utilizada sem ser inicializada" + "\n");
					expressao.add(i.getTipo());
					return;
				} else {
					tabela.get(in).setIni(true);
					expressao.push(i.getTipo());
					return;
				}
			} else if (i.getId().equals(t.getLexeme())) {
				if (sim == null) {
					inn = in;
					sim = i;
				} else {
					if (sim.getEscopo() < i.getEscopo()) {
						inn = in;
						sim.setEscopo(i.getEscopo());
					}
				}

			}
		}
		if (sim == null) {
			throw new SemanticError("A variável utilizada não foi declarada", t.getPosition());
		}
		if (!sim.isUsada()) {
			System.out.println("teste");
			tela.addLinhaLog("Varialvel '" + sim.getId() + "' está sendo utilizada sem ser inicializada" + "\n");
		}

		tabela.get(inn).setIni(true);
		expressao.push(sim.getTipo());
	}

	// detecta se a variavel a ser atribuido o valor foi declarada
	public void detectaVariavelAtribuicaoDireta(String t) throws SemanticError {
		int in = 0, inachado = 0;
		Symbol sim = null;
		for (Symbol i : tabela) {
			if (i.getId().equals(t) && i.getEscopo() == (int) escoposFuncao.peek()) {
				sim = i;
				expressao.push(i.getTipo());
				inachado = in;
				break;

			} else if (i.getId().equals(t)) {
				if (sim == null) {
					sim = new Symbol(t, (int) escoposFuncao.peek());
					inachado = in;
				} else {
					if (sim.getEscopo() < i.getEscopo()) {
						sim = i;
						inachado = in;
					}
				}

			}
			in++;
		}
		if (sim == null) {
			throw new SemanticError("A variável '" + t + "' utilizada não foi declarada");
		}
		expressao.push(sim.getTipo());
		tabela.get(inachado).setUsada(true);
		indiceVariavel = in - 1;
	}

	// verifica a chamada de uma função
	public void chamadaDeFunc(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			int in = 0;
			if (i.getId().equals(t.getLexeme()) && i.isFunc()) {
				funcaoAtiva = in;
				expressao.push(i.getTipo());
				return;
			}
			in++;
		}
		throw new SemanticError("Chamada de função não encontrada", t.getPosition());
	}

	// verifica as insconsistencias entre a declaração de uma função e a sua
	// chamada
	public void idParametroDeChamadaDeFuncao(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			SemanticTable sem = new SemanticTable();
			if (i.getId().equals(t.getLexeme())) {
				if (!i.isUsada()) {
					throw new SemanticError(
							"Variável '" + t.getLexeme() + "' está sendo utilizada sem estar incicializada", 0);
				}
				switch (sem.atribType(tabela.get(funcaoAtiva).getParametro().get(pos).tipo, i.getTipo())) {
				case ERR:
					throw new SemanticError("Variável '" + t.getLexeme() + "' por parâmetro com tipo imcompatível",
							t.getPosition());
				case WAR:
					pos++;
					tela.addLinhaLog("Variavel '" + i.getId() + "' poderá ter seu valor alterado" + "\n");
					break;
				case OK_:
					pos++;
					return;
				}
			}

		}
		throw new SemanticError("Variável não encontrada", t.getPosition());
	}

	public void idVetor(Token t) throws SemanticError {
		if (vetorDeclaracao) {
			for (Symbol i : tabela) {
				if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek()) {
					throw new SemanticError("Vetor '" + i.getId() + "' está em duplicidade", t.getPosition());
				}
			}
			Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek());
			sim.setVet(true);
			sim.setTipo(tip);
			tabela.add(sim);
			tela.adicionarSymbol(sim, "" + sim.getEscopo());
			vetorDeclaracao = false;
			return;
		} else if (vetorParâmetro) {
			for (Symbol i : tabela) {
				if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek() + 1) {
					throw new SemanticError(
							"Não é possivel inserir novamente um parâmetro com o nome '" + i.getId() + "'",
							t.getPosition());
				}
				Symbol sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek() + 1);
				pos++;
				sim.setParam(true);
				sim.setPos(pos);
				sim.setTipo(tip);
				sim.setFuncao(funcaoAtiva);
				tabela.add(sim);
				tela.adicionarSymbol(sim, "" + sim.getEscopo());
				tabela.get(funcaoAtiva).getParametro().add(new Parametro(tabela.size() - 1, tip));
			}

		} else {
			detectaVariavelAtribuicaoDireta(t.getLexeme());
			atribuicao(t);
		}
		tip = null;

	}

	public void atribuicao(Token t) throws SemanticError {
		Symbol sim = null;
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme())) {
				if (sim == null) {
					sim = new Symbol(t.getLexeme(), escoposFuncao.peek());
				} else {
					if (sim.getEscopo() < i.getEscopo()) {
						sim.setEscopo(i.getEscopo());
					}
				}

			}
		}
		if (sim == null) {
			throw new SemanticError("A variável utilizada não foi declarada", t.getPosition());
		}
		expressao.push(-3);
	}

	public void idVariavelAutoAtribuicao(Token t) throws SemanticError {
		SemanticTable sem = new SemanticTable();
		Symbol sim = null;
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() == (int) escoposFuncao.peek()) {
				int result = sem.atribType(i.getTipo(), 0);
				if (result == OK_) {
					return;
				}
				throw new SemanticError(
						"Erro ao atribuir a variavel '" + i.getId() + "' na expressão de auto atribuição",
						t.getPosition());
			} else if (i.getId().equals(t.getLexeme())) {
				if (sim == null) {
					sim = new Symbol(t.getLexeme(), (int) escoposFuncao.peek());
				} else {
					if (sim.getEscopo() < i.getEscopo()) {
						sim = i;
					}
				}

			}

		}
		if (sim == null) {
			throw new SemanticError("Variável " + t.getLexeme() + " não foi declarada.", t.getPosition());
		}
		int result = sem.atribType(sim.getTipo(), 0);
		if (result == OK_) {
			return;
		}
		throw new SemanticError("Erro ao atribuir a variavel '" + sim.getId() + "' na expressão de auto atribuição",
				t.getPosition());
	}

	public void idSwitchCase(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getId().equals(t.getLexeme()) && i.getEscopo() <= (int) escoposFuncao.peek()) {
				if (i.getId().equals(t.getLexeme()) && !i.isUsada()) {
					throw new SemanticError("Não é possível utilizar no escolha caso uma variavle não inicializada",
							t.getPosition());
				}
				return;
			}

		}
		throw new SemanticError("Variável '" + t.getLexeme() + "' não declarada", t.getPosition());
	}

	public int buscaTipoFuncao() {
		return tabela.get(idChamFuncao).getTipo();
	}

	public int validaExpressao(Token t) throws SemanticError {
		int op1, op2, op, result;
		while (true) {
			op2 = (int) expressao.pop();
			if (op2 == -2) {
				op2 = validaExpressao(t);
			}
			op = (int) expressao.pop();
			if (op == -3) {
				switch (new SemanticTable().atribType((int) expressao.peek(), op2)) {
				case ERR:
					throw new SemanticError("Tipo incompativel para atribuição da expressão com o uso da variável '"
							+ t.getLexeme() + "' ", t.getPosition());
				case WAR:
					tela.addLinhaLog("Poderá haver perda de dados na atribuição da expressão com o uso da variável '"
							+ t.getLexeme() + "' na posição" + t.getPosition() + "\n");
					return OK_;
				default:
					tabela.get(indiceVariavel).setUsada(true);
					return new SemanticTable().atribType((int) expressao.pop(), op2);
				}
			}
//			corrigir
//			if (!expressao.empty()) {
				op1 = (int) expressao.pop();

				if (op1 == -2) {
					op1 = validaExpressao(t);

				}
				new SemanticTable();
				result = SemanticTable.resultType(op1, op2, op);
				switch (result) {
				case ERR:
					throw new SemanticError("Expressão em formato invalido no uso da variavel '" + t.getLexeme() + "' ",
							t.getPosition());

				}
				if ((int) expressao.peek() == -4) {
					expressao.pop();
					return result;
				}
				expressao.push(result);
				if (expressao.size() == 1) {
					return OK_;

				}
			}
//		}
	}

	public void verificaVariaveis(Token t) throws SemanticError {
		for (Symbol i : tabela) {
			if (i.getEscopo() == (int) escoposFuncao.peek() && !i.isIni() && !i.isFunc() && !i.isParam()) {
				tela.addLinhaLog("Variavel '" + i.getId() + "' foi declarada mas não utilizada" + "\n");
			}

		}
		escoposFuncao.pop();

	}
}
