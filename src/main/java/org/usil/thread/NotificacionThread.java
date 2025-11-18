package org.usil.thread;

import org.usil.model.Comprobante;
import java.util.concurrent.BlockingQueue;

//Hilo que envía notificaciones y actualiza inventario: procesa comprobantes generados
public class NotificacionThread extends Thread {
    
    private BlockingQueue<Comprobante> colaComprobantes;
    private volatile boolean activo = true;
    
    public NotificacionThread(BlockingQueue<Comprobante> colaComprobantes) {
        this.colaComprobantes = colaComprobantes;
        this.setName("Hilo-Notificaciones");
    }
    
    @Override
    public void run() {
        System.out.println("[Thread] " + Thread.currentThread().getName() + " iniciado");
        
        while (activo || !colaComprobantes.isEmpty()) {
            try {
                Comprobante comprobante = colaComprobantes.take();
                
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " procesando notificaciones para factura: " + 
                                 comprobante.getNumeroFactura());
                
                // Simular envío de notificaciones y actualización de inventario
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " - Notificaciones enviadas para factura " + 
                                 comprobante.getNumeroFactura());
                System.out.println("[Thread] " + Thread.currentThread().getName() + 
                                 " - Inventario actualizado para producto: " + 
                                 comprobante.getProducto().getNombre());
                
                Thread.sleep(80); // Simula tiempo de envío de notificaciones
                
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

