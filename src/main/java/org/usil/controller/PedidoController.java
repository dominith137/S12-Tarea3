package org.usil.controller;

import org.usil.facade.PedidoFacade;
import org.usil.model.Cliente;
import org.usil.model.Comprobante;
import org.usil.model.Producto;


public class PedidoController {
    
    private PedidoFacade pedidoFacade;
    
    public PedidoController(PedidoFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    public Comprobante registrarPedido(String nombreCliente, Producto producto, int cantidad) {
        Cliente cliente = new Cliente(nombreCliente);
        Comprobante comprobante = pedidoFacade.procesarPedido(cliente, producto, cantidad);
        return comprobante;
    }
}

