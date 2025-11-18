package org.usil.facade;

import org.usil.controller.ModelController;
import org.usil.model.Cliente;
import org.usil.model.Comprobante;
import org.usil.model.Pedido;
import org.usil.model.Producto;
import org.usil.service.ComprobanteService;
import org.usil.service.FacturaService;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import org.usil.strategy.ImpuestoStrategy;

public class PedidoFacade {
    
    private StockService stockService;
    private ImpuestoService impuestoService;
    private PedidoService pedidoService;
    private FacturaService facturaService;
    private ComprobanteService comprobanteService;
    private ThreadFacade threadFacade;
    private ModelController modelController;
    private boolean usarProcesamientoParalelo;
    
    public PedidoFacade(StockService stockService, 
                       ImpuestoService impuestoService,
                       PedidoService pedidoService,
                       FacturaService facturaService,
                       ComprobanteService comprobanteService,
                       ModelController modelController) {
        this.stockService = stockService;
        this.impuestoService = impuestoService;
        this.pedidoService = pedidoService;
        this.facturaService = facturaService;
        this.comprobanteService = comprobanteService;
        this.modelController = modelController;
        this.usarProcesamientoParalelo = false;
    }
    
    //Configura el ThreadFacade para procesamiento paralelo
    public void configurarProcesamientoParalelo(ThreadFacade threadFacade) {
        this.threadFacade = threadFacade;
        this.usarProcesamientoParalelo = true;
    }
    
    //Permite elegir qué estrategia de impuestos usar antes de procesar el pedido
    public void establecerEstrategiaImpuesto(ImpuestoStrategy estrategia) {
        impuestoService.setEstrategia(estrategia);
    }

    public Comprobante procesarPedido(Cliente cliente, Producto producto, int cantidad) {
        //Si está configurado para procesamiento paralelo, usar hilos
        if (usarProcesamientoParalelo && threadFacade != null) {
            System.out.println("[PedidoFacade] Procesando pedido de forma paralela usando hilos");
            threadFacade.procesarPedidoAsync(cliente, producto, cantidad);
            //Retornar null ya que el procesamiento es asíncrono
            //El comprobante se generará en los hilos
            return null;
        }
        
        //Procesamiento síncrono tradicional
        //1. Validar stock
        if (!stockService.validarStock(producto, cantidad)) {
            return null;
        }
        
        //2. Crear el pedido usando ModelController
        Pedido pedido = modelController.crearPedido(cliente, producto, cantidad);
        if (pedido == null) {
            return null;
        }
        
        // 3. Calcular impuestos (subtotal, IGV, total)
        impuestoService.calcularImpuestos(pedido);
        
        // 4. Registrar el pedido
        boolean registrado = pedidoService.registrarPedido(pedido);
        if (!registrado) {
            return null;
        }
        
        // 5. Generar factura usando el servicio de facturación (adapter)
        String numeroFactura = facturaService.generarFactura(pedido);
        pedido.setNumeroFactura(numeroFactura);
        
        // 6. Generar y retornar el comprobante
        Comprobante comprobante = comprobanteService.generarComprobante(pedido);
        return comprobante;
    }

    public void mostrarComprobante(Comprobante comprobante) {
        if (comprobante != null) {
            comprobanteService.mostrarComprobante(comprobante);
        } else {
            System.out.println("No hay comprobante para mostrar");
        }
    }
}

