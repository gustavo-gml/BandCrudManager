package model;

import java.time.LocalDateTime;

public class Show {
	private Integer id;
	private Band band;
	private LiveHouse liveHouse;
	private Double valorCache;
	private LocalDateTime dataHora;
	private Integer tempoShow;
	
	public Show() {
		super();
	}

	public Show(int id) {
		this.id = id;
	}
	
	public Show(Band band, LiveHouse liveHouse, Double valorCache, LocalDateTime dataHora, int tempoShow) {
		this.band = band;
		this.liveHouse = liveHouse;
		this.valorCache = valorCache;
		this.dataHora = dataHora;
		this.tempoShow = tempoShow;
	}
	
	public Show(int id, Band band, LiveHouse liveHouse, Double valorCache, LocalDateTime dataHora, int tempoShow) {
		this(band, liveHouse, valorCache, dataHora, tempoShow);
		this.id = id;
	}

	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}
	public LiveHouse getLiveHouse() {
		return liveHouse;
	}
	public void setLiveHouse(LiveHouse liveHouse) {
		this.liveHouse = liveHouse;
	}
	public Double getValorCache() {
		return valorCache;
	}
	public void setValorCache(Double valorCache) {
		this.valorCache = valorCache;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public Integer getTempoShow() {
		return tempoShow;
	}
	public void setTempoShow(Integer tempoShow) {
		this.tempoShow = tempoShow;
	}
	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Banda: " + this.band + " - Data: " + this.dataHora;
	}
}
