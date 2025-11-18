package org.usil.controller;

import org.usil.facade.ThreadFacade;
import org.usil.service.ComprobanteService;
import org.usil.service.FacturaService;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import org.usil.thread.ThreadManager;

//Controller que orquesta la creación, inicialización y gestión del ciclo de vida de threads
public class ThreadController {
    
    private ThreadManager threadManager;
    private ThreadFacade threadFacade;
    
    public ThreadController(StockService stockService,
                           ImpuestoService impuestoService,
                           PedidoService pedidoService,
                           FacturaService facturaService,
                           ComprobanteService comprobanteService) {
        System.out.println("[ThreadController] Creando ThreadManager...");
        this.threadManager = new ThreadManager(
            stockService,
            impuestoService,
            pedidoService,
            facturaService,
            comprobanteService
        );
        
        System.out.println("[ThreadController] Creando ThreadFacade...");
        this.threadFacade = new ThreadFacade(threadManager);
    }
    
    //Inicia todos los hilos de procesamiento paralelo
    public void iniciarHilos() {
        if (threadManager != null) {
            threadManager.iniciarHilos();
        } else {
            System.out.println("[ThreadController] Error: ThreadManager no está inicializado");
        }
    }
    
    //Detiene todos los hilos de forma segura
    public void detenerHilos() {
        if (threadManager != null) {
            threadManager.detenerHilos();
        } else {
            System.out.println("[ThreadController] Error: ThreadManager no está inicializado");
        }
    }
    
    //Obtiene el ThreadFacade para acceso a funcionalidades de procesamiento paralelo
    public ThreadFacade getThreadFacade() {
        return threadFacade;
    }
    
    //Obtiene el ThreadManager directamente (solo si es necesario para casos especiales)
    public ThreadManager getThreadManager() {
        return threadManager;
    }
    
    //Verifica si el ThreadController está inicializado correctamente
    public boolean estaInicializado() {
        return threadManager != null && threadFacade != null;
    }
}

