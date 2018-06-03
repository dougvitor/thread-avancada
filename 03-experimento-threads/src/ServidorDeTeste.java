import java.io.IOException;

public class ServidorDeTeste {

	private volatile boolean estaRodando = false;

	public void rodar() throws IOException {

		Runnable r = ()->{
			System.out.println("Servidor começando, estaRodando = " + estaRodando);
			
			while(!estaRodando) {}
			
			if (estaRodando) {
                throw new RuntimeException("Deu erro na thread...");
            }
			
			System.out.println("Servidor rodando, estaRodando = " + estaRodando);
			
			while(estaRodando) {}
			
			System.out.println("Servidor terminando, estaRodando = " + estaRodando);
		};
		
		Thread newThread = new Thread(r);
		newThread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		newThread.start();
	}

	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		
		System.out.println("Main alterando estaRodando = " + (estaRodando = true));
		
		Thread.sleep(5000);
		
		System.out.println("Main alterando estaRodando = " + (estaRodando = false));
	}

	public static void main(String[] args) throws Exception {
		ServidorDeTeste servidor = new ServidorDeTeste();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

}
