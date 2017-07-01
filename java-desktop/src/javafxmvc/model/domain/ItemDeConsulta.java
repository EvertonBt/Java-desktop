package javafxmvc.model.domain;

import java.io.Serializable;

public class ItemDeConsulta implements Serializable {

    private int cdItemDeConsulta;
    private int quantidade;
    private double valor;
    private Produto produto;
    private Consulta consulta;

    public ItemDeConsulta() {
    }

    public int getCdItemDeConsulta() {
        return cdItemDeConsulta;
    }

    public void setCdItemDeConsulta(int cdItemDeConsulta) {
        this.cdItemDeConsulta = cdItemDeConsulta;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    
}
