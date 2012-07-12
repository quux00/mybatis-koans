package net.thornydev.mybatis.test.koan24;

import java.util.Collection;
import java.util.List;

public interface Koan24Mapper {

	void createTable();

	void removeTable();

	//

	int insertRecord(InstInfo info);

	InstInfo selectRecord(String nativeId);

	int updateRecord(InstInfo info);

	//

	int getRecordCount();

	List<InstInfo> getRecordList();

	List<InstInfo> getRecordListBySourceId(Collection<String> list);

	//

	List<String> getListSourceId();

}
