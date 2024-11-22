package it.testCatalogo.dao.impl;

import java.util.List;

import it.testCatalogo.dao.ProdottoDao;
import it.testCatalogo.model.Prodotto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class ProdottoDaoImpl implements ProdottoDao{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void add(Prodotto p) {
		em.persist(p);
	}

	@Override
	@Transactional
	public void update(Prodotto p) {
		em.merge(p);
		
	}

	@Override
	@Transactional
	public void delete(int id) {
		em.remove(getById(id));
	}

	@Override
	public Prodotto getById(int id) {
		
		return em.find(Prodotto.class, id);
	}

	@Override
	public List<Prodotto> getAll() {
		Query q = em.createQuery("SELECT p FROM Prodotto p");
		return q.getResultList();
		
	}

}
