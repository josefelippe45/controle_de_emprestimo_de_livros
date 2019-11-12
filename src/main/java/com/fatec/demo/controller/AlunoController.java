package com.fatec.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.demo.model.*;

@RestController
@RequestMapping(path = "/alunos")
public class AlunoController {
//insert into aluno values ('1', 'Gabriel','', 'engenharia')
	@Autowired
	private AlunoRepository repository;

	@GetMapping("/consulta")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("ConsultarAluno");
		modelAndView.addObject("alunos", repository.findAll());
		return modelAndView;
	}

	/**
	 * quando o usuario digita localhost:8080/api/add
	 *
	 * @param aluno
	 * @return o html /Cadastraraluno
	 */
	@GetMapping("/cadastrar")
	public ModelAndView cadastraAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("CadastrarAluno");
		mv.addObject("alunos", aluno);
		return mv;
	} 

	@GetMapping("/edit/{nome}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView mostraFormAdd(@PathVariable("nome") String nome) {
		ModelAndView modelAndView = new ModelAndView("AtualizaAluno");
		modelAndView.addObject("aluno", repository.findByRa(nome)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		repository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("alunos", repository.findAll());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Aluno aluno, BindingResult result) {
		ModelAndView mv = new ModelAndView("CadastrarAluno");
		if (result.hasErrors()) {
			mv.addObject("fail", "Dados inválidos"); // quando fail nao eh nulo a msg aparece na tela
			return mv;
		}
		try {
			Aluno jaExiste = null;
			jaExiste = repository.findByRa(aluno.getRa());
			if (jaExiste == null) {
				repository.save(aluno);
				mv.addObject("success", "Aluno cadastrado com sucesso"); // success nao eh nulo
				return mv;
			} else {
				mv.addObject("fail", "Aluno já cadastrado."); // fail nao eh nulo a msg aparece na tela
				return mv;
			}
		} catch (Exception e) {
			mv.addObject("fail", "erro ===> " + e.getMessage());
			return mv;
		}
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualiza(@PathVariable("id") Long id, @Valid Aluno aluno, BindingResult result) {
		if (result.hasErrors()) {
			aluno.setId(id);
			return new ModelAndView("atualizaAluno");
		}
		Aluno umAluno = repository.findById(id).get();
		umAluno.setCurso(aluno.getCurso());
		umAluno.setNome(aluno.getNome());
		umAluno.setRa(aluno.getRa());
		repository.save(umAluno);
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("alunos", repository.findAll());
		return modelAndView;
	}
}