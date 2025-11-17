package org.usil.observer;

import org.usil.model.Pedido;
import org.usil.model.Comprobante;

//Interfaz Observer del patrón Observer - Define métodos que serán llamados cuando el observable notifique cambios
public interface Observer {
    
    //Metodo llamado cuando se registra un nuevo pedido
    void onPedidoRegistrado(Pedido pedido);
    
    //Metodo llamado cuando se genera una nueva factura/comprobante
    void onFacturaGenerada(Comprobante comprobante);
}

