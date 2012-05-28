package net.thornydev.mybatis.koan.koan19b;

import net.thornydev.mybatis.koan.domain.Staff;

import org.apache.ibatis.annotations.Param;

public interface Koan19bMapper {
	Staff getStaffById(int id);
	void setEmail(@Param("email") String email, @Param("id") int id);
}
