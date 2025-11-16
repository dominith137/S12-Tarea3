package org.usil.service;

import org.usil.model.Producto;

/**
 * Servicio responsable de validar el stock de productos
 */
public class StockService {
    
    /**
     * Valida que la cantidad solicitada sea positiva y que haya stock disponible
     * @param producto El producto a validar
     * @param cantidad La cantidad solicitada
     * @return true si la validación es exitosa, false en caso contrario
     */
    public boolean validarStock(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        return producto.getStock() >= cantidad;
    }
}

