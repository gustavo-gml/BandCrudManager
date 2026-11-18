package model;

import java.time.LocalDate;

public class LiveHouse {
	private Integer id;
	private Employer employer;
	private String nomeFantasia;
	private String email;
	private String endereco;
	private Integer capacidade;
	private LocalDate dataInauguracao;
	private boolean estacionamento;
	
	public LiveHouse() {
		super();
	}
	
	public LiveHouse(int id) {
		this.id = id;
	}

	public LiveHouse(Employer employer, String nomeFantasia, String email, String endereco,
			Integer capacidade, LocalDate dataInauguracao, boolean estacionamento) {
		this.employer = employer;
		this.nomeFantasia = nomeFantasia;
		this.email = email;
		this.endereco = endereco;
		this.capacidade = capacidade;
		this.dataInauguracao = dataInauguracao;
		this.estacionamento = estacionamento;
	}
	
	public LiveHouse(int id, Employer employer, String nomeFantasia, String email, String endereco,
			Integer capacidade, LocalDate dataInauguracao, boolean estacionamento) {
		this(employer, nomeFantasia, email, endereco, capacidade, dataInauguracao, estacionamento);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public LocalDate getDataInauguracao() {
		return dataInauguracao;
	}

	public void setDataInauguracao(LocalDate dataInauguracao) {
		this.dataInauguracao = dataInauguracao;
	}

	public boolean isEstacionamento() {
		return estacionamento;
	}

	public void setEstacionamento(boolean estacionamento) {
		this.estacionamento = estacionamento;
	}
	
	@Override
	public String toString() {
		return "Casa: " + this.nomeFantasia + " - Endereço: " + this.endereco;
	}
}
