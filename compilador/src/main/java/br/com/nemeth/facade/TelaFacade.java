package br.com.nemeth.facade;

import br.com.nemeth.view.TelaController;

public class TelaFacade {

	private TelaController tela;
	private static TelaFacade INSTANCE;

	public synchronized TelaFacade getInstance(TelaController tela) {
		if (INSTANCE == null) {
			INSTANCE = new TelaFacade(tela);
		}
		return INSTANCE;
	}

	public TelaFacade(TelaController tela) {
		this.tela = tela;
	}

	@SuppressWarnings("unused")
	private TelaFacade() {

	}

	/*
	 * Pegar texto de input digitado pelo usuario
	 */
	public String getInput() {
		return tela.getInput();
	}

	/*
	 * Envia texto de output para a tela
	 */
	public void setOutput(String output) {
		tela.setOutput(output);
	}

	/*
	 * Adiciona uma única linha ao log
	 */
	public void addLinhaLog(String log) {
		tela.addLinhaLog(log);
	}

	public void reescreverLog(String log) {
		tela.reescreverLog(log);
	}
	
	public void limpaOutput() {
		tela.limpaOutput();
	}
	
	public void limpaLog() {
		tela.limpaLog();
	}
	
	public void limpaStatus() {
		tela.limpaStatus();
	}
}
