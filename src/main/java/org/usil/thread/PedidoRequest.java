package org.usil.thread;

import org.usil.model.Cliente;
import org.usil.model.Producto;

//Clase que representa una solicitud de pedido para ser procesada por un hilo
public class PedidoRequest {
    
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    
    public PedidoRequest(Cliente cliente, Producto producto, int cantidad) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public int getCantidad() {
        return cantidad;
    }
}

