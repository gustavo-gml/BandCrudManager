package model;

public class LiveHouse {
	private Integer id;
	private String razaoSocial;
	private String nomeFantasia;
	private String Cnpj;
	private String endereco;
	
	public LiveHouse() {
		super();
	}
	
	public LiveHouse(int id) {
		this.id = id;
	}

	public LiveHouse(String razaoSocial, String nomeFantasia, String Cnpj, String endereco) {
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.Cnpj = Cnpj;
		this.endereco = endereco;
	}
	
	public LiveHouse(int id, String razaoSocial, String nomeFantasia, String Cnpj, String endereco) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.Cnpj = Cnpj;
		this.endereco = endereco;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return Cnpj;
	}

	public void setCnpj(String cnpj) {
		Cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Casa: " + this.nomeFantasia + " - Endereço: " + this.endereco;
	}
	
	
}
