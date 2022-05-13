package com.abn.marketscale.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.abn.marketscale.interfaces.PedidoFace;

public class PedidoDAO implements Serializable, PedidoFace {

	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public PedidoDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("mydb");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public String gerar(Pedido pedido, long id) {
		String resultado = null;
		try {
			entityManager.getTransaction().begin();
			Produto produto = entityManager.find(Produto.class, id);
			pedido.setProduto(produto);
			entityManager.persist(pedido);
			entityManager.getTransaction().commit();
			resultado = "Pedido gerado!";
			entityManager.close();
		} catch (Exception e) {
			resultado = "Erro no banco!";
		}
		return resultado;
	}

	@Override
	public String alterar(Pedido pedido, long id) {
		String resultado = null;
		try {
			entityManager.getTransaction().begin();
			Produto produto = entityManager.find(Produto.class, id);
			pedido.setProduto(produto);
			entityManager.merge(pedido);
			entityManager.getTransaction().commit();
			resultado = "Pedido alterado!";
			entityManager.close();
		} catch (Exception e) {
			resultado = "Erro no banco!";
		}
		return resultado;
	}

	@Override
	public Collection<PedidoProduto> listar() {
		Collection<PedidoProduto> lista = new ArrayList<>();
		try {
			entityManager.getTransaction().begin();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
			Root<Pedido> rootPedido = criteriaQuery.from(Pedido.class);
			Root<Produto> rootProduto = criteriaQuery.from(Produto.class);
			criteriaQuery.multiselect(rootPedido, rootProduto);
			criteriaQuery.where(builder.equal(rootPedido.get("produto"), rootProduto.get("id")));
			Collection<Object[]> objetos = entityManager.createQuery(criteriaQuery).getResultList();
			for (Object[] objeto : objetos) {
				Pedido pedido = (Pedido) objeto[0];
				Produto produto = (Produto) objeto[1];
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setPedido(pedido);
				pedidoProduto.setProduto(produto);
				lista.add(pedidoProduto);
			}
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

}
