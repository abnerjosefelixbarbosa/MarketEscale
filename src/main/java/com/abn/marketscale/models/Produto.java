package com.abn.marketscale.models;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.abn.marketscale.interfaces.ProdutoFace;

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable, ProdutoFace {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull(message = "Descrição obrigatória!")
	@NotEmpty(message = "Descrição obrigatória!")
	@Size(min = 0, max = 100, message = "Descrição deve ter no máximo 100 digitos!")
	@Column(nullable = false, unique = true, length = 100)
	private String descricao;
	@NotNull(message = "Categória obrigatória!")
	@NotEmpty(message = "Categória obrigatória!")
	@Size(min = 0, max = 50, message = "Categória deve ter no máximo 50 digitos!")
	@Column(nullable = false, length = 50)
	private String categoria;
	@DecimalMin(value = "0.00", message = "Preço deve ter no minimo 0.00!")
	@DecimalMax(value = "999.99", message = "Preço deve ter no máximo 999.99!")
	@Column(nullable = false, precision = 5, scale = 2)
	private double preco;
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private Collection<Pedido> pedidos;

	@Override
	public String adicionar(Produto produto) {		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.adicionar(produto);
	}

	@Override
	public String atualizar(Produto produto) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.atualizar(produto);
	}

	@Override
	public Collection<Produto> listar() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.listar();
	}
	
	public Produto procurar(long id) { 
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.procurar(id);
	}
	
	public String imprimir() {
		return String.format("Produto [") + 
			   String.format("id: %d, ", id) +
			   String.format("descrição: %s, ", descricao) +
			   String.format("categória: %s, ", categoria) +
			   String.format("preço: %f]", preco);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Collection<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
