package org.usil.facade;

import org.usil.model.Cliente;
import org.usil.model.Comprobante;
import org.usil.model.Producto;
import org.usil.thread.ThreadManager;

//Facade que simplifica el uso de ThreadManager para procesamiento paralelo de pedidos
public class ThreadFacade {
    
    private ThreadManager threadManager;
    
    public ThreadFacade(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }
    
    //Procesa un pedido de forma asíncrona usando hilos paralelos
    public void procesarPedidoAsync(Cliente cliente, Producto producto, int cantidad) {
        if (threadManager != null) {
            threadManager.procesarPedidoAsync(cliente, producto, cantidad);
        } else {
            System.out.println("[ThreadFacade] Error: ThreadManager no está inicializado");
        }
    }
    
    //Espera a que todos los pedidos en cola sean procesados completamente
    public void esperarProcesamientoCompleto() {
        if (threadManager != null) {
            threadManager.esperarProcesamientoCompleto();
        } else {
            System.out.println("[ThreadFacade] Error: ThreadManager no está inicializado");
        }
    }
    
    //Obtiene un comprobante de la cola si está disponible (no bloqueante)
    public Comprobante obtenerComprobante() {
        if (threadManager != null) {
            return threadManager.obtenerComprobante();
        }
        return null;
    }
    
    //Verifica si el ThreadManager está inicializado
    public boolean estaInicializado() {
        return threadManager != null;
    }
}

