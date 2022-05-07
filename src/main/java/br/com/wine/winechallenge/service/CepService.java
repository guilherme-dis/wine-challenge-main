package br.com.wine.winechallenge.service;

import br.com.wine.winechallenge.consumer.dto.viacep.ViaCepDTO;
import br.com.wine.winechallenge.persistance.dao.CepDAO;
import br.com.wine.winechallenge.persistance.dao.CidadeDAO;
import br.com.wine.winechallenge.persistance.dto.response.CepDTO;
import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.persistance.model.Cidade;
import br.com.wine.winechallenge.service.callbacks.CepConsultaService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CepService {
	
	
	public CepDTO getCep(String cep) {
		
		CepDAO cepDAO= new CepDAO();

		Cep persistedObject =cepDAO.findOne(cep);

		if(persistedObject == null){
			CepConsultaService cepConsultaService = new CepConsultaService();

			ViaCepDTO viaCepDTO = cepConsultaService.viaCepConsultaSaida(cep);

			Cep entity = new Cep();

			entity.setCep(cep);
			entity.setBairro(viaCepDTO.getBairro());
			entity.setComplemento(viaCepDTO.getComplemento());
			entity.setLogradouro(viaCepDTO.getLogradouro());
			entity.setCidade(new Cidade(viaCepDTO.getIbge(), viaCepDTO.getUf(), viaCepDTO.getLocalidade()));

			persistedObject = cepDAO.create(entity);
		}
		
		return persistedObject.toDTO();
	}
	public List<CepDTO> findCEPByIBGECodeAndUF(String ibge, String uf) {
		List<CepDTO> dtos = new ArrayList<>();

		CidadeDAO cidadeDAO = new CidadeDAO();

		//List<Cidade> persistedObjects = cidadeDAO.

//		for (Cep entity: entities) {
//			dtos.add(convertFromEntity(entity));
//		}

		return dtos;
	}

}
