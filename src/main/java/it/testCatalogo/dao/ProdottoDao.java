package it.testCatalogo.dao;

import java.util.List;

import it.testCatalogo.model.Prodotto;

public interface ProdottoDao {

	public void add(Prodotto p);
	public void update(Prodotto p);
	public void delete(int id);
	public Prodotto getById(int id);
	public List<Prodotto> getAll();
}
