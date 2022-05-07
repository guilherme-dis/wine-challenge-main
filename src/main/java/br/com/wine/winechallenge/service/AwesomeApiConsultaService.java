package br.com.wine.winechallenge.service;

import br.com.wine.winechallenge.consumer.client.Consumer;
import br.com.wine.winechallenge.consumer.dto.apicep.ApiCepDTO;
import br.com.wine.winechallenge.consumer.dto.awesomeapi.AwesomeApiDTO;
import br.com.wine.winechallenge.consumer.dto.opencep.OpenCepDTO;
import br.com.wine.winechallenge.consumer.dto.viacep.ViaCepDTO;
import br.com.wine.winechallenge.service.callbacks.CepConsultaService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AwesomeApiConsultaService {

    private CepConsultaService cepConsultaService = new CepConsultaService();

    private ViaCepDTO viaCepUtils = new ViaCepDTO();
    private ApiCepDTO apiCepUtils = new ApiCepDTO();
    private OpenCepDTO openCepUtils = new OpenCepDTO();
    private AwesomeApiDTO awesomeApiCepUtils = new AwesomeApiDTO();
    private Consumer utils = new Consumer();
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> consultaCep(String cepEntrada) {
        ViaCepDTO viaCepConsultaSaida = new ViaCepDTO();
        ApiCepDTO apiCepConsultaSaida = new ApiCepDTO();
        OpenCepDTO openCepConsultaSaida = new OpenCepDTO();
        AwesomeApiDTO awesomeApiConsultaSaida = new AwesomeApiDTO();
        int status = 0;


        if(cepEntrada.isBlank()) {
            return ResponseEntity.badRequest().body("{\"erro\"" + ":" + "\"CEP não encontrado\"}");
        } else if (cepEntrada.length() != 8) {
            return ResponseEntity.badRequest().body("{\"erro\"" + ":" + "\"CEP inválido\"}");
        } else if(cepEntrada.matches("[A-Z][a-z]*")) {
            return ResponseEntity.badRequest().body("{\"erro\"" + ":" + "\"CEP é composto por letras\"}");
        }

        try {
            ResponseEntity<Object> responseEntity = utils.getHttp("https://cep.awesomeapi.com" +
                    ".br/json/" + cepEntrada);
            status = responseEntity.getStatusCodeValue();
            ObjectMapper mapper = new ObjectMapper();
            awesomeApiConsultaSaida = mapper.convertValue(responseEntity.getBody(), new TypeReference<AwesomeApiDTO>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (awesomeApiConsultaSaida.getCep() == null) {
                viaCepConsultaSaida = cepConsultaService.viaCepConsultaSaida(cepEntrada);
            }
            if (openCepConsultaSaida.getCep() == null) {
                openCepConsultaSaida = cepConsultaService.openCepConsultaSaida(cepEntrada);
            }
            if (apiCepConsultaSaida.getCode() == null) {
                apiCepConsultaSaida = cepConsultaService.apiCepConsultaSaida(cepEntrada);
            }
        }

        return ResponseEntity.status(400).body("{\"erro\"" + ":" + "\"CEP não encontrado\"}");
    }
}