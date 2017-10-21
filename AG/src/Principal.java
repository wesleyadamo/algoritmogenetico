import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Soundbank;

public class Principal {

	ArrayList<Individuo> populacao;
	private int tamPopulacao = 20; // 50
	private int valorRoleta = 0;
	private double aptidaoGeral = 0;
	static int tamCromossomo = 12;
	private double probCruzamento = 0.9;
	private Individuo progenitor1;
	private Individuo progenitor2;
	private double probMultacao = 0.1;
	private int geracoes = 10000;
	private int torneio = 12;

	public Principal() {
		populacao = new ArrayList<>();
		criarPopulacao();
		criarProbabilidade();
	}

	public void criarPopulacao() {
		for (int i = 0; i < tamPopulacao; i++) {

			populacao.add(new Individuo());
			aptidaoGeral += Math.round(populacao.get(i).getAptidao());
		}
	}

	public void criarProbabilidade() {

		int faixa1 = -1;
		int faixaTotal = 0;
		int tamFaixa = 0;

		for (int i = 0; i < tamPopulacao; i++) {
			tamFaixa = (int) Math.round((Math.round(populacao.get(i).getAptidao()) * 360) / aptidaoGeral);
			populacao.get(i).setFaixaInf(faixa1 + 1);
			populacao.get(i).setFaixaSup(faixa1 + tamFaixa);
			faixa1 = populacao.get(i).getFaixaSup();
			faixaTotal += tamFaixa;

		}

		valorRoleta = faixaTotal - 1;

	}

	public Individuo roleta() {
		Random aleatorio = new Random();
		int valorGerado = aleatorio.nextInt(valorRoleta);

		for (int i = 0; i < tamPopulacao; i++) {
			if (valorGerado <= populacao.get(i).getFaixaSup())
				return populacao.get(i);
		}

		return null;
	}

	public Individuo[] cruzamento() {

		criarProbabilidade();

		Individuo p1 = roleta();
		Individuo p2 = roleta();

		progenitor1 = p1;

		while (p1.equals(p2)) {
			p2 = roleta();
		}

		progenitor1 = p1;
		progenitor2 = p2;

		int pai1Gene[] = p1.getCromossomos();
		int pai2Gene[] = p2.getCromossomos();

		Individuo filho = new Individuo();
		Individuo filho2 = new Individuo();

		int[] cromossomo1 = new int[tamCromossomo * 2];
		int[] cromossomo2 = new int[tamCromossomo * 2];

		Random gerador = new Random();
		int corte = gerador.nextInt(tamCromossomo * 2);
		int corteTemp = corte;

		int pos = 0;
		// pai1
		for (int i = 0; i < corte; i++) {
			cromossomo1[i] = pai1Gene[i];

		}

		// pai2
		for (int i = corte; i < tamCromossomo * 2; i++) {
			cromossomo1[i] = pai2Gene[i];

		}

		// primeira parte segundo filho
		for (int i = corte; i < cromossomo1.length; i++) {
			cromossomo2[i] = pai1Gene[i];
		}

		for (int i = 0; i < corte; i++) {
			cromossomo2[i] = pai2Gene[i];
		}

		int c1[] = new int[tamCromossomo];
		int c2[] = new int[tamCromossomo];

		int c3[] = new int[tamCromossomo];
		int c4[] = new int[tamCromossomo];

		for (int i = 0; i < tamCromossomo * 2; i++)

			if (i < tamCromossomo)
				c1[i] = cromossomo1[i];
			else
				c2[i - tamCromossomo] = cromossomo1[i];

		for (int i = 0; i < tamCromossomo * 2; i++)

			if (i < tamCromossomo)
				c3[i] = cromossomo2[i];
			else
				c4[i - tamCromossomo] = cromossomo2[i];

		filho.setCromossomo1(c1);
		filho.setCromossomo2(c2);
		filho2.setCromossomo1(c3);
		filho2.setCromossomo2(c4);
		filho.aptidaoIndividuo();
		filho2.aptidaoIndividuo();

		Individuo[] retorno = new Individuo[2];
		retorno[0] = filho;
		retorno[1] = filho2;

		return retorno;

	}

	public Individuo getMelhorIndividuo() {

		Individuo melhor = populacao.get(0);
		for (int i = 0; i < populacao.size(); i++) {

			if (populacao.get(i).getAptidao() > melhor.getAptidao()) {
				melhor = populacao.get(i);
			}

		}

		return melhor;

	}

	public void novaGeracao(Individuo f[]) {

		if (f[0].getAptidao() > progenitor1.getAptidao() && progenitor2.getAptidao() >= progenitor1.getAptidao()) {
			populacao.remove(progenitor1);
			populacao.add(f[0]);

		} else if (f[0].getAptidao() > progenitor2.getAptidao()
				&& progenitor1.getAptidao() >= progenitor2.getAptidao()) {
			populacao.remove(progenitor2);
			populacao.add(f[0]);

		} else if (f[1].getAptidao() > progenitor1.getAptidao()
				&& progenitor2.getAptidao() >= progenitor1.getAptidao()) {
			populacao.remove(progenitor1);
			populacao.add(f[1]);
		} else if (f[1].getAptidao() > progenitor2.getAptidao()
				&& progenitor1.getAptidao() >= progenitor2.getAptidao()) {
			populacao.remove(progenitor2);
			populacao.add(f[1]);
		}
	}

	public boolean testarParada() {

		double valorMaxFunc = 22027.465888843344;
		Individuo melhor = getMelhorIndividuo();

		if (Math.abs(melhor.getAptidao() - valorMaxFunc) < 0.005) {
			return true;
		}

		return false;

	}

	public static void main(String[] args) {
		Principal p = new Principal();

		Random gerador = new Random();

		int geracoesTotal = 0;
		double probMutacao = 0;

		for (int i = 0; i < p.geracoes; i++) {

			for (int j = 0; j < p.torneio; j++) {

				double aleatProbCruz = gerador.nextDouble();

				if (aleatProbCruz < p.probCruzamento) {

					Individuo f[] = p.cruzamento();

					probMutacao = gerador.nextDouble();
					int individuoEscolhido = gerador.nextInt(2);
					if (probMutacao < p.probMultacao && individuoEscolhido == 0) {
						f[0].crossover();
					} else if (probMutacao < p.probMultacao && individuoEscolhido == 1) {
						f[1].crossover();
					}

					p.novaGeracao(f);

				}

			}

			geracoesTotal++;

			if (p.testarParada())
				break;

		}

		System.out.println("\tESTATÍSTICA AG");
		System.out.println("=======================================");
		Individuo melhor = p.getMelhorIndividuo();

		System.out.println("Individuo vecender: ");
		for (int i : melhor.getCromossomos())
			System.out.print(i);
		System.out.println("");

		System.out.println("\nMáximo obtido: " + melhor.getAptidao());
		System.out.println("\nMáximo desejado: " + 22027.465888843344);
		System.out.println("\nTotal de Gerações: " + geracoesTotal);
		System.out.println("\nValor de X: " + melhor.getValorX());
		System.out.println("\nValor de Y: " + melhor.getValorY());
		System.out.println("\nErro: " + Math.abs(melhor.getAptidao() - 22027.465888843344));
		System.out.println("=======================================");

	}

}
