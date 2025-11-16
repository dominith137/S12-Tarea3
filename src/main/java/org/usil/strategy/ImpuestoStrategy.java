package org.usil.strategy;

//Interfaz Strategy para el cálculo de impuestos
public interface ImpuestoStrategy {
    
    //Calcula el impuesto basado en el subtotal
    double calcular(double subtotal);
}

