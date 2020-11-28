package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Funcionario {
	private Integer id;
	private String nome;
	private Date Data_nascimento;
	private String cargo;
	private BigDecimal salario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return Data_nascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		Data_nascimento = dataNascimento;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	

}
