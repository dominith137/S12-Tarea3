package org.usil.controller;

import org.usil.facade.ObserverFacade;
import org.usil.observer.ClienteObserver;
import org.usil.observer.InventarioObserver;
import org.usil.observer.LogObserver;
import org.usil.service.ComprobanteService;
import org.usil.service.PedidoService;

//Controller que orquesta la configuración y gestión de observers en el sistema
public class ObserverController {
    
    private ObserverFacade observerFacade;
    
    public ObserverController() {
        this.observerFacade = new ObserverFacade();
    }
    
    //Configura los observers por defecto en los servicios especificados
    public void configurarObservers(PedidoService pedidoService, ComprobanteService comprobanteService) {
        if (pedidoService == null || comprobanteService == null) {
            System.out.println("[ObserverController] Error: Los servicios no pueden ser null");
            return;
        }
        
        System.out.println("[ObserverController] Iniciando configuración de observers...");
        
        //Configurar el facade con los servicios observables
        observerFacade.configurarObserversPorDefecto(pedidoService, comprobanteService);
        
        //Crear y registrar observers en ambos servicios
        observerFacade.registrarObserverEnPedidoService(pedidoService, new ClienteObserver());
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, new ClienteObserver());
        
        observerFacade.registrarObserverEnPedidoService(pedidoService, new InventarioObserver());
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, new InventarioObserver());
        
        observerFacade.registrarObserverEnPedidoService(pedidoService, new LogObserver());
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, new LogObserver());
        
        System.out.println("[ObserverController] Configuración de observers completada");
    }
    
    //Obtiene el ObserverFacade para acceso directo si es necesario
    public ObserverFacade getObserverFacade() {
        return observerFacade;
    }
}

