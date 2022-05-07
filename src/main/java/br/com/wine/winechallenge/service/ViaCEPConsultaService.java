package br.com.wine.winechallenge.service;

import br.com.wine.winechallenge.consumer.client.Consumer;
import br.com.wine.winechallenge.consumer.dto.awesomeapi.AwesomeApiDTO;
import br.com.wine.winechallenge.consumer.dto.opencep.OpenCepDTO;
import br.com.wine.winechallenge.consumer.dto.viacep.ViaCepDTO;
import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.persistance.model.Cidade;
import br.com.wine.winechallenge.service.callbacks.FallbackCEPService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ViaCEPConsultaService {

    private FallbackCEPService cepService = new FallbackCEPService();
    private Consumer consumer = new Consumer();
    private Cep entity = new Cep();

    public ResponseEntity<?> consultaCep(String cep) {
        ViaCepDTO viaCepResponse = new ViaCepDTO();
        OpenCepDTO openCepResponse = new OpenCepDTO();
        AwesomeApiDTO awesomeApiResponse = new AwesomeApiDTO();
        int status = 0;


        if (cep.length() != 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"erro\"" + ":" + "\"CEP inválido\"}");
        } else if(cep.matches("[A-Z][a-z]*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"erro\"" + ":" + "\"CEP é composto por letras\"}");
        }

        try {
            ResponseEntity<Object> responseEntity = consumer.getHttp("https://viacep.com.br/ws/" +
                    cep + "/json/");
            ObjectMapper mapper = new ObjectMapper();
            viaCepResponse = mapper.convertValue(responseEntity.getBody(), new TypeReference<ViaCepDTO>() {
            });

            entity.setCep(cep);

            entity.setBairro(viaCepResponse.getBairro());
            entity.setComplemento(viaCepResponse.getComplemento());
            entity.setLogradouro(viaCepResponse.getLogradouro());
            entity.setCidade(new Cidade(viaCepResponse.getIbge(), viaCepResponse.getUf(), viaCepResponse.getLocalidade()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (openCepResponse.getCep() == null) {
                openCepResponse = cepService.openCepConsumerResponse(cep);

                entity.setBairro(openCepResponse.getBairro());
                entity.setComplemento(openCepResponse.getComplemento());
                entity.setLogradouro(openCepResponse.getLogradouro());
                entity.setCidade(new Cidade(openCepResponse.getIbge(), openCepResponse.getUf(), openCepResponse.getLocalidade()));

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(entity);
            }

            if (awesomeApiResponse.getCep() == null) {
                awesomeApiResponse = cepService.awesomeApiConsumerResponse(cep);

                entity.setBairro(awesomeApiResponse.getDistrict());
                entity.setComplemento(awesomeApiResponse.getAddress());
                entity.setLogradouro(awesomeApiResponse.getAddress());
                entity.setCidade(new Cidade(awesomeApiResponse.getCity_ibge(), awesomeApiResponse.getState(), awesomeApiResponse.getAddress()));

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(entity);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"erro\"" + ":" + "\"CEP não encontrado\"}");
    }
}