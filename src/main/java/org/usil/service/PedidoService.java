package org.usil.service;

import org.usil.model.Pedido;
import org.usil.model.Producto;
import org.usil.observer.PedidoObservable;
import org.usil.repository.PedidoRepository;

//Servicio que gestiona pedidos y notifica a observadores cuando se registra un pedido
public class PedidoService extends PedidoObservable {

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
        boolean guardado = pedidoRepository.guardar(pedido);
        
        // Notificar a los observadores que se registró un pedido
        if (guardado) {
            notificarPedidoRegistrado(pedido);
        }
        
        return guardado;
    }
}

