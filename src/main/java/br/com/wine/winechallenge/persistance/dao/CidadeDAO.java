package br.com.wine.winechallenge.persistance.dao;

import br.com.wine.winechallenge.persistance.model.Cidade;

public class CidadeDAO extends  AbstractDao<Cidade> {
    public CidadeDAO() {
        setClazz(Cidade.class);
    }
}
