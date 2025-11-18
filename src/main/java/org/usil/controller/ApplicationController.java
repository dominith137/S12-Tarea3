package org.usil.controller;

import org.usil.adapter.FacturaAdapter;
import org.usil.facade.PedidoFacade;
import org.usil.legacy.LegacyBillingSystem;
import org.usil.observer.ClienteObserver;
import org.usil.observer.InventarioObserver;
import org.usil.observer.LogObserver;
import org.usil.repository.PedidoRepository;
import org.usil.service.ComprobanteService;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import org.usil.strategy.IGV18Strategy;
import org.usil.thread.ThreadManager;
import org.usil.view.PedidoView;

public class ApplicationController {
    
    private PedidoView view;
    private ThreadManager threadManager;
    
    public ApplicationController() {
        inicializarSistema();
    }
    
    private void inicializarSistema() {

        StockService stockService = new StockService();
        IGV18Strategy estrategiaDefault = new IGV18Strategy();
        ImpuestoService impuestoService = new ImpuestoService(estrategiaDefault);
        PedidoRepository pedidoRepository = new PedidoRepository();
        PedidoService pedidoService = new PedidoService(pedidoRepository);
        ComprobanteService comprobanteService = new ComprobanteService();

        LegacyBillingSystem legacySystem = new LegacyBillingSystem();
        FacturaAdapter facturaAdapter = new FacturaAdapter(legacySystem);

        // Registrar observadores en los servicios observables
        ClienteObserver clienteObserver = new ClienteObserver();
        InventarioObserver inventarioObserver = new InventarioObserver();
        LogObserver logObserver = new LogObserver();
        
        pedidoService.agregarObserver(clienteObserver);
        pedidoService.agregarObserver(inventarioObserver);
        pedidoService.agregarObserver(logObserver);
        
        comprobanteService.agregarObserver(clienteObserver);
        comprobanteService.agregarObserver(inventarioObserver);
        comprobanteService.agregarObserver(logObserver);

        PedidoFacade pedidoFacade = new PedidoFacade(
            stockService,
            impuestoService,
            pedidoService,
            facturaAdapter,
            comprobanteService
        );

        // Crear ThreadManager para procesamiento paralelo
        threadManager = new ThreadManager(
            stockService,
            impuestoService,
            pedidoService,
            facturaAdapter,
            comprobanteService
        );
        
        // Configurar procesamiento paralelo en el facade
        pedidoFacade.configurarProcesamientoParalelo(threadManager);
        
        // Iniciar hilos de procesamiento paralelo
        threadManager.iniciarHilos();

        PedidoController pedidoController = new PedidoController(pedidoFacade);

        this.view = new PedidoView(pedidoController, pedidoFacade, pedidoRepository, threadManager);
    }
    
    public void finalizar() {
        if (threadManager != null) {
            threadManager.detenerHilos();
        }
    }
    
    public void iniciar() {
        view.mostrarBienvenida();
        view.mostrarEjemplos();
    }
}

