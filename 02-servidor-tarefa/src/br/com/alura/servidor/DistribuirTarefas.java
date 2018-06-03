package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidor) {
		this.threadPool = threadPool;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {

		try {

			System.out.println("Distribuindo as tarefas para o cliente " + socket);

			Scanner entradaCliente = new Scanner(socket.getInputStream());

			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

			while (entradaCliente.hasNextLine()) {

				String comando = entradaCliente.nextLine();
				System.out.println("Comando recebido " + comando);
				
				switch (comando) {
					case "c1": {						
						saidaCliente.println("Confirmação do comando c1");
		                ComandoC1 c1 = new ComandoC1(saidaCliente);
		                this.threadPool.execute(c1);
						break;
					}
					case "c2": {
						saidaCliente.println("Confirmação do comando c2");
		                ComandoC2 c2 = new ComandoC2(saidaCliente);
		                this.threadPool.execute(c2);
						break;
					}
					case "c3":{
						saidaCliente.println("Confirmação do comando c3");
						ComandoC3ChamaWS c3WS = new ComandoC3ChamaWS(saidaCliente);
						ComandoC3ChamaBanco c3Banco = new ComandoC3ChamaBanco(saidaCliente);
						
						Future<Integer> futureWS = this.threadPool.submit(c3WS);
						Future<Integer> futureBanco = this.threadPool.submit(c3Banco);
						
						FutureTask<Void> futureTask = new FutureTask<>(new FutureWSBancoResponse(futureWS, futureBanco, saidaCliente));
						new Thread(futureTask).start();
						
						//this.threadPool.submit(new FutureWSBancoResponse(futureWS, futureBanco, saidaCliente));
						break;
					}
					case "c4" :{
						this.filaComandos.put(comando);
						saidaCliente.println("Comando " + comando + "  adicionado na fila");
						break;
					}
					case "fim": {
						saidaCliente.println("Desligando o servidor");
						servidor.parar();
						return;
					}
					default: {
						saidaCliente.println("Comando não encontrado");
					}
				}

			}

			saidaCliente.close();
			entradaCliente.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
