/***Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19
* Clase padre SQLTable donde se crea los contenedores de almacenamiento*/
package baseDatos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SQLTable {
	/***
	 *Variables utilizadas,listas para almacenamiento de atributos de tabla-
	 */
	private List<String> names;
	private List<Type> types;
	private List<Row> rows;
	protected int srows;
	protected int scols;
/***constructor de la tabla*/
	public SQLTable() {
		names = new ArrayList<String>();
		types = new ArrayList<Type>();
		rows = new ArrayList<Row>();
		srows = 0;
		scols = 0;
	}
/***metodo de clonar tabla*/
	public SQLTable clone() {
		SQLTable t2 = new SQLTable();
		t2.setFormat(types, names, scols);
		for (Row r : rows) {
			t2.setRows(r.clone());
		}
		return t2;
	}
/***agregar columnas a la tabla*/
	public void addCols(String name, Type Type) {
		names.add(Row.verifyString(name));
		types.add(Type);
		scols++;
		update();
	}
/***agregar filas a la tabla*/
	public void addRows() {
		Row r = new Row();
		rows.add(r);
		srows++;
		r.setFormat(types, scols);
	}
/**poner fila que ya existe*/
	private void setRows(Row r) {
		rows.add(r);
		srows++;
		r.setFormat(types, scols);
	}
/***obtener los tipos de datos en la tabla*/
	public String getTypes() {
		if (scols == 0)
			return "-";
		String r = "";
		for (int i = 0; i < scols - 1; i++) {
			r += "" + names.get(i) + ":\t" + types.get(i) + "\n";
		}
		r += "" + names.get(scols - 1) + ":\t" + types.get(scols - 1);
		return r;
	}
/****contar numero de filas en la tabla*/
	public int Count() {
		return srows;
	}

	public boolean addValue(int row, String name, Object value) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1) {
			throw new BDIndexOutOfBoundsException(name, scols);
		}
		if (row < 0 || row >= srows)
			throw new BDIndexOutOfBoundsException(row, srows);
		Row r = rows.get(row);
		return r.addValue(cols, value);
	}
/***agregar valores a filas indicadas*/
	public boolean addRowValue(int row, Object... values) throws BDIndexOutOfBoundsException {
		update();
		if (row < 0 || row >= srows)
			throw new BDIndexOutOfBoundsException(row, srows);
		Row r = rows.get(row);
		return r.addValue(values);
	}

	/**agregar instancias, es decir filas y valores

	 * @param values
	 * @return
	 * @throws BDIndexOutOfBoundsException
	 */
	public boolean newRowValue(Object... values) throws BDIndexOutOfBoundsException {
		addRows();
		return addRowValue(rows.size() - 1, values);
	}
	//agregar valores de una lista
	public boolean addListValue(List<Object> values) throws BDIndexOutOfBoundsException {
		addRows();
		return addRowValue(rows.size() - 1, values.toArray());
	}

	/**Borrar valor de una tabla
	 * @param row
	 * @param name
	 * @param value
	 * @return
	 * @throws BDIndexOutOfBoundsException
	 */

	public Object deleteValue(int row, String name, Object value) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		if (row < 0 || row >= srows)
			throw new BDIndexOutOfBoundsException(row, srows);
		Row r = rows.get(row);
		return r.deleteValue(cols, value);
	}

	/**borrar filas o instancias
	 * @param place
	 * @return
	 * @throws BDIndexOutOfBoundsException
	 */
	public boolean deleteRows(int place) throws BDIndexOutOfBoundsException {
		if (place < 0 || place >= srows)
			throw new BDIndexOutOfBoundsException(place, srows);
		rows.remove(place);
		srows--;
		return true;
	}
//borrar columnas
	public boolean deleteCols(String name) throws BDIndexOutOfBoundsException {
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		names.remove(cols);
		types.remove(cols);
		for (int i = 0; i < rows.size(); i++) {
			Row r = rows.get(i);
			r.deleteCols(cols);
		}
		scols--;
		update();
		return true;
	}
