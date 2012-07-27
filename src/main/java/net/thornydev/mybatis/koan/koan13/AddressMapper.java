package net.thornydev.mybatis.koan.koan13;

import net.thornydev.mybatis.koan.domain.Address;

public interface AddressMapper {
  Address getAddressById(int id);
}
