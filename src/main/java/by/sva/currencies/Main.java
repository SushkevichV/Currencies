package by.sva.currencies;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;

// получает курсы валют с сайта НБ РБ за последние 11 дней и сравнивает их

public class Main {

	public static void main(String[] args) {
		CurrencyNB currencyNB = new CurrencyNB();
		CurrencyAbsolut currencyAbsolut = new CurrencyAbsolut();
		
		Map<Date, Double> euro = null;
		Map<Date, Double> usd = null;
		
		
		try {
			euro = currencyNB.getCurrency(10, 451); // 451 - актуальный ID евро
			usd = currencyNB.getCurrency(10, 431);  // 431 - актуальный ID доллара США
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<Date, Double> item : euro.entrySet()) {
			System.out.format("%s - %.4f - %.4f - %.4f%n", 
					new SimpleDateFormat("dd.MM.yyyy").format(item.getKey()), 	// ключ множества euro
					item.getValue(),											// значение по ключу
					usd.get(item.getKey()),										// значение из множества usd по тому же ключу (поскольку ключем является дата, ключи совпадают)
					item.getValue()/usd.get(item.getKey()));					// соотношение курсов валют
			
		}
		
		
		System.out.println("\nКурсы обмена валют Абсолютбанк");
		List<Currency> currencies = currencyAbsolut.getCurrencies("USD", "EUR", "RUB"); // можно передать любое количество аргументов
		for(Currency cur : currencies) {
			if (cur != null) {
				System.out.format("%s %.4f/%.4f\n", cur.getName(), cur.getBuy(), cur.getSell());
			} else {
				System.out.println("Курс не установлен");
			}
		}

	}

}
