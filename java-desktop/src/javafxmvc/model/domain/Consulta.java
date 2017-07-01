package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Consulta implements Serializable {

    private int cdConsulta;
    private LocalDate data;
    private double valor;
    private boolean pago;
    private String estado;
    private List<ItemDeConsulta> itensDeConsulta;
    private Cliente cliente;
  
    public Consulta() {
    }

    public Consulta(int cdconsulta, LocalDate data, double valor, boolean pago) {
        this.cdConsulta = cdconsulta;
        this.data = data;
        this.valor = valor;
        this.pago = pago;
    }

    public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getCdConsulta() {
        return cdConsulta;
    }

    public void setCdConsulta(int cdConsulta) {
        this.cdConsulta = cdConsulta;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public List<ItemDeConsulta> getItensDeConsulta() {
        return itensDeConsulta;
    }

    public void setItensDeConsulta(List<ItemDeConsulta> itensDeConsulta) {
        this.itensDeConsulta = itensDeConsulta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}