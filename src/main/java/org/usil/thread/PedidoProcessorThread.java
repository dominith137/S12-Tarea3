package org.usil.thread;

import org.usil.controller.ModelController;
import org.usil.model.Pedido;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import java.util.concurrent.BlockingQueue;

//Hilo que procesa pedidos: valida stock, crea pedido, calcula impuestos y registra el pedido
public class PedidoProcessorThread extends Thread {
    
    private BlockingQueue<PedidoRequest> colaPedidos;
    private BlockingQueue<Pedido> colaPedidosRegistrados;
    private StockService stockService;
    private ImpuestoService impuestoService;
    private PedidoService pedidoService;
    private ModelController modelController;
    private volatile boolean activo = true;
    
    public PedidoProcessorThread(BlockingQueue<PedidoRequest> colaPedidos,
                                BlockingQueue<Pedido> colaPedidosRegistrados,
                                StockService stockService,
                                ImpuestoService impuestoService,
                                PedidoService pedidoService,
                                ModelController modelController) {
        this.colaPedidos = colaPedidos;
        this.colaPedidosRegistrados = colaPedidosRegistrados;
        this.stockService = stockService;
        this.impuestoService = impuestoService;
        this.pedidoService = pedidoService;
        this.modelController = modelController;
        this.setName("Hilo-Procesador-Pedidos");
    }
    
    @Override
    public void run() {
        System.out.println("[Thread] " + Thread.currentThread().getName() + " iniciado");
        
        while (activo || !colaPedidos.isEmpty()) {
            try {
                PedidoRequest request = colaPedidos.take();
                
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " procesando pedido para: " + request.getCliente().getNombre());
                
                //1. Validar stock
                if (!stockService.validarStock(request.getProducto(), request.getCantidad())) {
                    System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                     " - Stock insuficiente para " + request.getProducto().getNombre());
                    continue;
                }
                
                //2. Crear el pedido usando ModelController
                Pedido pedido = modelController.crearPedido(
                    request.getCliente(), 
                    request.getProducto(), 
                    request.getCantidad()
                );
                if (pedido == null) {
                    System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                     " - Error al crear pedido");
                    continue;
                }
                
                // 3. Calcular impuestos
                impuestoService.calcularImpuestos(pedido);
                
                // 4. Registrar el pedido
                boolean registrado = pedidoService.registrarPedido(pedido);
                
                if (registrado) {
                    System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                     " - Pedido registrado exitosamente");
                    colaPedidosRegistrados.put(pedido);
                } else {
                    System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                     " - Error al registrar pedido");
                }
                
                Thread.sleep(100); // Simula tiempo de procesamiento
                
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

