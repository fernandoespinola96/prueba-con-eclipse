package ar.edu.unlam.pb1.dominio.enums;

public enum SectorEstadio {

	TRIBUNA_POPULAR(600.0), 
	PLATEA_ALTA(800.0), 
	PLATEA_BAJA(1200.0), 
	PALCO_VIP(2000.0);

	private final double precio;

	SectorEstadio(double precio) {
		this.precio = precio;
	}

	public double getPrecio() {
		return precio;
	}
	
}
