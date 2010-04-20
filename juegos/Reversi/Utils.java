package juegos.Reversi;

import java.util.ArrayList;
import java.util.List;

import juegos.Reversi.Reversi.EstadoReversi.MovimientoReversi;
import juegos.base.Movimiento;

public class Utils {

	/**
	 * Indica si las casillas están alineadas en una diagonal con pendiente negativa
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma diagonal con pendiente negativa
	 */
	public static boolean mismaDiagonalNegativa(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    + i + 1;
			newColumna = casillaColocar.columna() + i + 1;
			
			// Si la ficha que está en el tablero está en la misma diagonal que la casilla a colocar...
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Indica si las casillas están alineadas en una diagonal con pendiente positiva
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma diagonal con pendiente positiva
	 */
	public static boolean mismaDiagonalPositiva(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		int newFila = 0;
		int newColumna = 0;
		
		for (int i = -8; i <= 8; i++) {
			
			newFila    = casillaColocar.fila()    - i + 1;
			newColumna = casillaColocar.columna() + i - 1;
			
			// Si la ficha que está en el tablero está en la misma diagonal que la casilla a colocar...
			if(newFila == casillaEnElTablero.fila() && newColumna == casillaEnElTablero.columna())
				return true;
		}
		
		return false;
	}

	/**
	 * Indica si las casillas están alineadas en una misma columna
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma columna
	 */
	public static boolean mismaColumna(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		// Si la ficha que está en el tablero está en la misma columna que la casilla a colocar...
		if(casillaColocar.columna() == casillaEnElTablero.columna()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Indica si las casillas están alineadas en una misma fila
	 * @param casillaColocar
	 * @param casillaEnElTablero
	 * @return Si están en la misma fila
	 */
	public static boolean mismaFila(Casilla casillaColocar, Casilla casillaEnElTablero) {
		
		// Si la ficha que está en el tablero está en la misma fila que la casilla a colocar...
		if(casillaColocar.fila() == casillaEnElTablero.fila()) {			
			return true;
		}
		
		return false;
	}

	/**
	 * Método que se encarga de ordenar un array de movimientos.
	 * @param movs: array de movimientos.
	 */
	public static void ordenarMovimientos(Movimiento[] movs) 
	{
		boolean hayCambios = true;
		int v1 = 0;
		int v2 = 0;
		Movimiento movAux;
		 
		for (int i = 0; hayCambios ; i++) 
		{
			hayCambios = false;
		    for (int j = 0; j < movs.length - 1; j++) 
		    {
		    	v1 = ((MovimientoReversi)movs[j]).newColumna;
		    	v2 = ((MovimientoReversi)movs[j + 1]).newColumna;
		    	if (v1 > v2) 
		    	{
		    		movAux = movs[j];
		    		movs[j] = movs[j + 1];
		    		movs[j + 1] = movAux;
		    		hayCambios = true;
		    	}
		    }
		}		
	}

	/**
	 * Elimina los movimientos repetidos en el array.
	 * @param movs: array de movimientos del reversi.
	 */
	public static Movimiento[] borrarRepetidos(Movimiento[] movs) 
	{
		List<MovimientoReversi> aux = new ArrayList<MovimientoReversi>();
		MovimientoReversi mov;
		boolean agregar = true;
		
		for (int i = 0; i < movs.length; i++) 
		{
			mov = ((MovimientoReversi)movs[i]);
			for (MovimientoReversi mr : aux) 
			{
				if(mov.newColumna == mr.newColumna && mov.newFila == mr.newFila)
				{
					agregar = false;
					mr.casillasQueCambian.addAll(mov.casillasQueCambian);
				}
			}
			if(agregar) {
				aux.add(mov);
			} else {
				agregar = true;
			}
		}
		Movimiento[] movimientos = new Movimiento[aux.size()];
		int count = 0;
		for (MovimientoReversi movimientoReversi : aux) 
		{
			movimientos[count] = movimientoReversi;
			count++;
		}
		return movimientos;
	}
	
}
