package br.com.wine.winechallenge.service;

import br.com.wine.winechallenge.persistance.dao.CepDAO;
import br.com.wine.winechallenge.persistance.dao.CidadeDAO;
import br.com.wine.winechallenge.persistance.dto.response.CepDTO;
import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.persistance.model.Cidade;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CepService {


    public CepDTO getCep(String cep) {

        CepDAO cepDAO = new CepDAO();

        Cep persistedObject = cepDAO.findOne(cep);

        if (persistedObject == null) {
            ViaCEPConsultaService awesomeApiConsultaService = new ViaCEPConsultaService();

            ResponseEntity<?> dto = awesomeApiConsultaService.consultaCep(cep);
            Cep entity = (Cep) dto.getBody();

            persistedObject = cepDAO.create(entity);
        }

        return persistedObject.toDTO();
    }

    public ResponseEntity<List<CepDTO>> findCEPByIBGECodeAndUF(String ibge, Optional<String> uf) {

        List<CepDTO> dtos = new ArrayList<>();
        List<Cep> ceps = new ArrayList<>();
        CidadeDAO cidadeDAO = new CidadeDAO();


        if (uf.isPresent()) {
            Cidade city = cidadeDAO.findByCitY(ibge);

            if (Objects.equals(city.getUf(), uf.get())) {
                ceps = city.getCep();
            }else{
                return ResponseEntity.notFound().build();
            }

        } else {
            ceps = cidadeDAO.findByCitY(ibge).getCep();
        }

        for (Cep entity : ceps) {
            dtos.add(entity.toDTO());
        }

        return ResponseEntity.ok(dtos);
    }

}
