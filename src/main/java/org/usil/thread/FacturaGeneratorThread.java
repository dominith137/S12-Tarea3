package org.usil.thread;

import org.usil.model.Comprobante;
import org.usil.model.Pedido;
import org.usil.service.ComprobanteService;
import org.usil.service.FacturaService;
import java.util.concurrent.BlockingQueue;

//Hilo que genera facturas: toma pedidos registrados, genera factura y comprobante
public class FacturaGeneratorThread extends Thread {
    
    private BlockingQueue<Pedido> colaPedidosRegistrados;
    private BlockingQueue<Comprobante> colaComprobantes;
    private FacturaService facturaService;
    private ComprobanteService comprobanteService;
    private volatile boolean activo = true;
    
    public FacturaGeneratorThread(BlockingQueue<Pedido> colaPedidosRegistrados,
                                 BlockingQueue<Comprobante> colaComprobantes,
                                 FacturaService facturaService,
                                 ComprobanteService comprobanteService) {
        this.colaPedidosRegistrados = colaPedidosRegistrados;
        this.colaComprobantes = colaComprobantes;
        this.facturaService = facturaService;
        this.comprobanteService = comprobanteService;
        this.setName("Hilo-Generador-Facturas");
    }
    
    @Override
    public void run() {
        System.out.println("[Thread] " + Thread.currentThread().getName() + " iniciado");
        
        while (activo || !colaPedidosRegistrados.isEmpty()) {
            try {
                Pedido pedido = colaPedidosRegistrados.take();
                
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " generando factura para pedido de: " + pedido.getCliente().getNombre());
                
                // 1. Generar factura usando el servicio de facturación
                String numeroFactura = facturaService.generarFactura(pedido);
                pedido.setNumeroFactura(numeroFactura);
                
                // 2. Generar comprobante
                Comprobante comprobante = comprobanteService.generarComprobante(pedido);
                
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " - Factura " + numeroFactura + " generada exitosamente");
                
                colaComprobantes.put(comprobante);
                
                Thread.sleep(150); // Simula tiempo de generación de factura
                
            } catch (InterruptedException e) {
                System.out.println("[Thread] " + Thread.currentThread().getName() + " interrumpido");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("[Thread] " + Thread.currentThread().getName() + " finalizado");
    }
    
    public void detener() {
        this.activo = false;
        this.interrupt();
    }
}

