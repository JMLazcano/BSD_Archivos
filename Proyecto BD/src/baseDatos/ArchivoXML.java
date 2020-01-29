package baseDatos;
/***
 Creado por Sergio Arias,JuanManuelLazcano,AlexisChavez 08/12/19
* Clase de extension de sql donde utiliza archivos en formato XML */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import baseDatos.SQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;

public class ArchivoXML extends SQL {
	private Document document;
	
	public ArchivoXML() {
		super();
	}

	/**Crear una instacioa de la clase, leer archivo mediante el nombre indicado
	 * @param root
	 */
	public ArchivoXML(String root) {
		int cols=0;
		int filas=0;
		root += ".xml";
		SQLTable t = new SQLTable();
		String[] arr = null;
		ArrayList<String> s=new ArrayList<>();
		List<Type> types = null;
		String cad = "";
		int option=0;
		Scanner entrada = null;
		try {

			File myFile = new File(root);
			if (!(myFile.exists())) {
				System.out.println("El archivo no existe");
			}
			DocumentBuilderFactory doc = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = doc.newDocumentBuilder();
			Document document = docBuild.parse(root);
			document.getDocumentElement().normalize();

			NodeList nodeList=document.getElementsByTagName("*");

				for (int i=0; i<nodeList.getLength(); i++)
				{
					// Get element
					Element element = (Element)nodeList.item(i);
					if(!element.getNodeName().equals("row")&&!element.getNodeName().equals("root")){
						if(!s.contains(element.getNodeName())) {
							s.add(element.getNodeName());

						}
					}

				}

				for(String current:s){
					t.addCols(current,Type.STRING);
				}

				cols=s.size();
				int ncols=cols;
				int gg=0;
			int j_j=0;
			String[][] fx=null;
			NodeList nList = document.getElementsByTagName("row");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				j_j=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;

					if(eElement.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for(int j=0; j<nl.getLength(); j++) {
							if(gg==0) {
								fx=new String[ncols][nl.getLength()];
								gg++;
							}
							Node nd = nl.item(j);
							if(j%2!=0) {

								fx[i][j_j]=nd.getTextContent();
								j_j++;
							}
						}
					}
				}
			}
			ArrayList<Object> fil=new ArrayList<>();
			String[] arr2 = new String[ncols];
			for(int i=0; i<ncols; i++) {
				for(int j=0; j<j_j;j++) {
					arr2[j]=fx[i][j];
				}
				t.addListValue(Arrays.asList(arr2));
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		super.setSQLTable(t);

	}

	public ArchivoXML clone() {
		ArchivoXML a = new ArchivoXML();
		a.setSQLTable(super.getSQLTable().clone());
		return a;
	}
	
	public ArchivoXML searchTable(String name, Object value) throws BDIndexOutOfBoundsException {
		ArchivoXML a = new ArchivoXML();
		a.setSQLTable(super.searchTableN(name, value));// Simular la sobre escritura
		return a;
	}
	
	public ArchivoXML selection(String name, char operator, Object value) throws BDIndexOutOfBoundsException {
		ArchivoXML a = new ArchivoXML();
		a.setSQLTable(super.selectionN(name, operator, value));// Simular la sobre escritura
		return a;
	}
	
	public ArchivoXML selection(String name, String n2) throws BDIndexOutOfBoundsException {
		ArchivoXML a = new ArchivoXML();
		a.setSQLTable(super.selectionN(name, n2));// Simular la sobre escritura
		return a;
	}
	
	public ArchivoXML proyection(String... name) throws BDIndexOutOfBoundsException {
		ArchivoXML a = new ArchivoXML();
		a.setSQLTable(super.proyectionN(name));// Simular la sobre escritura
		return a;
	}

	public ArchivoXML union(ArchivoXML a) {
		ArchivoXML a2 = clone();
		SQLTable t = a2.getSQLTable().union(a.getSQLTable());
		a2.setSQLTable(t);
		return a2;
	}
	
	public ArchivoXML union(ArchivoXML... a) {
		ArchivoXML a2 = clone();
		List<SQLTable> s = new ArrayList<SQLTable>();
		SQLTable sf = a2.getSQLTable();
		for (ArchivoXML r : a)
			s.add(r.getSQLTable());
		sf = sf.union(s.toArray(new SQLTable[1]));
		a2.setSQLTable(sf);
		return a2;
	}
	
	public ArchivoXML xProduct(ArchivoXML a) {
		ArchivoXML a2 = clone();
		SQLTable t = a2.getSQLTable().xProduct(a.getSQLTable());
		a2.setSQLTable(t);
		return a2;
	}
	
	public ArchivoXML xProduct(ArchivoXML... a) {
		ArchivoXML a2 = clone();
		List<SQLTable> s = new ArrayList<SQLTable>();
		SQLTable sf = a2.getSQLTable();
		for (ArchivoXML r : a)
			s.add(r.getSQLTable());
		sf = sf.xProduct(s.toArray(new SQLTable[1]));
		a2.setSQLTable(sf);
		return a2;
	}
	
	public void guardar(String nameAr) {

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
	public void ConvertToCSV(String nameAr) {
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

}
