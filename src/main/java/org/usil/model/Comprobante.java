package org.usil.model;

public class Comprobante {
    private String numeroFactura;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private double subtotal;
    private double igv;
    private double total;
    
    public Comprobante(String numeroFactura, Cliente cliente, Producto producto, 
                       int cantidad, double subtotal, double igv, double total) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
    }
    
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public double getIgv() {
        return igv;
    }
    
    public double getTotal() {
        return total;
    }
}

