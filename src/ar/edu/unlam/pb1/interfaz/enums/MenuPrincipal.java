package ar.edu.unlam.pb1.interfaz.enums;

public enum MenuPrincipal {

	REGISTRAR_UN_PARTIDO(1, "➕ Registrar nuevo partido programado. "),
	VENDER_EMITIR_ENTRADA(2, "🎟️ Vender/Emitir entrada para un partido. "),
	CONTROLAR_ACCESO(3, "🚧 Controlar acceso en molinete (Escanear entrada) "),
	VER_RECAUDACION(4, "💰 Ver recaudación total de un partido. "), 
	SALIR(0, "❌ Salir "),;

	// Atributos
	private int opcion;
	private String descripcion;

	// Constructor
	MenuPrincipal(int opcion, String descripcion) {
		this.opcion = opcion;
		this.descripcion = descripcion;

	}

	public int getOpcion() {
		return opcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static String obtenerOpcionesDeMenu() {

		String reset = "\u001B[0m";
		String cian = "\u001B[36m";
		String azul = "\u001B[34m";
		String rojo = "\u001B[91m";
		String amarillo = "\u001B[1;33m";
		
		MenuPrincipal[] opciones = MenuPrincipal.values();

		String menu = cian + "========================================" + reset + "\n" 
				+ "🏆 TICKETERA " + amarillo + "ACCESOS " +reset + "MUNDIAL 2026 🏆" + "\n" + cian
				+ "========================================" + reset + "\n";

		for (int ordinal = 0; ordinal < opciones.length; ordinal++) {

			if (opciones[ordinal] == MenuPrincipal.SALIR) {
				menu += "\n" + rojo + opciones[ordinal].getOpcion() + ". " + opciones[ordinal].getDescripcion() + reset;
			} else {
				menu += "\n" + opciones[ordinal].getOpcion() + "." + opciones[ordinal].getDescripcion();
			}
		}

		menu += "\n" + cian + "========================================" + reset;

		return menu;
	}

	public static MenuPrincipal obtenerOpcionesPorId(int opcion) {

		MenuPrincipal[] opciones = MenuPrincipal.values();

		for (int i = 0; i < opciones.length; i++) {

			if (opciones[i].getOpcion() == opcion) {
				return opciones[i];
			}
		}

		return null;
	}

}
