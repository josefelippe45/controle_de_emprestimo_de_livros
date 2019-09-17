package com.fatec.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.demo.model.Aluno;
import com.fatec.demo.model.AlunoRepository;
import com.fatec.demo.model.Livro;


@RestController
@RequestMapping(path = "/aluno")
public class AlunoController {
	@Autowired
	private AlunoRepository repository;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarAluno");
		modelAndView.addObject("aluno", repository.findAll());
		return modelAndView;
	}
	@GetMapping("/cadastrar")
	public ModelAndView cadastraLivro(Aluno aluno) {
		ModelAndView mv = new ModelAndView("CadastrarAluno");
		mv.addObject("aluno", aluno);
		return mv;
	}

	@GetMapping("/edit/{ra}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("ra") String ra) {
		ModelAndView modelAndView = new ModelAndView("AtualizarAluno");
		modelAndView.addObject("aluno", repository.findById(ra)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		repository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("Consultar");
		modelAndView.addObject("aluno", repository.findAll());
		return modelAndView;
	}
	@PostMapping("/save")
	public ModelAndView save(@Valid Aluno aluno, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("CadastrarAluno");
		if (result.hasErrors()) {
			return new ModelAndView("CadastrarAluno");
		}
		try {
			Aluno jaExiste = null;
			jaExiste = repository.findById(aluno.getRa());
			if (jaExiste == null) {
				repository.save(aluno);
				modelAndView = new ModelAndView("ConsultarAluno");
				modelAndView.addObject("aluno", repository.findAll());
				return modelAndView;
			} else {
				return new ModelAndView("CadastrarAluno");
			}
		} catch (Exception e) {
			System.out.println("erro ===> " + e.getMessage());
			return modelAndView; // captura o erro mas nao informa o motivo.
		}
	}
}
