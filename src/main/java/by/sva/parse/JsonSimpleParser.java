package by.sva.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import by.sva.parse.model.People;
import by.sva.parse.model.Root;

public class JsonSimpleParser {
	
	public Root parse() {
		
		Root root = new Root();
		
		JSONParser parser = new JSONParser();
		
		
		// открыть файл для парсинга
		// обратить внимание - новый синтаксис try-catch и измененный Exception
		try(FileReader reader = new FileReader("src/source.json")) {
			
			JSONObject rootJsonObject = (JSONObject) parser.parse(reader); // получить объект из файла
			
			String name = (String) rootJsonObject.get("name"); // получить значение по ключу name
			
			List<People> peopleList = new ArrayList<>();
			
			JSONArray peopleJsonArray = (JSONArray) rootJsonObject.get("people"); // получить массив по ключу people
			
			for (Object item: peopleJsonArray) {
				JSONObject peopleJsonObject = (JSONObject) item; // получить объект из элемента массива
				
				String peopleName = (String) peopleJsonObject.get("name"); // получить значение по ключу name
				long peopleAge = (Long) peopleJsonObject.get("age"); // получить значение по ключу age
												// значения приходят в формате long или double
				
				People people = new People(peopleName, (int)peopleAge); // создать объект people (привести peopleAge к формату int)
				peopleList.add(people);								// добавить объект в коллекцию
			}
			
			root.setName(name);
			root.setPeople(peopleList);
			
			return root;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		
		
		return root;
	}

}
