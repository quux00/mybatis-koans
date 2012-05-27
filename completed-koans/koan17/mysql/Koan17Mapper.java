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

public interface Koan17Mapper {
	/* ---[ film_in_stock stored proc mapper methods ]--- */

	// doesn't work
	@Select("{ CALL film_in_stock( #{filmId, mode=IN, jdbcType=INTEGER}, #{storeId, mode=IN, jdbcType=INTEGER}, #{count, mode=OUT, jdbcType=INTEGER} )}")
	@Options(statementType = StatementType.CALLABLE)
	@ResultMap(value="hashResultMap")
	List<Map<String,Integer>> callFilmInStockWithHashMaps(Map<String,Integer> params);

	// using @Results doesn't work, but @ResultMap referencing an XML-based resultMap works
//	@Results(value = {
//	   @Result(column="inventory_id", property="filmId", javaType=FilmInStockId.class, jdbcType=JdbcType.INTEGER)
//	})
	@Select("{ CALL film_in_stock( #{filmId, mode=IN, jdbcType=INTEGER}, #{storeId, mode=IN, jdbcType=INTEGER}, #{count, mode=OUT, jdbcType=INTEGER} )}")
	@ResultMap(value="filmInStockIdMap")
	@Options(statementType = StatementType.CALLABLE)
	List<FilmInStockId> callFilmInStock(FilmInStockParam params);

	@Select("{ CALL film_in_stock( #{filmId, mode=IN, jdbcType=INTEGER}, #{storeId, mode=IN, jdbcType=INTEGER}, #{count, mode=OUT, jdbcType=INTEGER} )}")
	@Options(statementType = StatementType.CALLABLE)
	List<Integer> callFilmInStock2(FilmInStockParam params);

	/* ---[ inventory_in_stock stored function mapper methods ]--- */

	@Select("SELECT inventory_in_stock(#{id})")
	Boolean inventoryInStore(int storeId);
}
