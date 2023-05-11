package io.github.admachiaveli.divideaibackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.admachiaveli.divideaibackend.model.TipoValor;
import io.github.admachiaveli.divideaibackend.repo.TipoValorRepo;

@RestController
@RequestMapping(value ="/tipo-valor")
public class TipoValorController {

        @Autowired
	private TipoValorRepo tipoValorRepo;


	@GetMapping
	public List<TipoValor> findAll() {
		return tipoValorRepo.findAll();
	}
 
}
