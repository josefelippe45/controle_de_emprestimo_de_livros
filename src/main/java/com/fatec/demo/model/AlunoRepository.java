package com.fatec.demo.model;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {
	@Query("SELECT l FROM Aluno l WHERE l.nome = :nome")
	public Aluno findById(@Param("nome") String nome);
	
}