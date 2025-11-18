package org.usil.facade;

import org.usil.observer.Observable;
import org.usil.observer.Observer;
import org.usil.service.ComprobanteService;
import org.usil.service.PedidoService;
import java.util.ArrayList;
import java.util.List;

//Facade que simplifica la configuración y gestión de observers en los servicios observables
public class ObserverFacade {
    
    private List<Observable> serviciosObservables;
    
    public ObserverFacade() {
        this.serviciosObservables = new ArrayList<>();
    }
    
    //Registra un servicio observable para que pueda recibir observers
    public void registrarServicioObservable(Observable servicio) {
        if (servicio != null && !serviciosObservables.contains(servicio)) {
            serviciosObservables.add(servicio);
            System.out.println("[ObserverFacade] Servicio observable registrado: " + 
                             servicio.getClass().getSimpleName());
        }
    }
    
    //Registra un observer en todos los servicios observables registrados
    public void registrarObserverEnTodos(Observer observer) {
        if (observer != null) {
            for (Observable servicio : serviciosObservables) {
                servicio.agregarObserver(observer);
            }
            System.out.println("[ObserverFacade] Observer " + observer.getClass().getSimpleName() + 
                             " registrado en " + serviciosObservables.size() + " servicio(s)");
        }
    }
    
    //Registra un observer en un servicio observable específico
    public void registrarObserverEnServicio(Observable servicio, Observer observer) {
        if (servicio != null && observer != null) {
            servicio.agregarObserver(observer);
            System.out.println("[ObserverFacade] Observer " + observer.getClass().getSimpleName() + 
                             " registrado en " + servicio.getClass().getSimpleName());
        }
    }
    
    //Registra un observer solo en el PedidoService
    public void registrarObserverEnPedidoService(PedidoService pedidoService, Observer observer) {
        registrarObserverEnServicio(pedidoService, observer);
    }
    
    //Registra un observer solo en el ComprobanteService
    public void registrarObserverEnComprobanteService(ComprobanteService comprobanteService, Observer observer) {
        registrarObserverEnServicio(comprobanteService, observer);
    }
    
    //Configura los observers por defecto en los servicios especificados
    public void configurarObserversPorDefecto(PedidoService pedidoService, ComprobanteService comprobanteService) {
        if (pedidoService == null || comprobanteService == null) {
            System.out.println("[ObserverFacade] Error: Los servicios no pueden ser null");
            return;
        }
        
        registrarServicioObservable(pedidoService);
        registrarServicioObservable(comprobanteService);
        
        System.out.println("[ObserverFacade] Configurando observers por defecto...");
    }
    
    //Obtiene y retorna la cantidad de servicios observables registrados
    public int cantidadServiciosObservables() {
        return serviciosObservables.size();
    }
}

