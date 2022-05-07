package br.com.wine.winechallenge.consumer.dto;

import java.io.Serializable;

public class CepDTORequest implements Serializable{
    private String cep;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
