/***Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19
* Clase row para crear listas de los valores de las instancias o filas de la tabla/clase tabla*/
package baseDatos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

class Row {
	private List<Type> types;
	private List<Object> values;
	private int size;

	public Row() {
		types = new ArrayList<Type>();
		values = new ArrayList<Object>();
		size = 0;
	}

	public void addCols(Type type) {
		types.add(type);
		size++;
		update();
	}

	public boolean addValue(int place, Object value) throws BDIndexOutOfBoundsException {
		// Si la posición ya existe la sobreescribe si son del mismo tipo
		update();
		if (place < 0 || place >= size)
			throw new BDIndexOutOfBoundsException(place, size);
		if (equalType(place, value)) {
			if (value instanceof String)
				value = Row.verifyString((String) value);
			values.set(place, value);
			return true;
		}
		return false;
	}

	public boolean addValue(Object... values) throws BDIndexOutOfBoundsException {
		if (size != values.length)
			return false;
		byte bandera = 0;
		for (int i = 0; i < size; i++) {
			if (!(equalType(i, values[i]))) {
				bandera++;
				break;
			}
		}
		if (bandera > 0)
			return false;
		for (int i = 0; i < size; i++) {
			addValue(i, values[i]);
		}
		return true;
	}

	public Object deleteValue(int place, Object value) throws BDIndexOutOfBoundsException {
		update();
		if (place < 0 || place >= size)
			throw new BDIndexOutOfBoundsException(place, size);
		if (value.equals(values.get(place))) {
			values.set(place, "-");
			return value;
		}
		return null;
	}
//borra columna
	public boolean deleteCols(int place) throws BDIndexOutOfBoundsException {
		// Al borrar la columna borra los valores del final, no la columna en si (Ya no
		// funciona por separado)
		if (place < 0 || place >= size)
			throw new BDIndexOutOfBoundsException(place, size);
		values.remove(place);
		size--;
		update();
		return true;

	}

	public String toString() {
		update();
		String f = "";
		for (int i = 0; i < values.size() - 1; i++) {
			f += "" + values.get(i) + ",";
		}
		f += "" + values.get(values.size() - 1);
		return f;
	}

	public String print() {
		String t = this.toString();
		return t.replaceAll(",", "\t");
	}

	public List<Type> getTypes() {
		return types;
	}

	public Object getValue(int place) throws BDIndexOutOfBoundsException {
		update();
		if (place < 0 || place >= size)
			throw new BDIndexOutOfBoundsException(place, size);
		return values.get(place);
	}

	public List<Object> getValueList() {
		return values;
	}

	public void setFormat(List<Type> t, int s) {
		types = t;
		size = s;
		update();
	}

	public void clear() {
		types.clear();
		values.clear();
		size = 0;
	}
//clona las filas
	public Row clone() {
		Row r = new Row();
		for (int i = 0; i < size; i++) {
			Object o;
			o = getValue(i);
			Type s = types.get(i);
			r.addCols(s);
			r.addValue(i, Row.cloneCell(o));
		}
		return r;
	}
//combina filas
	public Row combineRows(Row r) throws BDIndexOutOfBoundsException {
		Row r2 = clone();
		for (int i = 0; i < r.size; i++) {
			Object o = r.getValue(i);
			Type s = r.types.get(i);
			r2.addCols(s);
			r2.addValue(r2.size - 1, Row.cloneCell(o));
		}
		return r2;
	}
	//clona la celda
	public static Object cloneCell(Object o) {
		Object o2;
		if (o instanceof Date)
			o2 = ((Date) o).clone();
		else if (o instanceof Integer)
			o2 = new Integer(((Integer) o).intValue());
		else if (o instanceof Double)
			o2 = new Double(((Double) o).doubleValue());
		else if (o instanceof String)
			o2 = ((String) o).toString();
		else
			o2 = null;
		return o2;
	}

	private void update() {
		// actuliza las filas para que todas midan siempre lo mismo
		// se implementa por cada funcion inplementada (a exepcion de las estaticas)
		while (values.size() < size) {
			values.add("-");
		}
		while (values.size() > size) {
			values.remove(values.size() - 1);
		}
	}

	private boolean equalType(int place, Object o) {
		switch (types.get(place)) {
		case INT:
			return (o instanceof Integer);
		case DOUBLE:
			return (o instanceof Double);
		case DATE:
			return (o instanceof Date);
		case STRING:
			return (o instanceof String);
		}
		return false;
	}

	public static String verifyString(String t) throws CommaInStringFoundExeption {
		if (t.contains(","))
			throw new CommaInStringFoundExeption(t);
		return t;
	}
}
