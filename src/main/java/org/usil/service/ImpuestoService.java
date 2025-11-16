package org.usil.service;

import org.usil.model.Pedido;
import org.usil.strategy.ImpuestoStrategy;

public class ImpuestoService {
    
    private ImpuestoStrategy estrategia;

    public ImpuestoService(ImpuestoStrategy estrategia) {
        this.estrategia = estrategia;
    }
    
    public void setEstrategia(ImpuestoStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void calcularImpuestos(Pedido pedido) {
        double precioUnitario = pedido.getProducto().getPrecio();
        int cantidad = pedido.getCantidad();
        
        double subtotal = precioUnitario * cantidad;
        double igv = estrategia.calcular(subtotal);
        double total = subtotal + igv;
        
        pedido.setSubtotal(subtotal);
        pedido.setIgv(igv);
        pedido.setTotal(total);
    }
}

