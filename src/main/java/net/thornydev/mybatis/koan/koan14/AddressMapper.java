package net.thornydev.mybatis.koan.koan14;

import net.thornydev.mybatis.koan.domain.Address;

public interface AddressMapper {
  Address getAddressById(int id);
}
