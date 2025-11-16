package org.usil.strategy;

//Estrategia que calcula impuesto exonerado (0%)
public class ExoneradoStrategy implements ImpuestoStrategy {
    
    @Override
    public double calcular(double subtotal) {
        return 0.0;
    }
}