//metodo to String
	public String toString() {
		update();
		if (scols == 0 && srows == 0)
			return "-";
		String f = "";
		if (scols == 0) {
			for (int i = 0; i < srows - 1; i++) {
				f += "-" + "\n";
			}
			f += "-";
			return f;
		}

		for (int i = 0; i < names.size() - 1; i++) {
			f += "" + names.get(i) + ",";
		}
		f += "" + names.get(names.size() - 1);
		if (srows == 0)
			return f;
		f += "\n";
		for (int i = 0; i < srows - 1; i++) {
			f += "" + rows.get(i) + "\n";
		}
		f += "" + rows.get(srows - 1);
		return f;
	}
//Obtener el valor de la fila
	public Object getValue(int row, String name) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		if (row < 0 || row >= srows)
			throw new BDIndexOutOfBoundsException(row, srows);
		Row r = rows.get(row);
		return r.getValue(cols);
	}
//El valor maximo de una columna
	public Object maxValue(String name) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		switch (types.get(cols)) {
		case INT:
			List<Integer> r = new ArrayList<Integer>();
			for (int i = 0; i < srows; i++) {
				r.add((Integer) getValue(i, name));
			}
			return r.stream().max(Comparator.comparing(Integer::valueOf));
		case DOUBLE:
			List<Double> r1 = new ArrayList<Double>();
			for (int i = 0; i < srows; i++) {
				r1.add((Double) getValue(i, name));
			}
			return r1.stream().max(Comparator.comparing(Double::valueOf));
		case STRING:
			List<String> r2 = new ArrayList<String>();
			for (int i = 0; i < srows; i++) {
				r2.add((String) getValue(i, name));
			}
			return r2.stream().max(Comparator.comparing(String::valueOf));
		case DATE:
			List<Date> r3 = new ArrayList<Date>();
			for (int i = 0; i < srows; i++) {
				r3.add((Date) getValue(i, name));
			}
			return r3.stream().max(new Comparator<Date>() {// Comparador de dates
				public int compare(Date o1, Date o2) {
					return o1.compareTo(o2);
				}
			});
		}
		return null;
	}
//Valor minimo de una columna
	public Object minValue(String name) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		switch (types.get(cols)) {
		case INT:
			List<Integer> r = new ArrayList<Integer>();
			for (int i = 0; i < srows; i++) {
				r.add((Integer) getValue(i, name));
			}
			return r.stream().min(Comparator.comparing(Integer::valueOf));
		case DOUBLE:
			List<Double> r1 = new ArrayList<Double>();
			for (int i = 0; i < srows; i++) {
				r1.add((Double) getValue(i, name));
			}
			return r1.stream().min(Comparator.comparing(Double::valueOf));
		case STRING:
			List<String> r2 = new ArrayList<String>();
			for (int i = 0; i < srows; i++) {
				r2.add((String) getValue(i, name));
			}
			return r2.stream().min(Comparator.comparing(String::valueOf));
		case DATE:
			List<Date> r3 = new ArrayList<Date>();
			for (int i = 0; i < srows; i++) {
				r3.add((Date) getValue(i, name));
			}
			return r3.stream().min(new Comparator<Date>() {// Comparador de dates
				public int compare(Date o1, Date o2) {
					return o1.compareTo(o2);
				}
			});
		}
		return null;
	}
//promedio de una columna
	public Double average(String name) throws NumberFormatException, BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		if (types.get(cols) == Type.INT || types.get(cols) == Type.DOUBLE) {
			double d = 0.0;
			for (int i = 0; i < srows; i++) {
				d += Double.parseDouble(getValue(i, name).toString());
			}
			d /= srows;
			return d;
		}
		return null;
	}
//la moda de una columna
	public Object moda(String name) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		List<Object> r = new ArrayList<Object>();
		for (int i = 0; i < srows; i++) {
			r.add(getValue(i, name));
		}
		Object o = null;
		int frecuency = 0;
		for (Object r2 : r) {
			if (Collections.frequency(r, r2) > frecuency) {
				o = r2;
				frecuency = Collections.frequency(r, r2);
			}
		}
		return o;
	}
