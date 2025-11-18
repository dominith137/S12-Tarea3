package org.usil.controller;

import org.usil.facade.PedidoFacade;
import org.usil.model.Cliente;
import org.usil.model.Comprobante;
import org.usil.model.Producto;

public class PedidoController {
    
    private PedidoFacade pedidoFacade;
    private ModelController modelController;
    
    public PedidoController(PedidoFacade pedidoFacade, ModelController modelController) {
        this.pedidoFacade = pedidoFacade;
        this.modelController = modelController;
    }

    public Comprobante registrarPedido(String nombreCliente, Producto producto, int cantidad) {
        Cliente cliente = modelController.crearCliente(nombreCliente);
        if (cliente == null) {
            return null;
        }
        Comprobante comprobante = pedidoFacade.procesarPedido(cliente, producto, cantidad);
        return comprobante;
    }
}

