package com.abn.marketscale.controllers;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.abn.marketscale.models.Produto;

@FunctionalInterface
interface AdionarProduto {
	String aplicar(Produto produto);
}

@FunctionalInterface
interface AtualizarProduto {
	String aplicar(Produto produto);
}

@FunctionalInterface
interface ListarProduto {
	Collection<Produto> aplicar();
}

@Named
@SessionScoped
public class ProdutoBeans implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produto produto;
	private Collection<Produto> produtos = new ArrayList<>();

	public String adionar() {
		AdionarProduto adionarProduto = (Produto p) -> {
			String messages = p.adicionar(p);
			produtos = p.listar();
			limpar();
			return messages;
		};
		String messages = adionarProduto.aplicar(produto);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, messages, messages));
		return null;
	}

	public String atualizar() {
		AtualizarProduto atualizarProduto = (Produto p) -> {
			String messages = p.atualizar(p);
			produtos = p.listar();
			limpar();
			return messages;
		};
		String messages = atualizarProduto.aplicar(produto);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, messages, messages));
		return null;
	}

	public String listar() {
		ListarProduto listarProduto = () -> produto.listar();
		produtos = listarProduto.aplicar();
		return null;
	}

	private void limpar() {
		produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Collection<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(Collection<Produto> produtos) {
		this.produtos = produtos;
	}

}
