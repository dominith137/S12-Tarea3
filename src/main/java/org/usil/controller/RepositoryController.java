package org.usil.controller;

import org.usil.model.Pedido;
import org.usil.repository.PedidoRepository;
import java.util.List;

//Controller que encapsula el acceso y operaciones sobre el repositorio de pedidos
public class RepositoryController {
    
    private PedidoRepository pedidoRepository;
    
    public RepositoryController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    
    //Guarda un pedido en el repositorio
    public boolean guardarPedido(Pedido pedido) {
        if (pedidoRepository != null) {
            return pedidoRepository.guardar(pedido);
        }
        return false;
    }
    
    //Obtiene todos los pedidos guardados
    public List<Pedido> obtenerTodosLosPedidos() {
        if (pedidoRepository != null) {
            return pedidoRepository.obtenerTodos();
        }
        return null;
    }
    
    //Busca un pedido por su número de factura
    public Pedido buscarPedidoPorNumeroFactura(String numeroFactura) {
        if (pedidoRepository != null && numeroFactura != null) {
            return pedidoRepository.buscarPorNumeroFactura(numeroFactura);
        }
        return null;
    }
    
    //Obtiene la cantidad total de pedidos guardados
    public int contarPedidos() {
        if (pedidoRepository != null) {
            return pedidoRepository.contar();
        }
        return 0;
    }
    
    //Obtiene el PedidoRepository directamente (solo si es necesario para casos especiales)
    public PedidoRepository getPedidoRepository() {
        return pedidoRepository;
    }
}

