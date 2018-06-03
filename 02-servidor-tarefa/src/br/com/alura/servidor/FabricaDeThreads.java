package br.com.alura.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

	private ThreadFactory defaultFactory;

    public FabricaDeThreads(ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public Thread newThread(Runnable tarefa) {
        Thread thread = defaultFactory.newThread(tarefa); 
        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
        thread.setDaemon(true);
        return thread;
    }
}
