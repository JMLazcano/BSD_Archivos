/***Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19*/

package baseDatos;
//Enumeracion para diferentes tipos de datos
public enum Type {
	INT("int"), DOUBLE("double"), STRING("String"), DATE("Date");
	private final String typeName;

	private Type(String name) {
		this.typeName = name;
	}

	public String toString() {
		return this.typeName;
	}
}
