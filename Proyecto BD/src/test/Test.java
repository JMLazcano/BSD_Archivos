package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.Date;
import java.util.*;

import baseDatos.*;

import com.fasterxml.jackson.core.*;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.sound.sampled.Line;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.simple.JSONArray;

public class Test {
	public static void main(String[] args) throws Exception {


		ArchivoJSON j=new ArchivoJSON("jsonp");
		System.out.println(j);
        j.ConvertToXML("ty");

		ArchivoXML fd=new ArchivoXML("x");
		System.out.println(fd);
		System.out.println(fd.getTypes());
		fd.ConvertToJSON("xJ");















		/*
			ArchivoXML fd=new ArchivoXML("C:\\Users\\JuanMa\\Desktop\\School Iteso\\Proyecto\\x");
			System.out.println("lolo");
			System.out.println(fd);
			System.out.println(fd.getTypes());
			fd.deleteCols("nombre");
			System.out.println(fd);

			System.out.println("***********************");
			ArchivoXML file=new ArchivoXML("x");
			System.out.println(file.getTypes());
			System.out.println(file);
			file.deleteRows(0);
			System.out.println(file);

				ArchivoXML filo=new ArchivoXML("x");
				System.out.println(filo);
				filo.guardar("z");

			ArchivoCSV l=new ArchivoCSV("test");
			System.out.println(l);


		ArchivoCSV a = new ArchivoCSV();
		a.addCols("nombre", Type.STRING);
		a.addCols("puntos", Type.DOUBLE);
		a.addCols("fecha", Type.DATE);
		a.addCols("entero", Type.INT);
		System.out.println(a.getTypes()+"\n");
		long ctm=System.currentTimeMillis();
		a.addRows();
		a.addRowValue(0,"Carlitos",6.5,Date.valueOf("2019-05-14"),5);
		System.out.println(a+"\n");
		a.newRowValue("Maqueen",7.0,new Date(ctm),3);
		System.out.println(a+"\n");
		a.newRowValue("Milos",150.0,Date.valueOf("0001-01-01"),9000);
		System.out.println(a+"\n");
		a.addRowValue(2,"Milos",150.0,Date.valueOf("0001-01-02"),10000);
		System.out.println(a+"\n");
		a.addValue(2, "entero", Date.valueOf("2019-12-12"),11000);
		System.out.println(a+"\n");
		a.newRowValue("Carlitos",7.5,Date.valueOf("2019-04-14"),6);
		System.out.println(a+"\n");


		//String userName = myObj.nextLine();//scanner

		System.out.println(a.maxValue("nombre"));

		//userName = myObj.nextLine();//scanner

		System.out.println(a.Count());

		//userName = myObj.nextLine();//scanner

		System.out.println(a.moda("nombre"));

		//userName = myObj.nextLine();//scanner

		System.out.println(a.average("entero"));

		//userName = myObj.nextLine();//scanner

		System.out.println(a.searchTable("nombre", "Carlitos")+"\n");

		//userName = myObj.nextLine();//scanner

		System.out.println(a.selection("puntos", '>', 10.0)+"\n");
		System.out.println(a.selection("puntos", '<', 10.0)+"\n");

		//userName = myObj.nextLine();//scanner

		System.out.println(a.sum("puntos"));

		//userName = myObj.nextLine();//scanner

		System.out.println(a.proyection("nombre","fecha")+"\n");

		//userName = myObj.nextLine();//scanner

		System.out.println(a.union(a)+"\n");

		System.out.println(a.union(a,a)+"\n");
		//userName = myObj.nextLine();//scanner


		System.out.println(a.print());

		//userName = myObj.nextLine();//scanner

		a.deleteRows(3);

		System.out.println(a);

		a.deleteCols("entero");

		System.out.println(a);

		a.guardar("Tabla");
		try {
			ArchivoCSV a2 = new ArchivoCSV("Tabla");
			System.out.println("\n"+a2+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//userName = myObj.nextLine();//scanner

		ArchivoCSV p = new ArchivoCSV();
		p.addCols("Otro", Type.STRING);
		p.newRowValue("Carlos");
		ArchivoCSV p2=a.xProduct(p);

		System.out.println(p.xProduct(a,a)+"\n");

		System.out.println(p2.selection("nombre", "Otro")+"\n");

		 */
		}
	}


