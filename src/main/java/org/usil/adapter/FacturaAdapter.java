package org.usil.adapter;

import org.usil.legacy.LegacyBillingSystem;
import org.usil.model.Pedido;
import org.usil.service.FacturaService;

//Adaptador que permite usar LegacyBillingSystem a través de la interfaz moderna FacturaService
public class FacturaAdapter implements FacturaService {
    
    private LegacyBillingSystem legacySystem;
    
    public FacturaAdapter(LegacyBillingSystem legacySystem) {
        this.legacySystem = legacySystem;
    }
    
    @Override
    public String generarFactura(Pedido pedido) {
        String clienteNombre = pedido.getCliente().getNombre();
        String productoNombre = pedido.getProducto().getNombre();
        int cantidad = pedido.getCantidad();
        double montoTotal = pedido.getTotal();
        
        // Llamar al metodo legacy con los parámetros adaptados
        String numeroFacturaLegacy = legacySystem.crearFacturaElectronica(
            clienteNombre, 
            productoNombre, 
            cantidad, 
            montoTotal
        );

        return numeroFacturaLegacy;
    }
}

