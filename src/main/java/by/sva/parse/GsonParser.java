package by.sva.parse;

import java.io.FileReader;

import com.google.gson.Gson;

import by.sva.parse.model.Root;

/* Читает JSON-файл. Если имена переменных и структура файла- источника совпадают
 * с переменными и структурой получаемого объекта, то создает объект
 * 
 * Если имя переменной не совпадает, в объекте нужно сделать аннотацию (см. Root)
 */

public class GsonParser {

	public Root parse() {
		
		Gson gson = new Gson();
		
		try(FileReader reader = new FileReader("src/source.json")) {
			
			// парсинг из источника reader в объект Root
			Root root = gson.fromJson(reader, Root.class);
			
			return root;
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		return null;
	}

}
