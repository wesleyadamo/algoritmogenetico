import java.util.ArrayList;
import java.util.Random;

public class Individuo {

	private int tamCromossomo = Principal.tamCromossomo;
	private Cromossomo cromossomo1;
	private Cromossomo cromossomo2;
	private double aptidao;
	private int faixaInf;
	private int faixaSup;
	private double valorX;
	private double valorY;

	public Individuo() {
		cromossomo1 = new Cromossomo(tamCromossomo);
		cromossomo2 = new Cromossomo(tamCromossomo);
		aptidaoIndividuo();

	}

	public double getAptidao() {
		return aptidao;
	}

	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;
	}

	public int[] getCromossomos() {

		int[] vet = new int[tamCromossomo * 2];
		int i = 0;
		for (int v : cromossomo1.getGene()) {
			vet[i] = v;
			i++;
		}

		for (int v : cromossomo2.getGene()) {
			vet[i] = v;
			i++;
		}

		return vet;
	}

	public void setCromossomo1(int[] vet) {
		cromossomo1.setCromossomo(vet);
	}

	public void setCromossomo2(int[] vet) {
		cromossomo2.setCromossomo(vet);
	}

	public int getFaixaInf() {
		return faixaInf;
	}

	public void setFaixaInf(int faixaInf) {
		this.faixaInf = faixaInf;
	}

	public int getFaixaSup() {
		return faixaSup;
	}

	public void setFaixaSup(int faixaSup) {
		this.faixaSup = faixaSup;
	}

	public void aptidaoIndividuo() {
		valorX = -10 + cromossomo1.getValorReal() * ((10 + 10) / (Math.pow(2, tamCromossomo) - 1));
		valorY = -10 + cromossomo2.getValorReal() * ((10 + 10) / (Math.pow(2, tamCromossomo) - 1));

		aptidao = Math.abs(Math.exp(-valorX) - Math.pow(valorY, 2) + 1) + Math.pow(10, -4);
	}

	public void crossover() {

		Random gerador = new Random();

		int x = gerador.nextInt(tamCromossomo);
		int y = gerador.nextInt(tamCromossomo);

		int valor = cromossomo1.getGene(x) == 1 ? 0 : 1;

		cromossomo1.SetGene(x, valor);

		valor = cromossomo2.getGene(y) == 1 ? 0 : 1;

		cromossomo2.SetGene(x, valor);

	}

	public double getValorX() {
		return valorX;
	}

	public void setValorX(double valorX) {
		this.valorX = valorX;
	}

	public double getValorY() {
		return valorY;
	}

	public void setValorY(double valorY) {
		this.valorY = valorY;
	}

}
