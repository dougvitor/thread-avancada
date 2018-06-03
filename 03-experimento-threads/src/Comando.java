import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Comando implements Comparable<Comando>{
	
	private String tipo;
	
	private int prioridade;
	
	private String params;
	
	public Comando(String tipo, int prioridade, String params) {
		this.tipo = tipo;
		this.prioridade = prioridade;
		this.params = params;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public int compareTo(Comando outroComando) {
		 return outroComando.prioridade - this.prioridade;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Comando> comandos = new PriorityBlockingQueue<>();

		comandos.put(new Comando("ADD", 5, "curso=threads2&dataCriacao=12/06/2016&nivel=avancada"));
		comandos.put(new Comando("UPDATE", 3, "curso=threads2&dataCriacao=13/06/2016")); 
		comandos.put(new Comando("REMOVE", 1, "id=3"));
		comandos.put(new Comando("GET", 2, "id=4"));

		Comando comando = null;
		while((comando = comandos.take()) != null) { 
		    System.out.println(comando.getTipo() + " - " + comando.getPrioridade());
		}
	}

}
