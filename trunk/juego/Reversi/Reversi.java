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

	// Variables de uso global
	public static Juego JUEGO = new Reversi();
	public static String nombreJugadorNegras = "JugadorNegras";
	public static String nombreJugadorBlancas = "JugadorBlancas";

	public static final String[] FILAS = { "1", "2", "3", "4", "5", "6", "7", "8" };
	public static final String[] COLUMNAS = { "A", "B", "C", "D", "E", "F",	"G", "H" };

	// Creamos un juego reversi para 2 Jugadores, uno fichas negras y otro
	// fichas balncas.
	private Reversi() {
		super("Reversi", "JugadorNegras", "JugadorBlancas");
	}

	/**
	 * Creamos el tablero inicial con las fichas en cada correspondiente lugar.
	 */
	@Override
	public Estado inicio(Jugador... jugadores) {
		Tablero tablero = new Tablero(8, 8, 0);
		tablero.setCasilla(3, 3, 'B');
		tablero.setCasilla(3, 4, 'N');
		tablero.setCasilla(4, 3, 'N');
		tablero.setCasilla(4, 4, 'B');
		
		return new EstadoReversi(tablero);
	}

	/**
	 * Funcion que evalua el tablero para ver si hay empate dado que el tablero
	 * corresponde a un estado final
	 * 
	 * @param Tablero
	 *            , es el tablero a identificar si hay empate, se va a dar en el
	 *            caso de que no haya mas movimientos para ninguno de los
	 *            jugadores y ademas cada uno de ellos tenga la misma cantidad
	 *            de fichas.
	 * @return booleano indicando si hay empate.
	 */
	private static final boolean empate(Tablero tablero) {
		return (tablero.cantidadFichas('B') == tablero.cantidadFichas('N'));
	}

	/**
	 * Funcion que evalua el tablero para ver si hay victoria de un determinado
	 * jugador dado que el tablero corresponde a un estado final
	 * 
	 * @param tablero
	 * @param jugador
	 *            , es el nombre del jugador a evaluar su resultado
	 * @return true si hay un ganador para el tablero del parametro
	 */
	private static final boolean gana(Tablero tablero, String jugador) {
		if (jugador.equals(nombreJugadorNegras)) {
			return (tablero.cantidadFichas('N') > tablero.cantidadFichas('B'));
		} else {
			return (tablero.cantidadFichas('B') > tablero.cantidadFichas('N'));
		}
	}

	/**
	 * Clase que representa el Estado del juego Reversi, dado por un determinado
	 * tablero
	 */
	public class EstadoReversi implements Estado {

		public final Tablero tablero;

		public EstadoReversi(Tablero tablero) {			
			this.tablero = tablero;
		}

		public Tablero getTablero()
		{
			return tablero;
		}
		
		/**
		 *@return Juego al cual pertenece el estado.
		 */
		@Override
		public Juego juego() {
			return Reversi.this;
		}

		/**
		 * @return Lista de Jugadores que juegan el juego de dicho estado
		 */
		@Override
		public Jugador[] jugadores() {
			return jugadores;
		}

		/**
		 * Método que se encarga de devolver el estado del juego luego de
		 * aplicar un movimiento.
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
		 * Retorna todos los posibles movimientos para un jugador
		 * 
		 * @return null en caso de que el jugador no es el que mueve o ha
		 *         terminado la partida.
		 */
		@Override
		public Movimiento[] movimientos(Jugador jugador) {
			
			
			int jugadorMover = gatIndice(jugadores, jugador); 
			
			if(tablero.getJugadorActual() != jugadorMover)
			{
				return null;
			}
			char charJugadorHabilitado = jugadorMover == 0 ? 'N' : 'B';
			char charJugadorContrario = jugadorMover == 0 ? 'B' : 'N';
			
			// Lista de fichas en el tablero para el jugador dado
			List<Casilla> listaDeFichasEnTablero = tablero.getFichasJugador(
					charJugadorHabilitado);
			
			// Lista de fichas donde sería posible colocar
			List<Casilla> listaDeFichasDondeColocar = tablero.getFichasDondeColocar();

			Movimiento mov;
			List<Movimiento> listaMovs = new ArrayList<Movimiento>();
			
			for (Casilla casillaColocar : listaDeFichasDondeColocar) {
				
				// Para cada casilla en donde podríamos colocar, verificamos
				// que exista algun tipo de movimiento
				List<Casilla> casillasQueCambian = new ArrayList<Casilla>();
				
				for (Casilla casillaEnElTablero : listaDeFichasEnTablero) {

					//Hacemos ésto debido a que cada verificacion de movimiento
					//en caso de que exista va cargando en casillasQueCambian
					//las casillas del contrario que cambiarian al hacer ese movimiento
					//si hacemos el OR con las coldiciones podría llegar a verificar una
					//sola y no setear correctamente todas las casillas que cambian
					//al hacer el movimiento
					boolean resultado1 = hayMovimientoEnFila(tablero, casillaColocar,
							casillaEnElTablero, charJugadorContrario, casillasQueCambian);
					boolean resultado2 = hayMovimientoEnColumna(tablero, casillaColocar,
							casillaEnElTablero, charJugadorContrario, casillasQueCambian);
					boolean resultado3 = hayMovimientoEnDiagonalNegativa(tablero,
							casillaColocar, casillaEnElTablero, charJugadorContrario, 
							casillasQueCambian);
					boolean resultado4 = hayMovimientoEnDiagonalPositiva(tablero,
							casillaColocar, casillaEnElTablero, charJugadorContrario,
							casillasQueCambian);
							
					if (resultado1||resultado2||resultado3||resultado4) 
					{
						mov = new MovimientoReversi(casillaColocar.fila(),
								casillaColocar.columna(), jugador, casillasQueCambian);
						listaMovs.add(mov);
					}
				}
			}

			// En caso de no haber movimientos actualiza el tablero cambiando el jugador 
			// actual al siguiente y retorna null como movimiento
			if (listaMovs.size() == 0) {
				tablero.actualizarTablero(null);
				return null;
			}

			// Construye el vector de movimientos.
			Movimiento[] movs = new Movimiento[listaMovs.size()];
			int count = 0;

			for (Movimiento movimiento : listaMovs) {
				movs[count] = movimiento;
				count++;
			}

			movs = Utils.borrarRepetidos(movs);
			Utils.ordenarMovimientos(movs);
			
			return movs;
		}

		/*
		 * Dada la lista de jugadores y un jugador particular devuelve
		 * el indice de la posicion del jugador en esa lista o -1 en caso
		 * de no pertencer a la lista
		 */
		private int gatIndice(Jugador[] jugadores, Jugador jugador) {
			int indice = 0;
			for(Jugador unJugador : jugadores)
			{
				if(unJugador.equals(jugador))
				{
					return indice;
				}
				indice++;
			}
			return -1;
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
		 * Resultado en el estado para el jugador dado.
		 * 
		 * @param jugador
		 *            Jugador a evaluar el resultado.
		 * @return 0 para indicar empate, 1 victoria del jugador, -1 derrota del
		 *         jugador debido a que es un juego de suma cero corresponde a
		 *         la victoria del adversario. null si el estado de juego no es
		 *         final.
		 */
		private Double resultado(String jugador) {
			if (this.esFinal()) {
				if (empate(tablero)) // empatan
					return 0.0;

				if (gana(tablero, jugador)) // gana jugador
					return 1.0;

				// gana jugador contrario
				if (gana(
						tablero,
						(jugador.equals(nombreJugadorBlancas) ? nombreJugadorNegras
								: nombreJugadorBlancas)))
					return -1.0;
			}
			return null; // El estado no es final
		}

		/**
		 * Verificamos si el estado es un estado final.
		 * 
		 * @return true si ningun jugador tiene movimientos, false si para algun
		 *         jugador existe al menos un movimiento.
		 */
		public boolean esFinal() {
			Jugador[] todosLosJugadores = jugadores();
			int actualAux = tablero.getJugadorActual();
			int aux;
			for (Jugador unJugador : todosLosJugadores) {
				aux = unJugador.toString().equals("JugadorNegras") ? 0 : 1;
				tablero.setJugadorActual(aux);
				if (movimientos(unJugador) != null) {
					tablero.setJugadorActual(actualAux);
					return false;
				}
			}
			tablero.setJugadorActual(actualAux);
			return true;
		}

		/**
		 * Indica si hay movimientos válidos en la diagonal con pendiente
		 * negativa entre las dos casillas
		 */
		private boolean hayMovimientoEnDiagonalNegativa(Tablero tablero,
				Casilla casillaColocar, Casilla casillaEnElTablero,
				char jugadorContrario, List<Casilla> casillasQueCambian) {

			List<Casilla> casillasQueCambianAux = new ArrayList<Casilla>();

			if (Utils.mismaDiagonalNegativa(casillaColocar, casillaEnElTablero)) {

				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();

				int filaFinal = casillaColocar.fila();

				// Agarro el que tiene el menor valor de columna
				if (casillaColocar.columna() < casillaEnElTablero.columna()) {
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();

					filaFinal = casillaEnElTablero.fila();
				}

				// Si las dos casillas están una al lado de otra -> no hay
				// movimiento posible
				if (filaInicio + 1 == filaFinal)
					return false;

				for (int filaActual = filaInicio + 1; filaActual < filaFinal; filaActual++) {
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if (!tablero.hayFichaAdversario(filaActual, columnaInicio, jugadorContrario)) {
						return false;
					}

					// Agregamos ésta casilla ya que debería cambiar al agregar
					// la ficha
					casillasQueCambianAux.add(new Casilla(filaActual,
							columnaInicio));
				}

				// Agregamos cada una de las casillas que cambiarían al agregar
				// ésta ficha
				casillasQueCambian.addAll(casillasQueCambianAux);
				return true;
			}

			return false;
		}

		/**
		 * Indica si hay movimientos válidos en la diagonal con pendiente
		 * positiva entre las dos casillas
		 */
		private boolean hayMovimientoEnDiagonalPositiva(Tablero tablero,
				Casilla casillaColocar, Casilla casillaEnElTablero,
				char jugadorContrario, List<Casilla> casillasQueCambian) {

			if (Utils.mismaDiagonalPositiva(casillaColocar, casillaEnElTablero)) {

				List<Casilla> casillasQueCambianAux = new ArrayList<Casilla>();

				int filaInicio = casillaEnElTablero.fila();
				int columnaInicio = casillaEnElTablero.columna();

				int filaFinal = casillaColocar.fila();

				// Agarro el que tiene el menor valor de columna
				if (casillaColocar.columna() < casillaEnElTablero.columna()) {
					filaInicio = casillaColocar.fila();
					columnaInicio = casillaColocar.columna();

					filaFinal = casillaEnElTablero.fila();
				}

				// Si las dos casillas están una al lado de otra -> no hay
				// movimiento posible
				if (filaInicio - 1 == filaFinal) {
					return false;
				}

				for (int filaActual = filaInicio - 1; filaActual > filaFinal; filaActual--) {
					columnaInicio += 1;
					// Si no hay ficha de adversario -> no se puede mover
					if (!tablero.hayFichaAdversario(filaActual, columnaInicio, jugadorContrario)) {
						return false;
					}

					// Agregamos ésta casilla ya que debería cambiar al agregar
					// la ficha
					casillasQueCambianAux.add(new Casilla(filaActual,
							columnaInicio));
				}

				// Agregamos cada una de las casillas que cambiarían al agregar
				// ésta ficha
				casillasQueCambian.addAll(casillasQueCambianAux);
				return true;
			}

			return false;
		}

		/**
		 * Indica si hay movimientos válidos en la columna entre las dos
		 * casillas
		 * 
		 * @param casillaColocar
		 * @param casillaEnElTablero
		 * @return true si estan las 2 casillas en la misma columna y puede
		 *         comer
		 */
		private boolean hayMovimientoEnColumna(Tablero tablero,
				Casilla casillaColocar, Casilla casillaEnElTablero,
				char jugadorContrario, List<Casilla> casillasQueCambian) {

			if (Utils.mismaColumna(casillaColocar, casillaEnElTablero)) {

				List<Casilla> casillasQueCambianAux = new ArrayList<Casilla>();

				int fil1, fil2;
				if (casillaColocar.fila() < casillaEnElTablero.fila()) {
					fil1 = casillaColocar.fila();
					fil2 = casillaEnElTablero.fila();
				} else {
					fil2 = casillaColocar.fila();
					fil1 = casillaEnElTablero.fila();
				}

				// Si las dos casillas están una al lado de otra -> no hay
				// movimiento posible
				if (fil1 + 1 == fil2)
					return false;

				// Verificamos que en la misma columna entre las dos filas solo
				// haya fichas del adversario
				for (int i = fil1 + 1; i < fil2; i++) {
					if (!tablero.hayFichaAdversario(i, casillaColocar.columna(), jugadorContrario))
						return false;

					// Agregamos ésta casilla ya que debería cambiar al agregar
					// la ficha
					casillasQueCambianAux.add(new Casilla(i, casillaColocar
							.columna()));
				}

				// Agregamos cada una de las casillas que cambiarían al agregar
				// ésta ficha
				casillasQueCambian.addAll(casillasQueCambianAux);
				return true;
			}
			return false;
		}

		/**
		 * Indica si hay movimientos válidos en la fila entre las dos casillas
		 * 
		 * @param casillaColocar
		 *            Casilla de posible colocacion de ficha
		 * @param casillaEnElTablero
		 *            Casilla en donde existe una ficha
		 * @return True si las dos casillas estan en la misma fila y puede
		 *         comer.
		 */
		private boolean hayMovimientoEnFila(Tablero tablero,
				Casilla casillaColocar, Casilla casillaEnElTablero,
				char jugadorContrario, List<Casilla> casillasQueCambian) {

			if (Utils.mismaFila(casillaColocar, casillaEnElTablero)) {

				List<Casilla> casillasQueCambianAux = new ArrayList<Casilla>();

				int col1, col2;
				if (casillaColocar.columna() < casillaEnElTablero.columna()) {
					col1 = casillaColocar.columna();
					col2 = casillaEnElTablero.columna();
				} else {
					col2 = casillaColocar.columna();
					col1 = casillaEnElTablero.columna();
				}

				// Si las dos casillas están una al lado de otra -> no hay
				// movimiento posible
				if (col1 + 1 == col2)
					return false;

				// Verificamos que en la misma fila entre las dos columnas solo
				// haya fichas del adversario
				for (int i = col1 + 1; i < col2; i++) {
					if (!tablero.hayFichaAdversario(casillaColocar.fila(), i, jugadorContrario))
						return false;

					// Agregamos ésta casilla ya que debería cambiar al agregar
					// la ficha
					casillasQueCambianAux.add(new Casilla(
							casillaColocar.fila(), i));
				}

				// Agregamos cada una de las casillas que cambiarían al agregar
				// ésta ficha
				casillasQueCambian.addAll(casillasQueCambianAux);
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
			public List<Casilla> casillasQueCambian;
			public Jugador jugador;

			/**
			 * Creamos un movimiento de una nueva posicion para un determinado jugador
			 * @param newFila  Nueva fila del movimiento	
			 * @param newColumna Columna del movimiento
			 * @param jugador	Jugador al cual corresponde el movimiento
			 * @param casillasQueCambian Casillas que cambian al realizar el movimiento 
			 * en el tablero.
			 */
			public MovimientoReversi(int newFila, int newColumna, Jugador jugador,
					List<Casilla> casillasQueCambian) {
				this.newFila = newFila;
				this.newColumna = newColumna;
				this.casillasQueCambian = casillasQueCambian;
				this.jugador =  jugador;
			}
			
			@Override
			public Estado estado() {
				return EstadoReversi.this;
			}

			/**
			 * Devulve el Jugador que puede realizar este movimiento.
			 * 
			 * @retunr Jugador
			 */
			@Override
			public Jugador jugador() {
				return jugador;
			}

			/**
			 * Convierte un movimiento a string
			 */
			@Override
			public String toString() {
				return COLUMNAS[newColumna] + FILAS[newFila];
			}
		}

		@Override
		public String toString() {
			return tablero.imprimirse();
		}

		@Override
		public Estado copiar() {
			return (new EstadoReversi(tablero.copiar()));
		}
	}

	public static void main(String[] args) throws Exception {
		AgenteHeuristico1 agenteH1 = new AgenteHeuristico1(3);
		AgenteHeuristicoDificil agenteHD = new AgenteHeuristicoDificil(3);
		AgenteAleatorio aleatario = new AgenteAleatorio();
		
		System.out.println(Partida.completa(Reversi.JUEGO, agenteH1, agenteHD).toString());
	}
}
