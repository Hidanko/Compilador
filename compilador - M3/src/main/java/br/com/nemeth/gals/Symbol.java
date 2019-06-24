package br.com.nemeth.gals;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class Symbol {

	private String id;
	private int tipo;
	private boolean ini;
	private boolean usada;
	private int escopo;
	private boolean param;
	private int pos;
	private boolean vet;
	private boolean func;
	private int tamanho;
    int funcao;

	private List<Parametro> parametro;
	private boolean ref;
	private boolean biblio;

	public Symbol(String id, int tipo, boolean ini, boolean usada, int escopo, boolean param, int pos, boolean vet,
			boolean func) {
		this.id = id;
		this.tipo = tipo;
		this.ini = ini;
		this.usada = usada;
		this.escopo = escopo;
		this.param = param;
		this.pos = pos;
		this.vet = vet;
		this.func = func;

		// printSymbol();
	}

	public void printSymbol() {
		System.out.print(this.id + " |  ");
		System.out.print(this.tipo + " |  ");
		System.out.print(this.ini + " |  ");
		System.out.print(this.usada + " |  ");
		System.out.print(this.escopo + " |  ");
		System.out.print(this.param + " |  ");
		System.out.print(this.pos + " |  ");
		System.out.print(this.vet + " |  ");
		System.out.println(this.func + " |  ");
	}

	public String getId() {
		return id;
	}

	public boolean isFunc() {
		return func;
	}

	public int getTipo() {
		return tipo;
	}

	public boolean isIni() {
		return ini;
	}

	public boolean isUsada() {
		return usada;
	}

	public boolean isParam() {
		return param;
	}

	public int getPos() {
		return pos;
	}

	public boolean isVet() {
		return vet;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setIni(boolean ini) {
		this.ini = ini;
	}

	public void setUsada(boolean usada) {
		this.usada = usada;
	}

	public void setEscopo(int escopo) {
		this.escopo = escopo;
	}

	public void setParam(boolean param) {
		this.param = param;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void setVet(boolean vet) {
		this.vet = vet;
	}

	public void setFunc(boolean func) {
		this.func = func;
	}

	public int getEscopo() {
		return escopo;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public Symbol(String id, int escopo) {
		this.id = id;
		this.escopo = escopo;
		this.usada = false;
		this.param = false;
		this.vet = false;
		this.setRef(false);
		this.func = false;
		this.setBiblio(false);
		this.funcao = 0;
		this.ini = false;
		this.setParametro(new ArrayList<>());

	}

	public boolean isBiblio() {
		return biblio;
	}

	public void setBiblio(boolean biblio) {
		this.biblio = biblio;
	}

	public boolean isRef() {
		return ref;
	}

	public void setRef(boolean ref) {
		this.ref = ref;
	}

	public List<Parametro> getParametro() {
		return parametro;
	}

	public void setParametro(List<Parametro> parametro) {
		this.parametro = parametro;
	}
    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
	
}