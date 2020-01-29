/***Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19
* Clase que extiende sql donde se utilizan archivos en formato csv y obtiene metodos del padre*/
package baseDatos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.print.attribute.standard.JobPriority;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArchivoCSV extends SQL {
	private String FileName;

	public ArchivoCSV() {
		super();
	}

	/**	/**Crear una instacioa de la clase, leer archivo mediante el nombre indicado
	 *
	 * @param nameAr
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArchivoCSV(String nameAr) throws FileNotFoundException, IOException {

        nameAr += ".csv";
		SQLTable t = new SQLTable();
		String[] arr = null;
		List<String> titles = null;
		List<Type> types = null;
		String cad = "";
		try {
			File myFile = new File(nameAr);
			if (!(myFile.exists()))
				System.out.println("The file does not exist.");
			Scanner sc = new Scanner(myFile);
			FileReader f = new FileReader(nameAr);
			BufferedReader b = new BufferedReader(f);
			if ((cad = sc.nextLine()) != null) {
				arr = cad.split(",");
				titles = Arrays.asList(arr);
			}

			if ((cad = sc.nextLine()) != null) {
				arr = cad.split(",");
				types = SQLTable.parseTypeCell(arr);
				t.setFormat(types, titles, titles.size());
				t.addListValue(SQLTable.parseCell(arr));
			}
			while (sc.hasNextLine()) {
				cad = sc.nextLine();
				arr = cad.split(",");
				t.addListValue(SQLTable.parseCell(arr));
			}
			b.close();
		} catch (Exception e) {
			e.printStackTrace();
			t = null;
		}
		super.setSQLTable(t);
	}

	public ArchivoCSV clone() {
		ArchivoCSV a = new ArchivoCSV();
		a.setSQLTable(super.getSQLTable().clone());
		return a;
	}

	public ArchivoCSV searchTable(String name, Object value) throws BDIndexOutOfBoundsException {
		ArchivoCSV a = new ArchivoCSV();
		a.setSQLTable(super.searchTableN(name, value));// Simular la sobre escritura
		return a;
	}

	public ArchivoCSV selection(String name, char operator, Object value) throws BDIndexOutOfBoundsException {
		ArchivoCSV a = new ArchivoCSV();
		a.setSQLTable(super.selectionN(name, operator, value));// Simular la sobre escritura
		return a;
	}

	public ArchivoCSV selection(String name, String n2) throws BDIndexOutOfBoundsException {
		ArchivoCSV a = new ArchivoCSV();
		a.setSQLTable(super.selectionN(name, n2));// Simular la sobre escritura
		return a;
	}

	public ArchivoCSV proyection(String... name) throws BDIndexOutOfBoundsException {
		ArchivoCSV a = new ArchivoCSV();
		a.setSQLTable(super.proyectionN(name));// Simular la sobre escritura
		return a;
	}

	public ArchivoCSV union(ArchivoCSV a) {
		ArchivoCSV a2 = clone();
		SQLTable t = a2.getSQLTable().union(a.getSQLTable());
		a2.setSQLTable(t);
		return a2;
	}

	public ArchivoCSV union(ArchivoCSV... a) {
		ArchivoCSV a2 = clone();
		List<SQLTable> s = new ArrayList<SQLTable>();
		SQLTable sf = a2.getSQLTable();
		for (ArchivoCSV r : a)
			s.add(r.getSQLTable());
		sf = sf.union(s.toArray(new SQLTable[1]));
		a2.setSQLTable(sf);
		return a2;
	}

	public ArchivoCSV xProduct(ArchivoCSV a) {
		ArchivoCSV a2 = clone();
		SQLTable t = a2.getSQLTable().xProduct(a.getSQLTable());
		a2.setSQLTable(t);
		return a2;
	}

	public ArchivoCSV xProduct(ArchivoCSV... a) {
		ArchivoCSV a2 = clone();
		List<SQLTable> s = new ArrayList<SQLTable>();
		SQLTable sf = a2.getSQLTable();
		for (ArchivoCSV r : a)
			s.add(r.getSQLTable());
		sf = sf.xProduct(s.toArray(new SQLTable[1]));
		a2.setSQLTable(sf);
		return a2;
	}

	public void guardar(String nameAr) {
		nameAr += ".csv";
		SQLTable t = super.getSQLTable();
		File artabla = new File(nameAr);
		boolean ext = artabla.exists();

		if (ext)
			artabla.delete();
		try {
			PrintWriter myWriter = new PrintWriter(nameAr);
			myWriter.println((t.titlesString()));
			for (int i = 0; i < t.Count(); i++)
				myWriter.println((t.rowToString(i)));
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();

		}
	}
	public void ConvertToJSON(String nameAr) {

		nameAr += ".json";
		SQLTable t = super.getSQLTable();
		File artabla = new File(nameAr);
		boolean ext = artabla.exists();

		if (ext)
			artabla.delete();
		JSONObject objj = new JSONObject();
		JSONArray list = new JSONArray();


		String[] columnas = null;
		columnas = t.titlesString().split(",");
		String[][] filas=new String[columnas.length][t.Count()];

		for(int fi=0;fi<t.Count();fi++){
			filas[fi]=t.rowToString(fi).split(",");
		}
		for(int i=0;i<columnas.length;i++){
			for(int j=0;j<t.Count();j++){
				System.out.println(filas[i][j]);
			}
		}

		int tl=0;
		for(int i=0;i<columnas.length;i++) {
			tl=0;
			for(int j=0; j<filas.length;j++) {
				objj.put(columnas[tl], filas[i][j]);
				tl++;
			}

			list.add(i,objj);
		}

		FileWriter file = null;
		try {
			file = new FileWriter("datosfinales.json");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			file.write(list.toString());
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		System.out.println("Write succesful to file");
	}
	public void ConvertToXML(String nameAr) {

		nameAr += ".xml";
		SQLTable t = super.getSQLTable();
		File artabla = new File(nameAr);
		boolean ext = artabla.exists();

		if (ext)
			artabla.delete();
		try {
			DocumentBuilderFactory docfact=DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild=docfact.newDocumentBuilder();
			Document xmldoc=docBuild.newDocument();

			String[] columnas = null;
			System.out.println("//////////////////");
			columnas = t.titlesString().split(",");
			String[][] filas=new String[columnas.length][t.Count()];

			for(int fi=0;fi<t.Count();fi++){
				filas[fi]=t.rowToString(fi).split(",");
			}
			for(int i=0;i<columnas.length;i++){
				for(int j=0;j<t.Count();j++){
					System.out.println(filas[i][j]);
				}
			}


			Element root=xmldoc.createElement("root");
			xmldoc.appendChild(root);

			Element main = null;
			Element son = null;
			int colu=0;
			for(int i=0;i<columnas.length;i++){
				main=xmldoc.createElement("row");
				root.appendChild(main);
				colu=0;
				for(int j=0;j<t.Count();j++){
					son=xmldoc.createElement(columnas[colu]);
					Text sontx=xmldoc.createTextNode(filas[i][j]);
					son.appendChild(sontx);
					System.out.println(filas[i][j]);
					main.appendChild(son);
					colu++;
				}

			}

			DOMSource source=new DOMSource(xmldoc);
			File f=new File(nameAr);
			Result result=new StreamResult(f);
			TransformerFactory transfactory=TransformerFactory.newInstance();
			Transformer transformer=transfactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			transformer.transform(source,result);
			System.out.println("Write succesful to file");

		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();

		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}


}