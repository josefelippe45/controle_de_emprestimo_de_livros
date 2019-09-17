package com.fatec.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.demo.model.AlunoRepository;
import com.fatec.demo.model.LivroRepository;

@RestController
@RequestMapping(path = "/aluno")
public class AlunoController {
	@Autowired
	private AlunoRepository repository;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarLivro");
		modelAndView.addObject("livros", repository.findAll());
		return modelAndView;
	}
}
