package org.usil.controller;

import org.usil.adapter.FacturaAdapter;
import org.usil.facade.PedidoFacade;
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

        PedidoFacade pedidoFacade = new PedidoFacade(
            stockService,
            impuestoService,
            pedidoService,
            facturaAdapter,
            comprobanteService
        );

        PedidoController pedidoController = new PedidoController(pedidoFacade);

        this.view = new PedidoView(pedidoController, pedidoFacade, pedidoRepository);
    }
    
    public void iniciar() {
        view.mostrarBienvenida();
        view.mostrarEjemplos();
    }
}