//Suma la valores de un columna
	public Double sum(String name) throws NumberFormatException, BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		if (types.get(cols) == Type.INT || types.get(cols) == Type.DOUBLE) {
			double d = 0.0;
			for (int i = 0; i < srows; i++) {
				d += Double.parseDouble(getValue(i, name).toString());
			}
			return d;
		}
		return null;
	}
//busca en la tabla el objetos o valor
	public SQLTable searchTable(String name, Object value) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		SQLTable t = new SQLTable();
		t.setFormat(types, names, scols);
		for (Row r : rows) {
			if (r.getValue(cols).equals(value))
				t.setRows(r.clone());
		}
		return t;
	}
//Obtiene los datos mediante una condicion
	public SQLTable selection(String name, char operator, Object value) throws BDIndexOutOfBoundsException {
		// Solo es para seleccionar tablas mayores o menoes a un valor
		// si se quieren usar para buscar un valor usar searchTable
		// y para igualar valores de una tabla se usa selection(String t1, String t2)
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		SQLTable t = new SQLTable();
		t.setFormat(types, names, scols);
		switch (operator) {
		case '<':
			for (Row r : rows) {
				if (value instanceof Date) {
					if (!(r.getValue(cols) instanceof Date))
						return null;
					Date d = (Date) r.getValue(cols);
					if (d.compareTo((Date) value) < 0)
						t.setRows(r.clone());
				}
				if (value instanceof Double) {
					if (!(r.getValue(cols) instanceof Double))
						return null;
					Double d = (Double) r.getValue(cols);
					if (d.compareTo((Double) value) < 0)
						t.setRows(r.clone());
				}
				if (value instanceof Integer) {
					if (!(r.getValue(cols) instanceof Integer))
						return null;
					Integer d = (Integer) r.getValue(cols);
					if (d.compareTo((Integer) value) < 0)
						t.setRows(r.clone());
				}
				if (value instanceof String) {
					if (!(r.getValue(cols) instanceof String))
						return null;
					String d = (String) r.getValue(cols);
					if (d.compareTo((String) value) < 0)
						t.setRows(r.clone());
				}
			}
			break;
		case '>':
			for (Row r : rows) {
				if (value instanceof Date) {
					if (!(r.getValue(cols) instanceof Date))
						return null;
					Date d = (Date) r.getValue(cols);
					if (d.compareTo((Date) value) > 0)
						t.setRows(r.clone());
				}
				if (value instanceof Double) {
					if (!(r.getValue(cols) instanceof Double))
						return null;
					Double d = (Double) r.getValue(cols);
					if (d.compareTo((Double) value) > 0)
						t.setRows(r.clone());
				}
				if (value instanceof Integer) {
					if (!(r.getValue(cols) instanceof Integer))
						return null;
					Integer d = (Integer) r.getValue(cols);
					if (d.compareTo((Integer) value) > 0)
						t.setRows(r.clone());
				}
				if (value instanceof String) {
					if (!(r.getValue(cols) instanceof String))
						return null;
					String d = (String) r.getValue(cols);
					if (d.compareTo((String) value) > 0)
						t.setRows(r.clone());
				}
			}
			break;
		default:
			t = null;
		}
		return t;
	}

	public SQLTable selection(String name, String n2) throws BDIndexOutOfBoundsException {
		update();
		int cols = names.indexOf(name);
		if (cols == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		int cols2 = names.indexOf(n2);
		if (cols2 == -1)
			throw new BDIndexOutOfBoundsException(name, scols);
		SQLTable t = new SQLTable();
		t.setFormat(types, names, scols);
		for (Row r : rows) {
			if (r.getValue(cols).equals(r.getValue(cols2)))
				t.setRows(r.clone());
		}
		return t;
	}

	public SQLTable proyection(String... name) throws BDIndexOutOfBoundsException {
		SQLTable t = new SQLTable();
		for (int i = 0; i < srows; i++) {
			t.addRows();
		}
		for (String s : name) {
			int cols = names.indexOf(s);
			if (cols == -1)
				return null;
			t.addCols(s, types.get(cols));
			for (int i = 0; i < srows; i++) {
				t.addValue(i, s, Row.cloneCell(getValue(i, s)));
			}
		}
		return t;
	}

	public SQLTable union(SQLTable t) {
		if (scols != t.scols)
			return null;
		for (int i = 0; i < scols; i++) {
			if (types.get(i) != t.types.get(i))
				return null;
		}
		SQLTable t2 = clone();
		for (Row r : t.rows) {
			t2.setRows(r.clone());
		}
		return t2;
	}

	public SQLTable union(SQLTable... l) {
		SQLTable t2 = clone();
		for (SQLTable t : l) {
			t2 = t2.union(t);
			if (t2 == null)
				return null;
		}
		return t2;
	}

	public SQLTable xProduct(SQLTable t) {
		SQLTable t2 = new SQLTable();
		for (int i = 0; i < scols; i++) {
			t2.addCols(names.get(i), types.get(i));
		}
		for (int i = 0; i < t.scols; i++) {
			t2.addCols(t.names.get(i), t.types.get(i));
		}
		for (Row r : rows) {
			for (Row r2 : t.rows) {
				t2.setRows(r.combineRows(r2));
			}
		}
		return t2;
	}

	public SQLTable xProduct(SQLTable... l) {
		SQLTable t2 = clone();
		for (SQLTable t : l) {
			t2 = t2.xProduct(t);
			if (t2 == null)
				return null;
		}
		return t2;
	}

	public void setFormat(List<Type> t, List<String> n, int sc) {
		types = new ArrayList<Type>(t);
		scols = sc;
		names = new ArrayList<String>(n);
	}

	public String print() {
		String t = this.toString();
		return t.replaceAll(",", "\t");
	}

	public static Type parseTypeCell(String s) {// Esta funcion usara el CSV para saber el tipo de sus celdas
		Type s2;
		try {
			Date.valueOf(s);
			s2 = Type.DATE;
		} catch (Exception ex) {
			try {
				Integer.parseInt(s);
				s2 = Type.INT;
			} catch (Exception ex2) {
				try {
					Double.parseDouble(s);
					s2 = Type.DOUBLE;
				} catch (Exception ex3) {
					s2 = Type.STRING;
				}
			}
		}
		return s2;
	}

	public static List<Type> parseTypeCell(String... names) {
		List<Type> l = new ArrayList<Type>();
		for (String s : names) {
			l.add(SQLTable.parseTypeCell(s));
		}
		return l;
	}

	public static Object parseCell(String s) {// Esta funcion usara el CSV para saber el tipo de sus celdas
		Object o;
		try {
			o = Date.valueOf(s);
		} catch (Exception ex) {
			try {
				o = Integer.parseInt(s);
			} catch (Exception ex2) {
				try {
					o = Double.parseDouble(s);
				} catch (Exception ex3) {
					o = s;
				}
			}
		}
		return o;
	}

	public static List<Object> parseCell(String... array) {
		List<Object> l = new ArrayList<Object>();
		for (String s : array) {
			l.add(SQLTable.parseCell(s));
		}
		return l;
	}

	public String rowToString(int i) {
		Row r = rows.get(i);
		return r.toString();
	}

	public String titlesString() {
		String r = "";
		for (int i = 0; i < names.size() - 1; i++) {
			r += "" + names.get(i) + ",";
		}
		r += "" + names.get(names.size() - 1);
		return r;
	}

	public void clear() {
		names.clear();
		types.clear();
		rows.clear();
		srows = 0;
		scols = 0;
	}
//actualiza la tabla
	private void update() {
		for (int i = 0; i < srows; i++) {
			Row r = rows.get(i);
			r.setFormat(types, scols);
		}
	}

}
