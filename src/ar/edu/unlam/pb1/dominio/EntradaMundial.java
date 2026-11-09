package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.SectorEstadio;

public class EntradaMundial {

	// Atributos
	private int numeroTicket; // Número autoincremental
	private SectorEstadio sectorElegido;
	private double precioAbonado;
	private boolean fueUsada;

	// Constructor
	public EntradaMundial(int numeroTicket, SectorEstadio sector, double precioAbonado) {
		this.numeroTicket = numeroTicket;
		this.sectorElegido = sector;
		this.precioAbonado = precioAbonado;

		this.fueUsada = false; // Inicializa lista para usar
	}

	// Getters
	public int getNumeroTicket() {
		return numeroTicket;
	}

	public SectorEstadio getSectorElegido() {
		return sectorElegido;
	}

	public double getPrecioAbonado() {
		return precioAbonado;
	}

	public boolean isFueUsada() {
		return fueUsada;
	}

	// toString
	@Override
	public String toString() {
		return "EntradaMundial [numeroTicket=" + numeroTicket + ", sectorElegido=" + sectorElegido + ", precioAbonado="
				+ precioAbonado + ", fueUsada=" + fueUsada + "]";
	}

	// Métodos Personalizados
	public boolean registrarIngreso() {

		if (!this.fueUsada) {
			this.fueUsada = true;
			return true; // Ingreso exitoso
		}

		return false; // No pudo ingresar | entrada inválida
	}

}
