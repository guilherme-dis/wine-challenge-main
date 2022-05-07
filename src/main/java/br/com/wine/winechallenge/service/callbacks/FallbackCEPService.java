package br.com.wine.winechallenge.service.callbacks;

import br.com.wine.winechallenge.consumer.client.Consumer;
import br.com.wine.winechallenge.consumer.dto.awesomeapi.AwesomeApiDTO;
import br.com.wine.winechallenge.consumer.dto.opencep.OpenCepDTO;
import br.com.wine.winechallenge.consumer.dto.viacep.ViaCepDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FallbackCEPService {
    private Consumer utils = new Consumer();
    private  ResponseEntity<Object> response = null;

    public AwesomeApiDTO awesomeApiConsumerResponse (String cep) {
        try {
            response = utils.getHttp("https://cep.awesomeapi.com" +
                    ".br/json/" + cep);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody(), new TypeReference<AwesomeApiDTO>() {});
    }

    public OpenCepDTO openCepConsumerResponse(String cep) {
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
