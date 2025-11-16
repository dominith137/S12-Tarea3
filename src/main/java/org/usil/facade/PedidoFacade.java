package org.usil.facade;

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
    
    public PedidoFacade(StockService stockService, 
                       ImpuestoService impuestoService,
                       PedidoService pedidoService,
                       FacturaService facturaService,
                       ComprobanteService comprobanteService) {
        this.stockService = stockService;
        this.impuestoService = impuestoService;
        this.pedidoService = pedidoService;
        this.facturaService = facturaService;
        this.comprobanteService = comprobanteService;
    }
    
    //Permite elegir qué estrategia de impuestos usar antes de procesar el pedido
    public void establecerEstrategiaImpuesto(ImpuestoStrategy estrategia) {
        impuestoService.setEstrategia(estrategia);
    }

    public Comprobante procesarPedido(Cliente cliente, Producto producto, int cantidad) {
        // 1. Validar stock
        if (!stockService.validarStock(producto, cantidad)) {
            return null;
        }
        
        // 2. Crear el pedido
        Pedido pedido = new Pedido(cliente, producto, cantidad);
        
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

