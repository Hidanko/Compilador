package br.com.nemeth.gals;

public class AssemblyManager {
	StringBuilder data;
	StringBuilder text;

	public AssemblyManager() {
		text = new StringBuilder();
		text.append(".text \n");
		data = new StringBuilder();
		data.append(".data \n");
	}

	public void addText(String texto) {
		text.append(texto);
	}

	public void addData(String data) {
		text.append(data);
	}

	public String getAssembly() {
		return (data.toString() + "\n" + text.toString());
	}

	public void addVectorData(String nome, int size) throws Exception {
		if (size <= 0) {
			throw new Exception("Tamanho de vetor inválido");
		}
		String vet = nome + ":";
		for (int i = 0; i < size; i++) {
			vet += "0,";
		}
		// substring para remover a ultima virgula
		this.addData(vet.substring(0, vet.length() - 1));
	}

}
