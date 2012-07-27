package net.thornydev.mybatis.test.koan17.pg;

import java.util.List;
import java.util.Map;

import net.thornydev.mybatis.test.util.FilmInStockId;
import net.thornydev.mybatis.test.util.FilmInStockParam;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface Koan17Mapper {
  /* ---[ film_in_stock stored proc mapper methods ]--- */

  @Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
  List<Map<String, Integer>> callFilmInStockWithHashMaps(
    Map<String, Integer> params);

  // using @Results doesn't work, but @ResultMap referencing an XML-based
  // resultMap works
  // @Results(value = {
  // @Result(column="film_in_stock", property="filmId",
  // javaType=FilmInStockId.class, jdbcType=JdbcType.INTEGER)
  // })
  @Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
  @ResultMap(value = "filmInStockIdMap")
  List<FilmInStockId> callFilmInStock(FilmInStockParam params);

  @Select("SELECT film_in_stock( #{filmId}, #{storeId} )")
  List<Integer> callFilmInStock2(FilmInStockParam params);

  /* ---[ inventory_in_stock stored function mapper methods ]--- */

  @Select("SELECT inventory_in_stock(#{id})")
  Boolean inventoryInStore(int storeId);
}
