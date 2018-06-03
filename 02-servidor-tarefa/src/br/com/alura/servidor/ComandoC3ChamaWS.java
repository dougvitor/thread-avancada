package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC3ChamaWS implements Callable<Integer> {

	private PrintStream saida;

	public ComandoC3ChamaWS(PrintStream saida) { 
		this.saida = saida;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("Servidor recebeu comando C3 - WS");
		
		saida.println("Processando comando C3 - WS");

		Thread.sleep(10000); //simulando algo demorado
		
		int numero = new Random().nextInt(100) + 1;
		
		// essa mensagem será enviada para o cliente
		this.saida.println("Servidor finalizou comando C3 - WS");
		
		return numero;
	}
}
