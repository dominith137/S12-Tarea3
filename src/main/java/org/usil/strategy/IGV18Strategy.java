package org.usil.strategy;

//Estrategia que calcula el IGV del 18%
public class IGV18Strategy implements ImpuestoStrategy {
    
    private static final double IGV_PORCENTAJE = 0.18;
    
    @Override
    public double calcular(double subtotal) {
        return subtotal * IGV_PORCENTAJE;
    }
}

