package baseDatos;
/***Creado por Juan Manuel Lazcano,Sergio Arias,Alexis Chavez
 * Clase de Archivo formato Json implementando sus propios metodods de lectura y escritura**/
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.HashMap;


public class ArchivoJSON extends SQL {

    public ArchivoJSON() {
        super();
    }

    public ArchivoJSON(String nameAr) throws FileNotFoundException, IOException {

        nameAr += ".json";
        SQLTable t = new SQLTable();
		String[] arr = null;
		SortedSet<String> s=new TreeSet<>();
		List<Type> types = null;
		String cad = "";
		int option=0;
		Scanner entrada = null;
        try {
            File myFile = new File(nameAr);
            if (!(myFile.exists()))
                System.out.println("El archivo no existe");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(nameAr));
	        JSONArray jsonArray = (JSONArray) obj;
	        int length = jsonArray.size();

	        LinkedList author = new LinkedList();
	        
	        for (int i =0; i< length; i++) {
	        	JSONObject jsonObject = (JSONObject) jsonArray.get(i);
	            Set ss = jsonObject.entrySet();
	            Iterator iter = ss.iterator();
	            
	            HashMap hm = new HashMap();

	            while(iter.hasNext()){
	                Map.Entry me = (Map.Entry) iter.next();
	                hm.put(me.getKey(), me.getValue());
	            }
	            author.add(hm);             
	       }
	        int numbloques=author.size();
	        int columns=0, tmp=0, tm=0, s_indice=0;
	        ArrayList<Object> fil=new ArrayList<>();
			String[] arr2 =null;
	        String[] etiquetas=null;
	        String[][] datos=null;
	        for(int i=0;i<author.size();i++){
	 	       HashMap hm = (HashMap) author.get(i);
	 	       Set ss = hm.entrySet(); //< cantidad de etiquetas
	 	       Iterator iter = ss.iterator();
	 	       tmp=0;
	 	       if(tm==0) {
	 	    	   columns=ss.size();
	 	    	   tm++;
	 	    	   datos= new String[numbloques][columns];
	 	    	   etiquetas=new String[columns];
	 	    	   arr2= new String[columns];
	 	       }
	 	      while(iter.hasNext()){
		           Map.Entry me = (Map.Entry) iter.next();
		           if(tmp<ss.size()) {
		        	   etiquetas[tmp]=(String) me.getKey();
		        	   datos[i][tmp]=(String) me.getValue();
		        	   arr2[tmp]=datos[i][tmp];
		        	   if(s_indice<columns) {
		        		   s.add(etiquetas[tmp]);
		        		   t.addCols(etiquetas[tmp],Type.STRING);
		        		   s_indice++;
		        	   }
		        	   tmp++; 
		           }

		         }
	 	      t.addListValue(Arrays.asList(arr2));
	        }


            
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
