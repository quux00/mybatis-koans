package net.thornydev.mybatis.koan.koan17;

import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.util.FilmInStockId;
import net.thornydev.mybatis.koan.util.FilmInStockParam;

public interface Koan17Mapper {
	/* ---[ film_in_stock stored proc mapper methods ]--- */

	// TODO: add annotations to make this work
	List<Map<String,Integer>> callFilmInStockWithHashMaps(Map<String,Integer> params);

	// TODO: add annotations to make this work
	List<FilmInStockId> callFilmInStock(FilmInStockParam params);

	// TODO: add annotations to make this work
	List<Integer> callFilmInStock2(FilmInStockParam params);

	/* ---[ inventory_in_stock stored function mapper methods ]--- */

	// TODO: add annotations to make this work
	Boolean inventoryInStore(int storeId);
}
