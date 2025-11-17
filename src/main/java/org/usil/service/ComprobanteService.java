package org.usil.service;

import org.usil.model.Comprobante;
import org.usil.model.Pedido;
import org.usil.observer.PedidoObservable;

//Servicio que genera comprobantes y notifica a observadores cuando se genera una factura
public class ComprobanteService extends PedidoObservable {

    public Comprobante generarComprobante(Pedido pedido) {
        Comprobante comprobante = new Comprobante(
            pedido.getNumeroFactura(),
            pedido.getCliente(),
            pedido.getProducto(),
            pedido.getCantidad(),
            pedido.getSubtotal(),
            pedido.getIgv(),
            pedido.getTotal()
        );
        
        // Notificar a los observadores que se generó una factura
        notificarFacturaGenerada(comprobante);
        
        return comprobante;
    }

    public void mostrarComprobante(Comprobante comprobante) {
        System.out.println("=========================================");
        System.out.println("         COMPROBANTE DE FACTURA         ");
        System.out.println("=========================================");
        System.out.println("Número de Factura: " + comprobante.getNumeroFactura());
        System.out.println("Cliente: " + comprobante.getCliente().getNombre());
        System.out.println("Producto: " + comprobante.getProducto().getNombre());
        System.out.println("Cantidad: " + comprobante.getCantidad());
        System.out.println("Precio Unitario: S/ " + comprobante.getProducto().getPrecio());
        System.out.println("----------------------------------------");
        System.out.println("Subtotal: S/ " + String.format("%.2f", comprobante.getSubtotal()));
        System.out.println("IGV (18%): S/ " + String.format("%.2f", comprobante.getIgv()));
        System.out.println("----------------------------------------");
        System.out.println("TOTAL: S/ " + String.format("%.2f", comprobante.getTotal()));
        System.out.println("=========================================");
    }
}

