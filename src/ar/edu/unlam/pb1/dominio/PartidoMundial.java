package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.SectorEstadio;

public class PartidoMundial {

	// Constantes
	private static final int CAPACIDAD_MAXIMA = 300;
	private static final int CAPACIDAD_VIP = 25;

	// Atributos
	private String codigoPartido;
	private String partidoDetalle;

	private EntradaMundial[] entradasVendidas;

	private int cantidadEntradas; // Contador de entradas totales emitidas
	private int capacidadMaxima; // Capacidad total del estadio

	// SECTOR PALCO VIP NUMERADO (Usa matriz)
	private boolean[][] palcoVip;

	// SECTOR PLATEAS Y POPULAR (Usa un contador)
	private int capacidadSectoresComunes;
	private int entradasSectoresComunesVendidas;

	// Constructor
	public PartidoMundial(String codigoPartido, String partidoDetalle, int capacidadSectoresComunes) {

		this.codigoPartido = codigoPartido;
		this.partidoDetalle = partidoDetalle;

		// Sectores PLATEA Y POPULAR
		this.capacidadSectoresComunes = capacidadSectoresComunes;
		this.entradasSectoresComunesVendidas = 0;

		// Inicializamos el PALCO VIP
		this.palcoVip = new boolean[5][5]; // 25 asientos totales

		for (int fila = 0; fila < this.palcoVip.length; fila++) {
			for (int columna = 0; columna < this.palcoVip[fila].length; columna++) {
				this.palcoVip[fila][columna] = true;
			}
		}

		// capacidad maxima (Suma de Platea y Popular + Asientos VIP)
		this.capacidadMaxima = capacidadSectoresComunes + CAPACIDAD_VIP;

		// Inicializo la capacidad de entradas del partido
		this.entradasVendidas = new EntradaMundial[CAPACIDAD_MAXIMA];
		this.cantidadEntradas = 0;
	}

	// Getters
	public String getCodigoPartido() {
		return codigoPartido;
	}

	public String getPartidoDetalle() {
		return partidoDetalle;
	}

	public int getCantidadEntradas() {
		return cantidadEntradas;
	}

	// Métodos personalizados

	// 1. Emitir entrada para Sectores Platea Y Popular
	public boolean emitirEntrada(SectorEstadio sector, double precio) {

		if (sector != SectorEstadio.PALCO_VIP && this.entradasSectoresComunesVendidas < this.capacidadSectoresComunes
				&& this.cantidadEntradas < this.capacidadMaxima) {

			int numeroTicketUnico = this.cantidadEntradas + 1;

			// Creo mi nueva entrada
			EntradaMundial nuevaEntrada = new EntradaMundial(numeroTicketUnico, sector, precio);

			// Asigno a la cantidad de entradas disponibles mi nueva entrada
			this.entradasVendidas[cantidadEntradas] = nuevaEntrada;
			this.cantidadEntradas++;
			this.entradasSectoresComunesVendidas++;
			return true;

		}
		return false; // Limite de capacidad de Partido alcanzado
	}

	// 2. Emitir entrada para Palco Vip (Matriz)
	public boolean emitirEntrada(SectorEstadio sector, double precio, int fila, int columna) {

		if (sector.equals(SectorEstadio.PALCO_VIP) && fila >= 0 && fila < palcoVip.length && columna >= 0
				&& columna < palcoVip[0].length && this.cantidadEntradas < this.capacidadMaxima) {

			// Validamos asiento Libre
			if (this.palcoVip[fila][columna] == true) {
				this.palcoVip[fila][columna] = false; // lo ocupamos

				int numeroTicketUnico = this.cantidadEntradas + 1;

				// Creo mi nueva entrada
				EntradaMundial nuevaEntrada = new EntradaMundial(numeroTicketUnico, sector, precio);

				// Asigno a la cantidad de entradas disponibles mi nueva entrada
				this.entradasVendidas[cantidadEntradas] = nuevaEntrada;
				this.cantidadEntradas++;
				return true;
			}
		}
		return false; // Asiento ocupado o no existe fila/columna

	}

	public boolean procesarAcceso(int numeroTicket) {
		for (int i = 0; i < cantidadEntradas; i++) {
			if (this.entradasVendidas[i] != null && this.entradasVendidas[i].getNumeroTicket() == numeroTicket) {
				return this.entradasVendidas[i].registrarIngreso();
			}
		}

		return false; // El numero de ticket no existe o ya fue usado
	}

	public double calcularRecaudacion() {
		double total = 0.0;
		for (int i = 0; i < cantidadEntradas; i++) {
			total += entradasVendidas[i].getPrecioAbonado();
		}

		return total;
	}

	public String obtenerMapaPalcoVip() {

		String mapa = "\n>>> MAPA ASIENTOS PALCO VIP <<< ([✔️] LIBRE | [❌] OCUPADO) \n";
		mapa += "      C1   C2   C3   C4  C5\n"; // Encabezado columna

		for (int fila = 0; fila < this.palcoVip.length; fila++) {
			// Indicador fila
			mapa += "F" + (fila + 1) + " | ";
			for (int columna = 0; columna < this.palcoVip[fila].length; columna++) {
				if (this.palcoVip[fila][columna] == true) {
					mapa += "[✔️] "; // Si está en true está LIBRE

				} else {
					mapa += "[❌] "; // Sino está OCUPADA
				}

			}
			mapa += "\n";
		}
		return mapa;
	}

}
