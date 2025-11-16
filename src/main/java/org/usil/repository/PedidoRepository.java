package org.usil.repository;

import org.usil.model.Pedido;
import java.util.ArrayList;
import java.util.List;

//Repositorio responsable de guardar y recuperar pedidos
public class PedidoRepository {
    
    private List<Pedido> pedidos;
    
    public PedidoRepository() {
        this.pedidos = new ArrayList<>();
    }
    
    //Guarda un pedido en el repositorio y retorna true al guardarse
    public boolean guardar(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
            return true;
        }
        return false;
    }
    
    //Recupera todos los pedidos guardados y lo retorna como una lista
    public List<Pedido> obtenerTodos() {
        return new ArrayList<>(pedidos);
    }
    

    //Busca un pedido por su número de factura
    public Pedido buscarPorNumeroFactura(String numeroFactura) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumeroFactura() != null && 
                pedido.getNumeroFactura().equals(numeroFactura)) {
                return pedido;
            }
        }
        return null;
    }
    
    //Obtiene y retorna la cantidad total de pedidos guardados
    public int contar() {
        return pedidos.size();
    }
}

