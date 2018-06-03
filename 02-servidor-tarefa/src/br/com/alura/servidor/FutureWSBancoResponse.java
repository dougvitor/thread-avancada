package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureWSBancoResponse implements Callable<Void> {
	
	private Future<Integer> futureWS;
	private Future<Integer> futureBanco;
	private PrintStream saidaCliente;

	public FutureWSBancoResponse(Future<Integer> futureWS, Future<Integer> futureBanco, PrintStream saidaCliente) {
		this.futureWS = futureWS;
		this.futureBanco = futureBanco;
		this.saidaCliente = saidaCliente;
	}

	@Override
	//não queremos devolver nada, então usamos um tipo que representa nada: Void
	public Void call() throws Exception {
		System.out.println("Aguardando resultados do future WS e Banco");

		try {
			Integer numeroMagico = this.futureWS.get(5, TimeUnit.SECONDS);
			Integer numeroMagico2 = this.futureBanco.get(5, TimeUnit.SECONDS);

			this.saidaCliente.println("Resultado do comando c3: " + numeroMagico + ", " + numeroMagico2);

		} catch (InterruptedException | ExecutionException | TimeoutException e) {

			System.out.println("Timeout: Cancelando a execução do comando c3");

			this.saidaCliente.println("Timeout na execução do comando c3");
			this.futureWS.cancel(true);
			this.futureBanco.cancel(true);
		}

		System.out.println("Finalizou FutureWSBancoResponse");

		return null; //esse Callable não tem retorno, por isso null
	}

}
