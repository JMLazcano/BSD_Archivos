/**Se utiliza cuando el indice de filas o columnas esta fuera del parametro en SQLTable y Row (se usa regresando la posición en numero y el tamaño
 * de la tabla o el nombre de la columna y la cantidad de columnas para SQLTable)
 * @author César Palomino, Miguel Figueroa
 * */
package baseDatos;

public class BDIndexOutOfBoundsException extends RuntimeException {
	private String index;
	private int size;

	public BDIndexOutOfBoundsException(int i, int s) {
		super("BDIndexOutOfBoundsException");
		this.index = "" + i;
		this.size = s;
	}

	public BDIndexOutOfBoundsException(String i, int s) {
		super("BDIndexOutOfBoundsException");
		this.index = i;
		this.size = s;
	}

	public String toString() {
		return getMessage() + "\nIndex: " + this.index + ", Size: " + this.size;
	}
}
