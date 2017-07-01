package javafxmvc.model.domain;

import java.io.Serializable;

public class Servico implements Serializable {

    public int getCdServico() {
		return cdServico;
	}
	public void setCdServico(int cdServico) {
		this.cdServico = cdServico;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	private int cdServico;
    private String nome;
    private double preco;
    private String descricao;
  

    
}