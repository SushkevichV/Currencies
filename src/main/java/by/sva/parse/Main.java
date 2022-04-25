package by.sva.parse;

import by.sva.parse.model.Root;

public class Main {

	public static void main(String[] args) {
		JsonSimpleParser jsonParser = new JsonSimpleParser(); // парсинг с помощью JSON
		
		Root root = jsonParser.parse();
		
		System.out.println("JSON parsing: " + root.toString());
		
		GsonParser gsonParser = new GsonParser();  // парсинг с помощью GSON
		
		root = gsonParser.parse();
		
		System.out.println("GSON parsing: " + root.toString());
	}

}
