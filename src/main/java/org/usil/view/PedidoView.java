package org.usil.view;

import org.usil.controller.PedidoController;
import org.usil.facade.PedidoFacade;
import org.usil.model.Comprobante;
import org.usil.model.Producto;
import org.usil.repository.PedidoRepository;
import org.usil.strategy.ExoneradoStrategy;
import org.usil.strategy.IGV18Strategy;

//Vista del patrón MVC - Responsable de la presentación y entrada de datos
public class PedidoView {
    
    private PedidoController controller;
    private PedidoFacade pedidoFacade;
    private PedidoRepository pedidoRepository;
    
    public PedidoView(PedidoController controller, PedidoFacade pedidoFacade, PedidoRepository pedidoRepository) {
        this.controller = controller;
        this.pedidoFacade = pedidoFacade;
        this.pedidoRepository = pedidoRepository;
    }
    
    public void mostrarBienvenida() {
        System.out.println("=========================================");
        System.out.println("  SISTEMA DE PROCESAMIENTO DE PEDIDOS  ");
        System.out.println("  (Repository + Strategy + Facade + Adapter)");
        System.out.println("=========================================\n");
    }
    
    public void procesarPedido(String nombreCliente, Producto producto, int cantidad, String nombreEstrategia) {
        System.out.println("--- Procesando pedido ---");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Producto: " + producto.getNombre());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Estrategia de Impuesto: " + nombreEstrategia);
        System.out.println();
        
        Comprobante comprobante = controller.registrarPedido(nombreCliente, producto, cantidad);
        
        if (comprobante != null) {
            pedidoFacade.mostrarComprobante(comprobante);
        } else {
            System.out.println("Error: No hay stock suficiente o la cantidad es inválida\n");
        }
    }
    
    public void mostrarEjemplos() {
        System.out.println("--- EJEMPLO 1: Pedido con IGV 18% ---\n");
        pedidoFacade.establecerEstrategiaImpuesto(new IGV18Strategy());
        Producto producto1 = new Producto("Laptop", 2500.00, 10);
        procesarPedido("Juan Pérez", producto1, 2, "IGV 18%");
        
        System.out.println("\n");
        
        System.out.println("--- EJEMPLO 2: Stock Insuficiente ---\n");
        Producto producto2 = new Producto("Mouse", 50.00, 5);
        procesarPedido("María García", producto2, 10, "IGV 18%");
        
        System.out.println("\n");
        
        System.out.println("--- EJEMPLO 3: Pedido Exonerado (0% impuesto) ---\n");
        pedidoFacade.establecerEstrategiaImpuesto(new ExoneradoStrategy());
        Producto producto3 = new Producto("Libro Educativo", 80.00, 15);
        procesarPedido("Carlos López", producto3, 3, "Exonerado (0%)");
        
        System.out.println("\n");
        
        System.out.println("--- EJEMPLO 4: Segundo Pedido con IGV 18% ---\n");
        pedidoFacade.establecerEstrategiaImpuesto(new IGV18Strategy());
        Producto producto4 = new Producto("Teclado", 120.00, 15);
        procesarPedido("Ana Martínez", producto4, 1, "IGV 18%");
        
        System.out.println("\n");
        
        mostrarResumenRepositorio();
        
        System.out.println("\n=========================================");
        System.out.println("       PROCESO FINALIZADO");
        System.out.println("=========================================");
    }
    
    private void mostrarResumenRepositorio() {
        System.out.println("--- RESUMEN DEL REPOSITORIO ---");
        System.out.println("Total de pedidos guardados: " + pedidoRepository.contar());
        System.out.println("Los pedidos han sido almacenados correctamente en el repositorio.");
    }
}

