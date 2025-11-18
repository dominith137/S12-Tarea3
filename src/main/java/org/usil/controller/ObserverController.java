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
    private ClienteObserver clienteObserver;
    private InventarioObserver inventarioObserver;
    private LogObserver logObserver;
    
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
        
        //Crear instancias de observers
        clienteObserver = new ClienteObserver();
        inventarioObserver = new InventarioObserver();
        logObserver = new LogObserver();
        
        //Configurar el facade con los servicios observables
        observerFacade.configurarObserversPorDefecto(pedidoService, comprobanteService);
        
        //Registrar cada observer en ambos servicios
        observerFacade.registrarObserverEnPedidoService(pedidoService, clienteObserver);
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, clienteObserver);
        
        observerFacade.registrarObserverEnPedidoService(pedidoService, inventarioObserver);
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, inventarioObserver);
        
        observerFacade.registrarObserverEnPedidoService(pedidoService, logObserver);
        observerFacade.registrarObserverEnComprobanteService(comprobanteService, logObserver);
        
        System.out.println("[ObserverController] Configuración de observers completada");
    }
    
    //Obtiene el ObserverFacade para acceso directo si es necesario
    public ObserverFacade getObserverFacade() {
        return observerFacade;
    }
    
    //Obtiene el ClienteObserver
    public ClienteObserver getClienteObserver() {
        return clienteObserver;
    }
    
    //Obtiene el InventarioObserver
    public InventarioObserver getInventarioObserver() {
        return inventarioObserver;
    }
    
    //Obtiene el LogObserver
    public LogObserver getLogObserver() {
        return logObserver;
    }
}

