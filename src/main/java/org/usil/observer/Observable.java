package org.usil.observer;

//Interfaz Observable del patrón Observer - Define métodos para gestionar observadores
public interface Observable {
    
    //Registra un observador para recibir notificaciones
    void agregarObserver(Observer observer);
    
    //Elimina un observador de la lista de notificaciones
    void eliminarObserver(Observer observer);
    
    //Notifica a todos los observadores que se registró un pedido
    void notificarPedidoRegistrado(org.usil.model.Pedido pedido);
    
    //Notifica a todos los observadores que se generó una factura
    void notificarFacturaGenerada(org.usil.model.Comprobante comprobante);
}

