package org.usil.observer;

import org.usil.model.Pedido;
import org.usil.model.Comprobante;
import java.util.ArrayList;
import java.util.List;

//Implementación base del patrón Observable - Gestiona la lista de observadores y las notificaciones
public class PedidoObservable implements Observable {
    
    private List<Observer> observadores;
    
    public PedidoObservable() {
        this.observadores = new ArrayList<>();
    }
    
    @Override
    public void agregarObserver(Observer observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
            System.out.println("[Observer] Observador agregado: " + observer.getClass().getSimpleName());
        }
    }
    
    @Override
    public void eliminarObserver(Observer observer) {
        if (observer != null) {
            observadores.remove(observer);
            System.out.println("[Observer] Observador eliminado: " + observer.getClass().getSimpleName());
        }
    }
    
    @Override
    public void notificarPedidoRegistrado(Pedido pedido) {
        if (pedido != null) {
            System.out.println("[Observer] Notificando a " + observadores.size() + " observador(es) sobre pedido registrado");
            for (Observer observer : observadores) {
                observer.onPedidoRegistrado(pedido);
            }
        }
    }
    
    @Override
    public void notificarFacturaGenerada(Comprobante comprobante) {
        if (comprobante != null) {
            System.out.println("[Observer] Notificando a " + observadores.size() + " observador(es) sobre factura generada");
            for (Observer observer : observadores) {
                observer.onFacturaGenerada(comprobante);
            }
        }
    }
    
    //Obtiene y retorna la cantidad de observadores registrados
    public int cantidadObservadores() {
        return observadores.size();
    }
}

