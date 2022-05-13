package com.abn.marketscale.models;

import java.io.Serializable;

public class PedidoProduto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	private Produto produto;
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
