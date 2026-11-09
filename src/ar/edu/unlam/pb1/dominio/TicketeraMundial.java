package ar.edu.unlam.pb1.dominio;

public class TicketeraMundial {

	private PartidoMundial[] partidosProgramados;
	private int cantidadPartidos;

	public TicketeraMundial(int maxPartidos) {

		this.cantidadPartidos = 0;
		this.partidosProgramados = new PartidoMundial[maxPartidos];

	}

	public boolean agregarPartido(PartidoMundial nuevoPartido) {

		if (this.cantidadPartidos < this.partidosProgramados.length) {
			this.partidosProgramados[this.cantidadPartidos] = nuevoPartido;
			this.cantidadPartidos++;
			return true; // Se pudo agregar

		}
		return false; // Capacidad máxima
	}

	public PartidoMundial buscarPartidoPorCodigo(String codigo) {

		for (int i = 0; i < this.cantidadPartidos; i++) {
			if (this.partidosProgramados[i] != null
					&& this.partidosProgramados[i].getCodigoPartido().equalsIgnoreCase(codigo)) {
				return this.partidosProgramados[i]; // Lo encontró y lo retorna
			}
		}
		return null; // No encontró el código
	}

}
