package com.fatec.demo.model;

import javax.persistence.*;

import javax.validation.constraints.*;

import org.hibernate.annotations.NaturalId;

@Entity(name = "Aluno")
public class Aluno {
	@NaturalId
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	@Column(nullable = false, length = 10)
	@NotEmpty(message = "O nome deve ser preenchido")
	private String nome;
	@Column(nullable = false, length = 13)
	@NotEmpty(message = "O RA deve ser preenchido")
	private String ra;
	@Column(nullable = false)
	@NotNull(message = "Curso invalido")
	@Size(min = 1, max = 50, message = "Curso deve ter entre 1 e 50 caracteres")
	private String curso;

	public Aluno() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Aluno(String i, String t, String a) {
		this.nome = i;
		this.ra = t;
		this.curso = a;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
}