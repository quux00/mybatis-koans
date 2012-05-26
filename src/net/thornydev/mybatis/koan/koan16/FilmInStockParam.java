package net.thornydev.mybatis.koan.koan16;

/**
 * 
 * 
 *
 */
public class FilmInStockParam {
	private int filmId;
	private int storeId;
	private int count;

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
