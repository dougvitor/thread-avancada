import java.io.IOException;

public class ServidorDeTesteSync {

	private boolean estaRodando = false;

	private  void rodar() throws IOException {

		new Thread(()->{
			System.out.println("Servidor começando, estaRodando = " + estaRodando());
			
			while(!estaRodando()) {}
			
			System.out.println("Servidor rodando, estaRodando = " + estaRodando());
			
			while(estaRodando()) {}
			
			System.out.println("Servidor terminando, estaRodando = " + estaRodando());
			
		}).start();;
	}
	
	public synchronized boolean estaRodando() {
        return this.estaRodando;
    }
	
	public synchronized void ligar() {
        this.estaRodando = true;
    }
	
	public synchronized void parar() {
        this.estaRodando = false;
    }

	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = true");
		this.ligar();
		
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = false");
		this.parar();
	}

	public static void main(String[] args) throws Exception {
		ServidorDeTesteSync servidor = new ServidorDeTesteSync();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

}
