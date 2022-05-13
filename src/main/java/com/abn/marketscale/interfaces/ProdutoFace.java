package com.abn.marketscale.interfaces;

import java.util.Collection;

import com.abn.marketscale.models.Produto;

public interface ProdutoFace {
	String adicionar(Produto produto);
	String atualizar(Produto produto);
	Collection<Produto> listar();
}
