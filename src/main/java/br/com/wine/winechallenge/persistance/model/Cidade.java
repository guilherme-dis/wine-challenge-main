package br.com.wine.winechallenge.persistance.model;

import javax.persistence.*;

import br.com.wine.winechallenge.persistance.dto.response.CidadeDTO;
import org.hibernate.boot.model.source.internal.hbm.CompositeIdentifierSingularAttributeSourceBasicImpl;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = Cidade.TABLE_NAME)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "TCIDADE";

    @Id
    @Column(name="IBGE",length = 20,nullable = false)
    private String ibge;

    @Column(name="UF",length = 2,nullable = false)
    private String uf;
    
    @Column(name="LOCALIDADE",length = 100,nullable = false)
    private String localidade;

    @OneToMany(mappedBy = "cidade", cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    
    private List<Cep> cep;
    
    public CidadeDTO toDTO() {
    	
    	CidadeDTO cidadeDTO = new CidadeDTO();
    	
    		cidadeDTO.setIbge(this.ibge);
    		cidadeDTO.setLocalidade(this.localidade);
    		cidadeDTO.setUf(this.uf);
    	
    	return cidadeDTO;
    }
    
    public Cidade(String ibge, String uf, String localidade) {
		super();
		this.ibge = ibge;
		this.uf = uf;
		this.localidade = localidade;
	}

	public List<Cep> getCep() {
		return cep;
	}

	public void setCep(List<Cep> cep) {
		this.cep = cep;
	}

	public Cidade(String ibge, String uf, String localidade, List<Cep> cep) {
		this.ibge = ibge;
		this.uf = uf;
		this.localidade = localidade;
		this.cep = cep;
	}

	public Cidade() {


	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	

	

	


}
