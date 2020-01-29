/***Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19
* Clase abstracta de los metodos a implementar de en las extensiones*/
package baseDatos;

import java.sql.Date;
import java.util.List;

public abstract class SQL {
	private SQLTable sql;

	public SQL() {
		sql = new SQLTable();
	}

	public void addCols(String name, Type Type) {
		sql.addCols(name, Type);
	}

	public void addRows() {
		sql.addRows();
	}

	public String getTypes() {
		return sql.getTypes();
	}

	public int Count() {
		return sql.Count();
	}

	public boolean addValue(int row, String name, Date date, Object value) throws BDIndexOutOfBoundsException {
		return sql.addValue(row, name, value);
	}

	public boolean addRowValue(int row, Object... values) throws BDIndexOutOfBoundsException {
		return sql.addRowValue(row, values);
	}

	public boolean newRowValue(Object... values) throws BDIndexOutOfBoundsException {
		return sql.newRowValue(values);
	}

	public boolean addListValue(List<Object> values) throws BDIndexOutOfBoundsException {
		return sql.addListValue(values);
	}

	public Object deleteValue(int row, String name, Object value) throws BDIndexOutOfBoundsException {
		return sql.deleteValue(row, name, value);
	}

	public boolean deleteRows(int place) throws BDIndexOutOfBoundsException {
		return sql.deleteRows(place);
	}

	public boolean deleteCols(String name) throws BDIndexOutOfBoundsException {
		return sql.deleteCols(name);
	}

	public String toString() {
		return sql.toString();
	}

	public Object getValue(int row, String name) throws BDIndexOutOfBoundsException {
		return sql.getValue(row, name);
	}

	public Object maxValue(String name) throws BDIndexOutOfBoundsException {
		return sql.maxValue(name);
	}

	public Object minValue(String name) throws BDIndexOutOfBoundsException {
		return sql.minValue(name);
	}

	public Double average(String name) throws NumberFormatException, BDIndexOutOfBoundsException {
		return sql.average(name);
	}

	public Object moda(String name) throws BDIndexOutOfBoundsException {
		return sql.moda(name);
	}

	public Double sum(String name) throws NumberFormatException, BDIndexOutOfBoundsException {
		return sql.sum(name);
	}

	protected void setSQLTable(SQLTable t) {
		sql = t;
	}

	protected SQLTable searchTableN(String name, Object value) throws BDIndexOutOfBoundsException {
		return sql.searchTable(name, value);
	}

	protected SQLTable selectionN(String name, char operator, Object value) throws BDIndexOutOfBoundsException {
		return sql.selection(name, operator, value);
	}

	protected SQLTable selectionN(String name, String n2) throws BDIndexOutOfBoundsException {
		return sql.selection(name, n2);
	}

	protected SQLTable proyectionN(String... name) throws BDIndexOutOfBoundsException {
		return sql.proyection(name);
	}

	protected SQLTable getSQLTable() {
		return sql;
	}

	protected SQLTable union(SQLTable t) {
		return sql.union(t);
	}

	protected SQLTable xProduct(SQLTable t) {
		return sql.xProduct(t);
	}

	public String print() {
		return sql.print();
	}

	public void clear() {
		sql.clear();
	}

	public abstract void guardar(String nameAr);
}
