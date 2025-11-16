package org.usil.service;

import org.usil.model.Pedido;
import org.usil.model.Producto;
import org.usil.repository.PedidoRepository;

public class PedidoService {

    private PedidoRepository pedidoRepository;
    
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public boolean registrarPedido(Pedido pedido) {
        // Actualizar el stock del producto
        Producto producto = pedido.getProducto();
        int stockActual = producto.getStock();
        int cantidadPedida = pedido.getCantidad();
        producto.setStock(stockActual - cantidadPedida);

        // Guardar el pedido usando el repositorio
        return pedidoRepository.guardar(pedido);
    }
}

