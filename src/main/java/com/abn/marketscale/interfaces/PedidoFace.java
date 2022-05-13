package com.abn.marketscale.interfaces;

import java.util.Collection;

import com.abn.marketscale.models.Pedido;
import com.abn.marketscale.models.PedidoProduto;

public interface PedidoFace {
	String gerar(Pedido pedido, long id);
	String alterar(Pedido pedido, long id);
	Collection<PedidoProduto> listar();
}
