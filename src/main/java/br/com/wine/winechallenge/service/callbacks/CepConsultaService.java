package br.com.wine.winechallenge.service.callbacks;

import br.com.wine.winechallenge.consumer.client.Consumer;
import br.com.wine.winechallenge.consumer.dto.apicep.ApiCepDTO;
import br.com.wine.winechallenge.consumer.dto.opencep.OpenCepDTO;
import br.com.wine.winechallenge.consumer.dto.viacep.ViaCepDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CepConsultaService {
    private Consumer utils = new Consumer();
    private  ResponseEntity<Object> response = null;

    public ApiCepDTO apiCepConsultaSaida(String cep) {
        try {
            response = utils.getHttp("https://ws.apicep.com/cep/" +
                    cep + ".json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody(), new TypeReference<ApiCepDTO>() {});
    }

    public ViaCepDTO viaCepConsultaSaida(String cep) {
        try {
            response = utils.getHttp("https://viacep.com.br/ws/" +
                    cep + "/json/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody(), new TypeReference<ViaCepDTO>() {});
    }

    public OpenCepDTO openCepConsultaSaida(String cep) {
        try {
            response = utils.getHttp("https://opencep.com/v1/" +
                    cep + ".json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody(), new TypeReference<OpenCepDTO>() {});
    }


}
