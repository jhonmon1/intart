package juego.Reversi;

import juegos.Partida;
import juegos.agentes.*;
import juegos.base.*;

/**
 * Clase que representa el juego Reversi
 */
public class Reversi extends _Juego {

	public static Juego JUEGO = new Reversi();
	public static String nombreJugadorNegras = "JugadorNegras";
	public static String nombreJugadorBlancas = "JugadorBlancas";

	private Reversi() {
		super("Reversi", nombreJugadorNegras, nombreJugadorBlancas);
	}

	@Override
	public Estado inicio(Jugador... jugadores) {
		Tablero tablero = new Tablero(8, 8);
		return new EstadoReversi(tablero);
	}

	/**
	 * @param tablero
	 * @return true si hay empate.
	 */
	private static final boolean empate(Tablero tablero) {
		return (tablero.esFinal() && tablero.fichasBlancas() == tablero
				.fichasNegras());
	}

	/**
	 * @param tablero
	 * @param jugador
	 *            , es el nombre del jugador a evaluar su resultado
	 * @return true si hay un ganador para el tablero del parametro
	 */
	private static final boolean gana(Tablero tablero, String jugador) {
		if (jugador.equals(nombreJugadorNegras)) {
			return (tablero.fichasNegras() > tablero.fichasBlancas());
		} else {
			return (tablero.fichasBlancas() > tablero.fichasNegras());
		}
	}

	/**
	 * @param tablero
	 * @param fila
	 * @param columna
	 * @return el contenido de la casilla [fila][columna] del tablero.
	 */
	private static final char casilla(Tablero tablero, int fila, int columna) {
		return tablero.getCasilla(fila, columna);
	}

	/**
	 * Clase que representa el Estado del juego Reversi
	 */
	private class EstadoReversi implements Estado {

		public final Tablero tablero;

		public EstadoReversi(Tablero tablero) {
			this.tablero = tablero;
		}

		@Override
		public Juego juego() {
			return Reversi.this;
		}

		@Override
		public Jugador[] jugadores() {
			return jugadores;
		}

		/**
		 * Método que se encarga de devolver el estado del juego luego de
		 * aplicar un movimiento
		 * 
		 * @return Estado que se obtiene de aplicar el movimiento al estado
		 *         actual
		 */
		@Override
		public Estado siguiente(Movimiento movimiento) {
			tablero.actualizarTablero(movimiento);
			return new EstadoReversi(tablero);
		}

		/**
		 * IMPLEMENTAR!!!!!! NO SE ROBEN LA GUITA Y HAGANLO!!!!
		 */
		@Override
		public Movimiento[] movimientos(Jugador jugador) {
			// Si el jugador no es el que mueve, o ha terminado la partida,
			// retorna null.
			int jugadorHabilitado = tablero.getJugadorActual();
			if (!jugador.equals(jugadores[jugadorHabilitado])
					|| resultado(jugadores[jugadorHabilitado]) != null) {
				return null;
			}
			int ocupadas = (tablero & 0x1FF) | ((tablero >> 9) & 0x1FF);
			boolean jueganXs = (tablero & 0x40000) == 0;

			// Cuenta la cantidad de movimientos.
			int length = 0;
			int mascara = 1;
			for (int i = 0; i < 9; i++) {
				if ((mascara & ocupadas) == 0) {
					length++;
				}
				mascara = mascara << 1;
			}

			// Construye el vector de movimientos.
			Movimiento[] movs = new Movimiento[length];
			mascara = 1;
			for (int i = 0, j = 0; i < 9; i++) {
				if ((mascara & ocupadas) == 0) {
					movs[j++] = new MovimientoTateti(jueganXs ? mascara
							: (mascara << 9));
				}
				mascara = mascara << 1;
			}
			return movs;
		}

		private Double resultado(String jugador) {
			if (empate(tablero)) { // empatan
				return 0.0;
			} else if (gana(tablero, jugador)) { // gana jugador
				return 1.0;
			} else if(gana(tablero, (jugador.equals(nombreJugadorBlancas) ? nombreJugadorNegras : nombreJugadorBlancas))){ // gana jugador contrario
				return -1.0;
			}
			
			return null;
		}

		/**
		 * Resultado en el estado para el jugador dado. Debe retornar 0 para
		 * indicar empate, un número positivo para victoria y un número negativo
		 * para derrota. Debe retornar null si el estado de juego no es final.
		 */
		@Override
		public Double resultado(Jugador jugador) {
			return resultado(jugador.equals(jugadores[0]) ? nombreJugadorNegras
					: nombreJugadorBlancas);
		}

		/**
		 * Clase que representa los movimientos del juego Reversi
		 */
		public class MovimientoReversi implements Movimiento {
			public final int newFila;
			public final int newColumna;

			public MovimientoReversi(int newFila, int newColumna) {
				this.newFila = newFila;
				this.newColumna = newColumna;
			}

			@Override
			public Estado estado() {
				return EstadoReversi.this;
			}

			/**
			 * Jugador que puede realizar este movimiento.
			 * 
			 * @see Jugador
			 */
			@Override
			public Jugador jugador() {
				return jugadores[tablero.getJugadorActual()];
			}

			/**
			 * NO SE!!!!! IMPLEMENTAR
			 */
			@Override
			public String toString() {
				return null;
				/*
				 * int m = mascara; if (m > 0x1FF) { m = m >> 9; } int i = 0;
				 * for (; m > 1; i++) { m = m >> 1; } return CASILLAS[i];
				 */
			}
		}

		@Override
		public String toString() {
			return tablero.imprimirse();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Partida.completa(Reversi.JUEGO, new AgenteConsola(),
				new AgenteAleatorio()).toString());
	}
}
