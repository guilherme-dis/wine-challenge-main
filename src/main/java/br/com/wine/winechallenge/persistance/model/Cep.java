package br.com.wine.winechallenge.persistance.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.wine.winechallenge.persistance.dao.AbstractDao;
import br.com.wine.winechallenge.persistance.dto.response.CepDTO;
import br.com.wine.winechallenge.persistance.dto.response.CidadeDTO;

@Entity
@Table(name = Cep.TABLE_NAME)
public class Cep  implements Serializable{
	
	private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "TCEP";

	public Cep(String cep, String logradouro, String complemento, String bairro, Cidade cidade) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
	}

	public Cep() {

	}

	@Id
    @Column(name="CEP", length = 10,nullable = false)
    private String cep;

    
    @Column(name="LOGRADOURO", length = 255,nullable = false)
    private String logradouro;
    
    @Column(name="COMPLEMENTO", length = 255,nullable = true)
    private String complemento;
    
    @Column(name="BAIRRO", length = 50,nullable = true)
    private String bairro;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="ibge", nullable = false)
    private Cidade cidade;
    
    public CepDTO toDTO() {
    	
    	CepDTO cepDTO = new CepDTO();
    	
    	cepDTO.setBairro(this.bairro);
    	cepDTO.setCep(this.cep);
    	cepDTO.setLogradouro(this.logradouro);
    	cepDTO.setComplemento(this.complemento);
    	cepDTO.setCidade(this.cidade.toDTO());
    	
    	return cepDTO;
    }
   
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}
    
    
}
