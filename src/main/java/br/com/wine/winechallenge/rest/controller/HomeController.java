package br.com.wine.winechallenge.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.wine.winechallenge.persistance.dto.response.CepDTO;
import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.service.CepService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
	private final CepService cepService;
    
	public HomeController(CepService cepService) {
		super();
		this.cepService = cepService;
	}

	@GetMapping("/cep/{cep}")
    @ResponseBody
    public CepDTO getCep(@PathVariable("cep")String cep ) {
    	 return cepService.getCep(cep);
    }


	@GetMapping("/ceps")
	public ResponseEntity<List<CepDTO>> findCEPByIBGECodeAndUF(@RequestParam String ibge,
															  @RequestParam Optional<String> uf) {return cepService.findCEPByIBGECodeAndUF(ibge,uf);}
}
