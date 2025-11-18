package org.usil.thread;

import org.usil.model.Cliente;
import org.usil.model.Comprobante;
import org.usil.model.Pedido;
import org.usil.model.Producto;
import org.usil.service.ComprobanteService;
import org.usil.service.FacturaService;
import org.usil.service.ImpuestoService;
import org.usil.service.PedidoService;
import org.usil.service.StockService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//Gestor de hilos que coordina el procesamiento paralelo de pedidos, facturas y notificaciones
public class ThreadManager {
    
    private BlockingQueue<PedidoRequest> colaPedidos;
    private BlockingQueue<Pedido> colaPedidosRegistrados;
    private BlockingQueue<Comprobante> colaComprobantes;
    
    private PedidoProcessorThread hiloProcesadorPedidos;
    private FacturaGeneratorThread hiloGeneradorFacturas;
    private NotificacionThread hiloNotificaciones;
    
    public ThreadManager(StockService stockService,
                        ImpuestoService impuestoService,
                        PedidoService pedidoService,
                        FacturaService facturaService,
                        ComprobanteService comprobanteService) {
        // Inicializar colas thread-safe
        this.colaPedidos = new LinkedBlockingQueue<>();
        this.colaPedidosRegistrados = new LinkedBlockingQueue<>();
        this.colaComprobantes = new LinkedBlockingQueue<>();
        
        // Crear hilos
        this.hiloProcesadorPedidos = new PedidoProcessorThread(
            colaPedidos, colaPedidosRegistrados, 
            stockService, impuestoService, pedidoService
        );
        
        this.hiloGeneradorFacturas = new FacturaGeneratorThread(
            colaPedidosRegistrados, colaComprobantes,
            facturaService, comprobanteService
        );
        
        this.hiloNotificaciones = new NotificacionThread(colaComprobantes);
    }
    
    //Inicia todos los hilos para procesamiento paralelo
    public void iniciarHilos() {
        System.out.println("[ThreadManager] Iniciando hilos de procesamiento paralelo...");
        hiloProcesadorPedidos.start();
        hiloGeneradorFacturas.start();
        hiloNotificaciones.start();
        System.out.println("[ThreadManager] Todos los hilos iniciados y listos para procesar");
    }
    
    //Agrega un pedido a la cola para ser procesado por los hilos
    public void procesarPedidoAsync(Cliente cliente, Producto producto, int cantidad) {
        PedidoRequest request = new PedidoRequest(cliente, producto, cantidad);
        try {
            colaPedidos.put(request);
            System.out.println("[ThreadManager] Pedido agregado a la cola para procesamiento paralelo");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[ThreadManager] Error al agregar pedido a la cola");
        }
    }
    
    //Detiene todos los hilos de forma segura
    public void detenerHilos() {
        System.out.println("[ThreadManager] Deteniendo hilos...");
        hiloProcesadorPedidos.detener();
        hiloGeneradorFacturas.detener();
        hiloNotificaciones.detener();
        
        try {
            hiloProcesadorPedidos.join();
            hiloGeneradorFacturas.join();
            hiloNotificaciones.join();
            System.out.println("[ThreadManager] Todos los hilos detenidos correctamente");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[ThreadManager] Error al esperar que los hilos terminen");
        }
    }
    
    //Espera a que todas las colas se vacíen y los hilos terminen de procesar
    public void esperarProcesamientoCompleto() {
        System.out.println("[ThreadManager] Esperando que se completen todos los procesos...");
        
        // Esperar a que se procesen todos los pedidos
        while (!colaPedidos.isEmpty() || !colaPedidosRegistrados.isEmpty() || !colaComprobantes.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Dar tiempo adicional para que los hilos terminen de procesar
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[ThreadManager] Procesamiento completo finalizado");
    }
    
    //Obtiene un comprobante de la cola si está disponible (no bloqueante)
    public Comprobante obtenerComprobante() {
        return colaComprobantes.poll();
    }
}

