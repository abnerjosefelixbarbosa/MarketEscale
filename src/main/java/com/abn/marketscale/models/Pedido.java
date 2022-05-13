package com.abn.marketscale.models;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.abn.marketscale.interfaces.PedidoFace;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable, PedidoFace {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "Data obrigatória!")
	@Column(nullable = false)
	private String data;
	@DecimalMin(value = "0", message = "Quantidade deve ter no minimo 0!")
	@Column(nullable = false)
	private int quantidade;
	@NotEmpty(message = "Nome do cliente obrigatório!")
	@Size(max = 100)
	@Column(nullable = false)
	private String nomeCliente;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
	
	@Override
	public String gerar(Pedido pedido, long id) {
		PedidoDAO pedidoDao = new PedidoDAO();		
		return pedidoDao.gerar(pedido, id);
	}
	
	@Override
	public String alterar(Pedido pedido, long id) {
		PedidoDAO pedidoDao = new PedidoDAO();	
		return pedidoDao.alterar(pedido, id);
	}
	
	@Override
	public Collection<PedidoProduto> listar() {
		PedidoDAO pedidoDao = new PedidoDAO();
		return pedidoDao.listar();
	}
	
	public String imprimir() {
		return String.format("Pedido [") +
			   String.format("id: %d, ", id) +
			   String.format("data: %s, ", data) +
			   String.format("quantidade: %s, ", quantidade) +
			   String.format("nome do cliente: %s]", nomeCliente);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
