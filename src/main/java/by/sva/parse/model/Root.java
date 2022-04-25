package by.sva.parse.model;

import java.util.List;

import com.google.gson.annotations.SerializedName; // для GSON аннотации

public class Root {
	
	private String name; // по названию первого тэга файла, содержащего строку
	
	@SerializedName("people") // имя в JSON-файле, соответствующее имени переменной после аннотации
	/* Эта аннотация нужна для GSON если имя переменной объекта не совпадает с соответствующим в JSON-файле
	 * GSON сопоставит переменную после аннотации с именем в JSON-файле, указанным в аннотации
	 */
	private List<People> people; // по названию второго тэга, содержащего несколько вложенных элементов

	public void setName(String name) {
		this.name = name;
	}

	public void setPeople(List<People> people) {
		this.people = people;
	}

	public String getName() {
		return name;
	}

	public List<People> getPeople() {
		return people;
	}

	@Override
	public String toString() {
		return "Root [name=" + name + ", people=" + people + "]";
	}

}