package net.thornydev.mybatis.koan.koan17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.koan.util.FilmInStockId;
import net.thornydev.mybatis.koan.util.FilmInStockParam;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;

public interface Koan17Mapper {
	/* ---[ film_in_stock stored proc mapper methods ]--- */

	@Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
	List<Map<String,Integer>> callFilmInStockWithHashMaps(Map<String,Integer> params);

	// using @Results doesn't work, but @ResultMap referencing an XML-based resultMap works
//	@Results(value = {
//	   @Result(column="film_in_stock", property="filmId", javaType=FilmInStockId.class, jdbcType=JdbcType.INTEGER)
//	})
	@Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
	@ResultMap(value="filmInStockIdMap")
	List<FilmInStockId> callFilmInStock(FilmInStockParam params);


    @Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
	List<Integer> callFilmInStock2(FilmInStockParam params);

	/* ---[ inventory_in_stock stored function mapper methods ]--- */

	@Select("SELECT inventory_in_stock(#{id})")
	Boolean inventoryInStore(int storeId);
}
