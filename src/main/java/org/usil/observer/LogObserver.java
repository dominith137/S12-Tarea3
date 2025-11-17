package org.usil.observer;

import org.usil.model.Pedido;
import org.usil.model.Comprobante;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Observador que registra eventos en un log del sistema
public class LogObserver implements Observer {
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void onPedidoRegistrado(Pedido pedido) {
        String timestamp = LocalDateTime.now().format(formatter);
        String nombreCliente = pedido.getCliente().getNombre();
        String nombreProducto = pedido.getProducto().getNombre();
        int cantidad = pedido.getCantidad();
        
        System.out.println("[LogObserver] [" + timestamp + "] EVENTO: Pedido registrado - " +
                         "Cliente: " + nombreCliente + ", Producto: " + nombreProducto + 
                         ", Cantidad: " + cantidad);
    }
    
    @Override
    public void onFacturaGenerada(Comprobante comprobante) {
        String timestamp = LocalDateTime.now().format(formatter);
        String numeroFactura = comprobante.getNumeroFactura();
        String nombreCliente = comprobante.getCliente().getNombre();
        double total = comprobante.getTotal();
        
        System.out.println("[LogObserver] [" + timestamp + "] EVENTO: Factura generada - " +
                         "Número: " + numeroFactura + ", Cliente: " + nombreCliente + 
                         ", Total: S/ " + String.format("%.2f", total));
    }
}

