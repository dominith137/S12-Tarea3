package org.usil.controller;

import org.usil.model.Cliente;
import org.usil.model.Pedido;
import org.usil.model.Producto;

//Controller que encapsula la creación de entidades del dominio (modelos)
public class ModelController {
    
    //Crea una instancia de Cliente con el nombre especificado
    public Cliente crearCliente(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("[ModelController] Advertencia: Nombre de cliente vacío o null");
            return null;
        }
        return new Cliente(nombre);
    }
    
    //Crea una instancia de Producto con los datos especificados
    public Producto crearProducto(String nombre, double precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("[ModelController] Advertencia: Nombre de producto vacío o null");
            return null;
        }
        if (precio < 0) {
            System.out.println("[ModelController] Advertencia: Precio negativo, se establecerá en 0");
            precio = 0;
        }
        if (stock < 0) {
            System.out.println("[ModelController] Advertencia: Stock negativo, se establecerá en 0");
            stock = 0;
        }
        return new Producto(nombre, precio, stock);
    }
    
    //Crea una instancia de Pedido con los datos especificados
    public Pedido crearPedido(Cliente cliente, Producto producto, int cantidad) {
        if (cliente == null) {
            System.out.println("[ModelController] Error: Cliente no puede ser null");
            return null;
        }
        if (producto == null) {
            System.out.println("[ModelController] Error: Producto no puede ser null");
            return null;
        }
        if (cantidad <= 0) {
            System.out.println("[ModelController] Error: Cantidad debe ser mayor a 0");
            return null;
        }
        return new Pedido(cliente, producto, cantidad);
    }
}

