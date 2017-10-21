import java.util.Random;

public class Cromossomo {

	public int[] getGene() {
		return gene;
	}

	public void setGene(int[] gene) {
		this.gene = gene;
	}

	public int getGene(int i) {
		return gene[i];
	}

	public void SetGene(int i, int v){
		 gene[i]=v;
	}

	private int[] gene;

	public int getValorReal() {
		return valorReal;
	}

	public void setValorReal(int valorReal) {
		this.valorReal = valorReal;
	}

	private int valorReal;
	private int tamCromossomo;
	private StringBuilder geneBin;

	public Cromossomo(int tam) {
		gene = new int[tam];
		geneBin = new StringBuilder();
		tamCromossomo = tam;
		criarCromossomo();
		valorReal();

	}

	public Cromossomo() {

	}

	public void criarCromossomo() {
		Random gerador = new Random();

		for (int i = 0; i < tamCromossomo; i++) {
			gene[i] = gerador.nextInt(2);
			geneBin.append(gene[i]);

		}
	}

	public void setCromossomo(int vet[]) {
		setGene(vet);

	}

	public void valorReal() {

		valorReal = Integer.parseInt(geneBin.toString(), 2);

	}

	

}
