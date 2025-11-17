package org.usil.observer;

import org.usil.model.Pedido;
import org.usil.model.Comprobante;

//Observador que notifica al cliente sobre pedidos y facturas generadas
public class ClienteObserver implements Observer {
    
    @Override
    public void onPedidoRegistrado(Pedido pedido) {
        String nombreCliente = pedido.getCliente().getNombre();
        String nombreProducto = pedido.getProducto().getNombre();
        int cantidad = pedido.getCantidad();
        
        System.out.println("[ClienteObserver] Cliente " + nombreCliente + 
                         " ha sido notificado: Su pedido de " + cantidad + 
                         " unidad(es) de " + nombreProducto + " ha sido registrado");
    }
    
    @Override
    public void onFacturaGenerada(Comprobante comprobante) {
        String nombreCliente = comprobante.getCliente().getNombre();
        String numeroFactura = comprobante.getNumeroFactura();
        double total = comprobante.getTotal();
        
        System.out.println("[ClienteObserver] Cliente " + nombreCliente + 
                         " ha sido notificado: Su factura " + numeroFactura + 
                         " por S/ " + String.format("%.2f", total) + " ha sido generada");
    }
}

