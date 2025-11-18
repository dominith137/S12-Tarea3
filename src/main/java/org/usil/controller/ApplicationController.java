package org.usil.controller;

import org.usil.adapter.FacturaAdapter;
import org.usil.facade.PedidoFacade;
import org.usil.facade.ThreadFacade;
import org.usil.legacy.LegacyBillingSystem;
import org.usil.repository.PedidoRepository;
import org.usil.service.ComprobanteService;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import org.usil.strategy.IGV18Strategy;
import org.usil.view.PedidoView;

public class ApplicationController {
    
    private PedidoView view;
    private ThreadController threadController;
    private ObserverController observerController;
    
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

        //Crear ModelController para gestión de entidades
        ModelController modelController = new ModelController();

        //Crear RepositoryController para encapsular acceso al repository
        RepositoryController repositoryController = new RepositoryController(pedidoRepository);

        //Configurar observers usando ObserverController
        observerController = new ObserverController();
        observerController.configurarObservers(pedidoService, comprobanteService);

        PedidoFacade pedidoFacade = new PedidoFacade(
            stockService,
            impuestoService,
            pedidoService,
            facturaAdapter,
            comprobanteService,
            modelController
        );

        //Crear ThreadController para procesamiento paralelo
        threadController = new ThreadController(
            stockService,
            impuestoService,
            pedidoService,
            facturaAdapter,
            comprobanteService,
            modelController
        );
        
        //Configurar procesamiento paralelo en el facade usando ThreadFacade
        ThreadFacade threadFacade = threadController.getThreadFacade();
        pedidoFacade.configurarProcesamientoParalelo(threadFacade);
        
        //Iniciar hilos de procesamiento paralelo
        threadController.iniciarHilos();

        PedidoController pedidoController = new PedidoController(pedidoFacade, modelController);

        this.view = new PedidoView(pedidoController, pedidoFacade, repositoryController, threadFacade, modelController);
    }
    
    public void finalizar() {
        if (threadController != null) {
            threadController.detenerHilos();
        }
    }
    
    public void iniciar() {
        view.mostrarBienvenida();
        view.mostrarEjemplos();
    }
}

