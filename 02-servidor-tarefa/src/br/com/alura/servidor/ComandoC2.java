package br.com.alura.servidor;

import java.io.PrintStream;

public class ComandoC2 implements Runnable {

	private PrintStream saida;

	public ComandoC2(PrintStream saida) { 
		this.saida = saida;
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {
		// será impresso no console do servidor
		System.out.println("Executando comando c2");

		try {
			Thread.sleep(5000); //simulando algo demorado
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		try {
			if(true)
				throw new Exception("problema no comando c2");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// essa mensagem será enviada para o cliente
		this.saida.println("Comando c2 executado com sucesso!");
	}
}
