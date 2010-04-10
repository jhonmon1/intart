package juego.Reversi;

import java.util.ArrayList;
import java.util.List;

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
		super("Reversi", "JugadorNegras", "JugadorBlancas");
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
	 * Clase que representa el Estado del juego Reversi
	 */
	public class EstadoReversi implements Estado {

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
		 * 
		 */
		@Override
		public Movimiento[] movimientos(Jugador jugador) 
		{
			// Si el jugador no es el que mueve, o ha terminado la partida,
			// retorna null.
			int jugadorHabilitado = tablero.getJugadorActual();
			
			if (!jugador.equals(jugadores[jugadorHabilitado]) 
					|| resultado(jugadores[jugadorHabilitado]) != null) {
				return null;
			}
			List<Casilla> listaDeFichasEnTablero = tablero.getFichasJugador();
			List<Casilla> listaDeFichasDondeColocar = tablero.getFichasDondeColocar();
			
			Movimiento mov;
			List<Movimiento> listaMovs = new ArrayList<Movimiento>();
			for (Casilla casillaColocar : listaDeFichasDondeColocar) {
				
				for (Casilla casillaEnElTablero : listaDeFichasEnTablero) {
					
					if(hayMovimientoEnFila(tablero, casillaColocar, casillaEnElTablero) ||
					   hayMovimientoEnColumna(tablero, casillaColocar, casillaEnElTablero) ||
					   hayMovimientoEnDiagonalNegativa(tablero, casillaColocar, casillaEnElTablero) ||
					   hayMovimientoEnDiagonalPositiva(tablero, casillaColocar, casillaEnElTablero))
					{
						mov = new MovimientoReversi(casillaColocar.fila(), casillaColocar.columna());
						listaMovs.add(mov);
					}
				}
			}
			
			// Construye el vector de movimientos.
			Movimiento[] movs = new Movimiento[listaMovs.size()];
			int count = 0;
			
			for (Movimiento movimiento : listaMovs) {
				movs[count] = movimiento;
				count++;
			}
			
			return movs;
		}
		
		private Double resultado(String jugador) {
			if (empate(tablero)) // empatan
				return 0.0;
			
			if (gana(tablero, jugador)) // gana jugador
				return 1.0;
			
			if(gana(tablero, (jugador.equals(nombreJugadorBlancas) ? nombreJugadorNegras : nombreJugadorBlancas))) // gana jugador contrario
				return -1.0;
						
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
		 * Indica si hay movimientos válidos en la diagonal con pendiente negativa entre las dos casillas
		 */
		private boolean hayMovimientoEnDiagonalNegativa(Tablero tablero, Casilla casillaColocar, 
				Casilla casillaEnElTablero) {
			
			if(Utils.mismaDiagonalNegativa(casillaColocar, casillaEnElTablero)) {
					
				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();
					
				int filaFinal = casillaColocar.fila();
					
				// Agarro el que tiene el menor valor de columna
				if(casillaColocar.columna() < casillaEnElTablero.columna()){
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();
						
					filaFinal = casillaEnElTablero.fila();
				}
					
				int columnaActual = columnaInicio;
					
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(filaInicio + 1 == filaFinal)
					return false;
					
				for(int filaActual = filaInicio + 1; filaActual < filaFinal; filaActual++){
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if(! tablero.hayFichaAdversario(filaActual, columnaActual)){
						return false;
					}
				}
					
				return true;
			}
			
			return false;
		}
		
		/**
		 * Indica si hay movimientos válidos en la diagonal con pendiente positiva entre las dos casillas
		 */
		private boolean hayMovimientoEnDiagonalPositiva(Tablero tablero, Casilla casillaColocar, 
				Casilla casillaEnElTablero) {
			
			if(Utils.mismaDiagonalPositiva(casillaColocar, casillaEnElTablero)) {
					
				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();
				
				int filaFinal = casillaColocar.fila();
				
				// Agarro el que tiene el menor valor de columna
				if(casillaColocar.columna() < casillaEnElTablero.columna()){
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();
					
					filaFinal = casillaEnElTablero.fila();
				}
				
				int columnaActual = columnaInicio;
				
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(filaInicio - 1 == filaFinal){
					return false;
				}
				
				for(int filaActual = filaInicio - 1; filaActual < filaFinal; filaActual--){
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if(! tablero.hayFichaAdversario(filaActual, columnaActual)){
						return false;
					}
				}
				
				return true;
			}
			
			return false;
		}
		
		/** Indica si hay movimientos válidos en la columna entre las dos casillas
		 * @param casillaColocar
		 * @param casillaEnElTablero
		 * @return true si estan las 2 casillas en la misma columna y puede comer
		 */
		private boolean hayMovimientoEnColumna(Tablero tablero, Casilla casillaColocar, Casilla casillaEnElTablero) {
			
			if(Utils.mismaColumna(casillaColocar, casillaEnElTablero)) {
				int fil1, fil2;
				if(casillaColocar.fila() < casillaEnElTablero.fila()) {
					fil1 = casillaColocar.fila();
					fil2 = casillaEnElTablero.fila();
				} else {
					fil2 = casillaColocar.fila();
					fil1 = casillaEnElTablero.fila();
				}
				
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(fil1 + 1 == fil2)
					return false;
				
				for (int i = fil1 + 1; i < fil2; i++) {
					if(! tablero.hayFichaAdversario(i, casillaColocar.columna())) // Si no hay ficha de adversario -> no se puede mover
						return false;
				}
					
				return true;
			}
			return false;
		}
		
		/** Indica si hay movimientos válidos en la fila entre las dos casillas
		 * @param casillaColocar
		 * @param casillaEnElTablero
		 * @return True si las dos casillas estan en la misma fila y puede comer.
		 */
		private boolean hayMovimientoEnFila(Tablero tablero, Casilla casillaColocar, Casilla casillaEnElTablero) {
			
			if(Utils.mismaFila(casillaColocar, casillaEnElTablero)) {
				
				int col1, col2;
				if(casillaColocar.columna() < casillaEnElTablero.columna()) {
					col1 = casillaColocar.columna();
					col2 = casillaEnElTablero.columna();
				} else {
					col2 = casillaColocar.columna();
					col1 = casillaEnElTablero.columna();
				}
				
				// Si las dos casillas están una al lado de otra -> no hay movimiento posible
				if(col1 + 1 == col2)
					return false;
				
				for (int i = col1 + 1; i < col2; i++) {
					if(! tablero.hayFichaAdversario(casillaColocar.fila(), i)) // Si no hay ficha de adversario -> no se puede mover
						return false;
				}
				
				return true;
			}
			return false;
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
