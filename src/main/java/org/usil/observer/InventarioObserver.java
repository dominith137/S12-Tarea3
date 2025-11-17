package org.usil.observer;

import org.usil.model.Pedido;
import org.usil.model.Comprobante;

//Observador que actualiza y monitorea el inventario cuando se registran pedidos
public class InventarioObserver implements Observer {
    
    @Override
    public void onPedidoRegistrado(Pedido pedido) {
        String nombreProducto = pedido.getProducto().getNombre();
        int cantidad = pedido.getCantidad();
        int stockActual = pedido.getProducto().getStock();
        
        System.out.println("[InventarioObserver] Inventario actualizado: Se descontaron " + 
                         cantidad + " unidad(es) de " + nombreProducto + 
                         ". Stock restante: " + stockActual);
    }
    
    @Override
    public void onFacturaGenerada(Comprobante comprobante) {
        String nombreProducto = comprobante.getProducto().getNombre();
        int cantidad = comprobante.getCantidad();
        
        System.out.println("[InventarioObserver] Factura generada para " + cantidad + 
                         " unidad(es) de " + nombreProducto + " - Inventario ya actualizado");
    }
}

