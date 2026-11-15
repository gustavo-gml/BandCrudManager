package model;

import java.time.LocalDate;

public class Band {
	private int id;
	private String name;
	private Integer members;
	private LocalDate fundationDate;
	private String musicGenre;

	public Band() {
		this(0);
	}
	
	public Band(int id) {
		this.id = id;
		
	}
	
	public Band(int id, String name, int members, LocalDate fundationDate, String musicGenre) {
		this.id = id;
		this.name = name;
		this.members = members;
		this.fundationDate = fundationDate;
		this.musicGenre = musicGenre;
	}
	
	public Band(String name, int members, LocalDate fundationDate, String musicGenre) {
		this.name = name;
		this.members = members;
		this.fundationDate = fundationDate;
		this.musicGenre = musicGenre;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMembers() {
		return members;
	}

	public void setMembers(Integer members) {
		this.members = members;
	}

	public LocalDate getFundationDate() {
		return fundationDate;
	}

	public void setFundationDate(LocalDate fundationDate) {
		this.fundationDate = fundationDate;
	}
	

	public String getMusicGenre() {
		return musicGenre;
	}

	public void setMusicGenre(String musicGenre) {
		this.musicGenre = musicGenre;
	}
	
	@Override
	public String toString() {
		return this.getName() +  " - " + this.getMusicGenre();
	}
	
}
