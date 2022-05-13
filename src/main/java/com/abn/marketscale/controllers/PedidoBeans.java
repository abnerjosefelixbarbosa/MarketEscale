package com.abn.marketscale.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.abn.marketscale.models.Pedido;
import com.abn.marketscale.models.PedidoProduto;
import com.abn.marketscale.models.Produto;

interface GerarPedido {
	String aplicar(Collection<PedidoProduto> valores);
}

interface AdicionarPedido {
	String aplicar(Pedido pedi, Produto prod);
}

interface ListarPedido {
	Collection<PedidoProduto> aplicar();
}

interface AlterarPedido {
	String aplicar(PedidoProduto pedidoProduto);
}


@Named
@SessionScoped
public class PedidoBeans implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedido pedido;
	@Inject
	private Produto produto;
	@Inject
	private PedidoProduto pedidoProduto;
	private Collection<PedidoProduto> lista1 = new ArrayList<>();
	private Collection<PedidoProduto> lista2 = new ArrayList<>();
	private double valorTotal = 0;

	public String gerar() {		
		GerarPedido gerarPedido = (Collection<PedidoProduto> valores) -> {
			String messages = null;
			if (valores.size() == 0) {
				messages = "A lista de pedidos está vázia!";
			} else {
				for (PedidoProduto valor : valores) {
					messages = pedido.gerar(valor.getPedido(), valor.getProduto().getId());
				}
			}	
			return messages;			
		};
		String messages = gerarPedido.aplicar(lista1);	
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, messages, messages));
		return null;
	}
	
	public String adicionar() {
		AdicionarPedido adicionarPedido = (Pedido pedi, Produto prod) -> {
			String messages = null;
			Produto dados = prod.procurar(prod.getId());
			if (dados == null) {
				messages = "Produto não encontrado!";			
			} else {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
				Date date = new Date(System.currentTimeMillis());
				PedidoProduto pedidoProduto = new PedidoProduto();
				try {
					pedido.setData(simpleDateFormat.format(date));
					pedidoProduto.setPedido(pedi);
					pedidoProduto.setProduto(prod);				
					lista1.add(pedidoProduto);
					valorTotal += dados.getPreco() * pedido.getQuantidade();
					messages = "Produto adicionado na lista!";
					limparCampos();
				} catch (Exception e) {
					messages = e.getMessage();
				}
			}
			return messages;			
		};
		String messages = adicionarPedido.aplicar(pedido, produto);		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, messages, messages));
		return null;
	}
	
	public String alterar() {
		AlterarPedido alterarPedido = (PedidoProduto pp) -> {
			String messages = null;
			Produto dados = pp.getProduto().procurar(pp.getProduto().getId());
			if (dados == null) {
				messages = "Produto não encontrado!";			
			} else {			
				messages = pp.getPedido().alterar(pp.getPedido(), pp.getProduto().getId());				
			}
			listar();
			return messages;
		};
		String messages = alterarPedido.aplicar(pedidoProduto);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, messages, messages));
		return null;
	}
	
	public String listar() {
		ListarPedido listarPedido = () -> pedido.listar();			
		lista2 = listarPedido.aplicar(); 		
		return null;
	}
	
	public String limparLista() {
		lista1.clear();
		valorTotal = 0;
		return null;
	}
	
	private void limparCampos() {
		pedido = new Pedido();
		produto = new Produto();
	}

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

	public PedidoProduto getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(PedidoProduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public Collection<PedidoProduto> getLista1() {
		return lista1;
	}

	public void setLista1(Collection<PedidoProduto> lista1) {
		this.lista1 = lista1;
	}

	public Collection<PedidoProduto> getLista2() {
		return lista2;
	}

	public void setLista2(Collection<PedidoProduto> lista2) {
		this.lista2 = lista2;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}	
	 
}