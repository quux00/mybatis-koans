package net.thornydev.mybatis.koan.koan19;

import net.thornydev.mybatis.koan.domain.Staff;

import org.apache.ibatis.annotations.Param;

public interface Koan19Mapper {
	Staff getStaffById(int id);
	void setEmail(@Param("email") String email, @Param("id") int id);
}
