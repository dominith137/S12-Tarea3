package org.usil.service;

import org.usil.model.Comprobante;
import org.usil.model.Pedido;


public class ComprobanteService {

    public Comprobante generarComprobante(Pedido pedido) {
        return new Comprobante(
            pedido.getNumeroFactura(),
            pedido.getCliente(),
            pedido.getProducto(),
            pedido.getCantidad(),
            pedido.getSubtotal(),
            pedido.getIgv(),
            pedido.getTotal()
        );
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

