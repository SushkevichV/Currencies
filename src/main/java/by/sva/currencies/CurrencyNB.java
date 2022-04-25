package by.sva.currencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// зависимость <!-- https://mvnrepository.com/artifact/org.json/json --> говорят, что старая, но рабочая

public class CurrencyNB {

	public Map<Date, Double> getCurrency(int days, int curID) throws IOException, JSONException {
		
		Map<Date, Double> currency = new TreeMap<>(); // сортированный список
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String endDate = new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		String startDate = new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime());
		
		//JSONObject json = readJsonFromUrl("https://www.nbrb.by/api/exrates/currencies/"+curID); // получает описание валюты
	    //System.out.println(json.toString()); // выводит описание валюты
	    
	    JSONArray json2 = readJsonArrayFromUrl("https://www.nbrb.by/api/exrates/rates/dynamics/"+curID+"?startdate="+startDate+"&enddate="+endDate); // получает массив курсов
	    //System.out.println(json2.toString()); // выводит массив курсов
	    
	    for (Object item: json2) {
	    	JSONObject element = (JSONObject) item; // получить объект из элемента массива
	    	String sDate = (String) element.get("Date"); // получить значение по ключу Date
	    	Date date = new Date();
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate); // парсинг строки в дату
			} catch (ParseException e) {
				e.printStackTrace();
			}

	    	double value = Double.valueOf((String) element.get("Cur_OfficialRate").toString()); // получить значение по ключу Cur_OfficialRate, переводит в String, а затем в Double
	    			// сразу в Double не преобразует, считает, что это BigDecimal
	    	
	    	currency.put(date, value);
	    }
	    
	    return currency;
	}

	// создает String из потока данных
	private String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	// создает JSONObject из String (текст должен начинаться с "{" )
	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	}
	
	// создает JSONArray из String (текст должен начинаться с "[" )
	private JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONArray json = new JSONArray(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	}
}
