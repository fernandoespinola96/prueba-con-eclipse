package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.dominio.PartidoMundial;
import ar.edu.unlam.pb1.dominio.TicketeraMundial;
import ar.edu.unlam.pb1.dominio.enums.SectorEstadio;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class InterfazTicketera {

	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		
		int cantidadPartidos = ingreseEntero("\n🏟️Ingrese la cantidad máxima de Partidos a programar: ");
		teclado.nextLine();

		TicketeraMundial gestionTicketera = new TicketeraMundial(cantidadPartidos);
		MenuPrincipal menuOpcion = null;

		do {
			menuOpcion = ingresarOpcionDelmenuPrincipal();

			switch (menuOpcion) {
			case REGISTRAR_UN_PARTIDO:
				registrarPartido(gestionTicketera);
				break;
			case VENDER_EMITIR_ENTRADA:
				venderEntrada(gestionTicketera);
				break;
			case CONTROLAR_ACCESO:
				controlarAcceso(gestionTicketera);
				break;
			case VER_RECAUDACION:
				verRecaudacion(gestionTicketera);
				break;

			case SALIR:
				mostrarPorPantalla("\nGracias por utlizar el Sistema de Gestión de Entradas Mundialista⚽. ");
				break;
			}

		} while (!menuOpcion.equals(MenuPrincipal.SALIR));

		teclado.close();
	}
	
	
	private static void registrarPartido(TicketeraMundial gestionTicketera) {

		mostrarPorPantalla("\n>>> REGISTRO DE NUEVO PARTIDO <<< ");
		String codigo = ingresarString("\nIngrese el código único del Partido: (ej. ARG-01): ");
		String detalle = ingresarString("\nIngrese el detalle del Partido: (ej. Argentina vs México): ");
		int capacidadComun = ingreseEntero("\nIngrese una capacidad para los Sectores comunes (PLATEA/POPULAR): ");
		teclado.nextLine();

		PartidoMundial nuevoPartido = new PartidoMundial(codigo, detalle, capacidadComun);

		boolean partidoAgregado = gestionTicketera.agregarPartido(nuevoPartido);

		if (partidoAgregado) {
			mostrarPorPantalla("\n✅ Partido registrado con éxito. ");

		} else {
			mostrarMensajeError("\n❌ No se pudo registrar el Partido. Capacidad máxima de la ticketera alcanzada. ");
		}

	}
	
	private static void venderEntrada(TicketeraMundial gestionTicketera) {

		mostrarPorPantalla("\n>>> VENTA DE ENTRADAS <<<");
		String codigo = ingresarString("\nIngrese el código del Partido: ");

		PartidoMundial partidoEncontrado = gestionTicketera.buscarPartidoPorCodigo(codigo);

		if (partidoEncontrado == null) {
			mostrarMensajeError("\n❌ Código de Partido no encontrado. ");
			return;
		}

		SectorEstadio sectorElegido = ingresarSectorEstadio("\n>>> SELECCIONE EL SECTOR <<<");
		double precio = sectorElegido.getPrecio();

		boolean entradaVendida = false;

		// 1. Si es Sector VIP elige fila y columna
		if (sectorElegido.equals(SectorEstadio.PALCO_VIP)) {
			
			mostrarPorPantalla(partidoEncontrado.obtenerMapaPalcoVip());
			
			mostrarPorPantalla("\nSector Palco VIP seleccionado: (Dimensiones 1 a 5) ");
			int fila = ingreseEntero("Ingrese número de Fila: ");
			int columna = ingreseEntero("\nIngrese número de columna: ");
			teclado.nextLine();

			// y utilizamos el método de 4 parámetros
			entradaVendida = partidoEncontrado.emitirEntrada(sectorElegido, precio, fila - 1, columna - 1);
		}

		// 2. Si es Sector común, utilizamos el otro método de 2 parámetros:
		else {
			entradaVendida = partidoEncontrado.emitirEntrada(sectorElegido, precio);
		}

		if (entradaVendida) {
			mostrarPorPantalla("\n✅ ¡Entrada adquirida con éxito!");
			mostrarPorPantalla("💲PRECIO COBRADO: $ " + precio);
			mostrarPorPantalla("💺SECTOR ELEGIDO: " + sectorElegido);
			mostrarPorPantalla("👉 SU NÚMERO DE TICKET ASIGNADO ES: " + partidoEncontrado.getCantidadEntradas());
			mostrarPorPantalla("❌ RECUERDE ESTE NÚMERO PARA PASAR POR EL MOLINETE.\n");
		} else {
			mostrarMensajeError("\n❌ No se pudo concretar la venta. Capacidad del Sector agotada o asiento Vip ocupado. ");
		}

	}

	private static void controlarAcceso(TicketeraMundial gestionTicketera) {
		mostrarPorPantalla("\n>>> CONTROL DE ACCESO A MOLINETES: <<<");
		String codigo = ingresarString("\nIngrese el Código del Partido: ");
		PartidoMundial partidoEncontrado = gestionTicketera.buscarPartidoPorCodigo(codigo);

		if (partidoEncontrado == null) {
			mostrarMensajeError("\n❌ Código de Partido no encontrado. ");
			return;
		}

		int numeroTicket = ingreseEntero("\nEscanee / Ingrese número de ticket para validar: ");
		teclado.nextLine();

		boolean accesoPermitido = partidoEncontrado.procesarAcceso(numeroTicket);

		if (accesoPermitido) {
			mostrarPorPantalla("\n✅ INGRESO AUTORIZADO. ¡Bienvenido al Estadio!. Disfrute el Partido. ");
		} else {
			mostrarMensajeError("\n❌ INGRESO DENEGADO. Ticket inválido o ya fue utilizado previamente. ");
		}

	}

	private static void verRecaudacion(TicketeraMundial gestionTicketera) {
		mostrarPorPantalla("\n>>> DETALLE DE RECAUDACIÓN: <<<");
		String codigo = ingresarString("\nIngrese el Código del Partido: ");
		PartidoMundial partidoEncontrado = gestionTicketera.buscarPartidoPorCodigo(codigo);

		if (partidoEncontrado == null) {
			mostrarMensajeError("\n❌ Código de Partido no encontrado.");
			return;
		}

		mostrarPorPantalla("\n========================================");
		mostrarPorPantalla("\nPartido: " + partidoEncontrado.getPartidoDetalle());
		mostrarPorPantalla("\n💰 Recaudación Total: " + partidoEncontrado.calcularRecaudacion());
		mostrarPorPantalla("========================================\n");
	}
	

	private static MenuPrincipal ingresarOpcionDelmenuPrincipal() {

		int opcion;
		MenuPrincipal menuElegido = null;

		do {

			mostrarPorPantalla(MenuPrincipal.obtenerOpcionesDeMenu());
			opcion = ingreseEntero("\n👉 Ingrese una opción del Menú: ");
			teclado.nextLine();

			menuElegido = MenuPrincipal.obtenerOpcionesPorId(opcion);

			if (menuElegido == null) {
				mostrarMensajeError("\n❌ Opción Incorrecta. Ingrese una opción del Menú. ");
			}
		} while (menuElegido == null);

		return menuElegido;
	}

	private static SectorEstadio ingresarSectorEstadio(String mensaje) {

		int opcion = 0;

		do {
			mostrarPorPantalla(mensaje);
			mostrarPorPantalla("1. TRIBUNA_POPULAR\n2. PLATEA_ALTA\n3. PLATEA_BAJA\n4. PALCO_VIP");
			opcion = ingreseEntero("Seleccione una opción numeral del 1 al 4: ");
			teclado.nextLine();
		} while (opcion < 1 || opcion > 4);

		SectorEstadio sectorElegido = null;
		
		switch (opcion) {
		case 1:
			sectorElegido = SectorEstadio.TRIBUNA_POPULAR;
			break;
		case 2:
			sectorElegido = SectorEstadio.PLATEA_ALTA;
			break;
		case 3:
			sectorElegido = SectorEstadio.PLATEA_BAJA;
			break;
		default:
			sectorElegido = SectorEstadio.PALCO_VIP;
			break;
		}
		return sectorElegido;
	}

	private static int ingreseEntero(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.nextInt();
	}
	
	private static String ingresarString(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.nextLine();
	}

	private static void mostrarPorPantalla(String mensaje) {
		System.out.println(mensaje);

	}

	private static void mostrarMensajeError(String mensaje) {
		System.err.println(mensaje);

	}
}
