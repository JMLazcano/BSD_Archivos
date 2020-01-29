
package baseDatos;

public class CommaInStringFoundExeption extends RuntimeException {
	private String commaInTitleFound;

	public CommaInStringFoundExeption(String t) {
		super("CommaInStringFoundExeption");
		this.commaInTitleFound = t;
	}

	public String toString() {
		return getMessage() + "\nComma detected in String: " + this.commaInTitleFound;
	}
}
