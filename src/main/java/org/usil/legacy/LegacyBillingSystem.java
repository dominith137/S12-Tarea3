package org.usil.legacy;

/**
 * Sistema legacy de facturación electrónica (librería antigua)
 * Esta clase simula un sistema externo con una interfaz incompatible
 */
public class LegacyBillingSystem {
    public String crearFacturaElectronica(String clienteNombre, String productoNombre, 
                                          int cantidad, double montoTotal) {
        // Simula la generación de factura en el sistema legacy
        String numeroFactura = "LEG-" + System.currentTimeMillis();
        System.out.println("[Legacy System] Factura generada: " + numeroFactura);
        System.out.println("[Legacy System] Cliente: " + clienteNombre);
        System.out.println("[Legacy System] Producto: " + productoNombre);
        System.out.println("[Legacy System] Monto: " + montoTotal);
        return numeroFactura;
    }
}

