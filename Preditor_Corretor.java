/* Universidade Presbiteriana Mackenzie
 * Ciencia da Computacao 4G - Matutino
 * Trabalho de Analise Num�rica II - Paulino 
 * Yuri Serrano  TIA: 41414349
 */

import java.util.Scanner;
import java.text.DecimalFormat;

public class Preditor_Corretor {
	static double a, b; 
	static double h;	 
	static double alfa;
	final static DecimalFormat format = new DecimalFormat("0.00000000000000"); //formatador para 14 casas decimais 
	
	// Fun��o que faz o c�lculo da fun��o y'.
	public static double f(double t, double y, int alternativa) {
		double f = 0;
		switch (alternativa) {
		case 1: 
			f = t * Math.pow(Math.E, 3 * t) - 2 * y; 
			break;
		case 2: 
			f = 1 + Math.pow(t - y, 2);
			break;
		case 3: 
			f = 1 + (y / t);
			break;
		case 4: 
			f = Math.cos(2 * t) + Math.sin(3 * t);
			break;
		} 
		return f;
	}
	
	// Fun��o que calcula a solu��o real da fun��o y em t.
	public static double y(double t, int alternativa) {
		double f = 0;
		switch (alternativa) {
		case 1: 	
			f = (0.2 * t * Math.pow(Math.E, 3 * t)) - (0.04 * Math.pow(Math.E, 3 * t)) + (0.04 * Math.pow(Math.E, -2 * t)); 
			break;
		case 2: 
			f = t + 1 / (1 - t);
			break;
		case 3: 
			f = t * Math.log(t) + 2 * t;
			break;
		case 4: 
			f = ((double) 1 / 2) * Math.sin(2 * t) - ((double) 1 / 3) * Math.cos(3 * t) + ((double) 4 / 3);
			break;			
		} 
		return f;
	}
	
	//C�lcula a aproxima��o w para cada t atrav�s do m�todo Preditor-Corretor 
	// de Quarta Ordem de Adams
	public static void main(String args[]) {		
		Scanner entrada = new Scanner(System.in);
		double t[], w[], K1, K2, K3, K4;  
		int opcao, N;
		
		System.out.print("Selecione uma opcao\n" +
						 "1. y' = te^(3t) - 2y\n" +
						 "2. y' = 1 + (t - y) ^ 2\n" +
						 "3. y' = 1 + y / t\n" +
						 "4. y' = cos(2t) + sen(3t)\n" +
						 "Opcao: ");		
		opcao = entrada.nextInt();	//armazena o c�digo do exerc�cio		
		
		t = new double[5];	
		w = new double[5];
		
		//exerc�cios 1, 2, 3 e 4
		switch (opcao) {
		case 1:	a = 0; b = 1; h = 0.2; alfa = 0; break;
		case 2: a = 2; b = 3; h = 0.2; alfa = 1; break;
		case 3:	a = 1; b = 2; h = 0.2; alfa = 2; break; 
		case 4:	a = 0; b = 1; h = 0.2; alfa = 1; break;
		default:
			System.out.println("N�o � uma op��o v�lida");
			return;
		}
		
		N = (int)((b - a) / h);	//n�mero de passos
		
		t[0] = a;				//t inicial
		w[0] = alfa;			//aproxima��o w inicial
		
		System.out.println("\nt \t \t \tAproximacao w \t \tSolucao real");
		System.out.println(format.format(t[0]) + "\t" + format.format(w[0]) + "\t" + format.format(y(t[0], opcao)));
		
		for (int i = 1; i <= 3; i++) {
			//c�lculo dos K
			K1 = h * f(t[i - 1], w[i - 1], opcao);
			K2 = h * f(t[i - 1] + h / 2, w[i - 1] + K1 / 2, opcao);
			K3 = h * f(t[i - 1] + h / 2, w[i - 1] + K2 / 2, opcao);
			K4 = h * f(t[i - 1] + h, w[i - 1] + K3, opcao);
			
			w[i] = w[i - 1] + (K1 + 2 * K2 + 2 * K3 + K4) / 6;	//aproxima��o da itera��o i
			t[i] = a + i * h;									//t da itera��o i
			
			System.out.println(format.format(t[i]) + "\t" + format.format(w[i]) + "\t" + format.format(y(t[i], opcao)));
		}
		
		for (int i = 4; i <= N; i++) {
			t[4] = a + i * h;	//c�lculo de t da itera��o i, agora sempre sobrescrevendo t[4]
			
			//c�lculo da aproxima��o w da itera��o i, sempre sobrescrevendo w[4]. Depois, a corre��o de w.
			w[4] = w[3] + h / 24 * (55 * f(t[3], w[3], opcao) - 59 * f(t[2], w[2], opcao) + 37 * f(t[1], w[1], opcao) - 9 * f(t[0], w[0], opcao));	
			w[4] = w[3] + h / 24 * (9 * f(t[4], w[4], opcao)  + 19 * f(t[3], w[3], opcao) - 5 * f(t[2], w[2], opcao) + f(t[1], w[1], opcao));	
			
			System.out.println(format.format(t[4]) + "\t" + format.format(w[4]) + "\t" + format.format(y(t[4], opcao)));
			
			//valores shiftados para a pr�xima itera��o ser executada
			for (int j = 0; j < 4; j++) {
				t[j] = t[j + 1];
				w[j] = w[j + 1];
			}
		}
		
		System.out.println("\nProcesso finalizado.");
	}
}
