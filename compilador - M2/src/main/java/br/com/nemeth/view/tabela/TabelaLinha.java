package br.com.nemeth.view.tabela;

public class TabelaLinha {

	private String nome;
	private String inT;
	private String flo;
	private String cha;
	private String string;
	private String boo;
	
	
	
	public TabelaLinha(String nome, String inT, String flo, String cha, String string, String boo) {
		super();
		this.nome = nome;
		this.inT = inT;
		this.flo = flo;
		this.cha = cha;
		this.string = string;
		this.boo = boo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFlo() {
		return flo;
	}
	public void setFlo(String flo) {
		this.flo = flo;
	}
	public String getCha() {
		return cha;
	}
	public void setCha(String cha) {
		this.cha = cha;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public String getBoo() {
		return boo;
	}
	public void setBoo(String boo) {
		this.boo = boo;
	}
	public String getInT() {
		return inT;
	}
	public void setInT(String inT) {
		this.inT = inT;
	}
}
