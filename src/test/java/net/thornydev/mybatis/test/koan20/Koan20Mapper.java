package net.thornydev.mybatis.test.koan20;

import java.util.List;

import net.thornydev.mybatis.test.domain.Film;

public interface Koan20Mapper {

	List<String> selectLongFilmsByTitleWithPound(Film f);

	List<String> selectLongFilmsByTitleWithDollar(Film f);

	List<String> selectLongFilmsByTitleAndRating(Film f);

}
