package com.abn.marketscale.models;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.abn.marketscale.interfaces.ProdutoFace;

public class ProdutoDAO implements Serializable, ProdutoFace {

	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

	public ProdutoDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("mydb");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public String adicionar(Produto produto) {
		String resultado = null;
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(produto);
			entityManager.getTransaction().commit();
			resultado = "Produto adicionado!";
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
			resultado = "Erro no banco!";
		} 
		return resultado;
	}

	@Override
	public String atualizar(Produto produto) {
		String resultado = null;
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(produto);
			entityManager.getTransaction().commit();
			resultado = "Produto atualizado!";
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
			resultado = "Erro no banco!";
		} 
		return resultado;
	}

	@Override
	public Collection<Produto> listar() {	
		Collection<Produto> produtos = null;
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("from Produto");
			produtos = query.getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return produtos;
	}
	
	public Produto procurar(long id) {
		Produto produto = null;
		try {
			entityManager.getTransaction().begin();
			produto = entityManager.find(Produto.class, id);
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produto;
	}
	
}
