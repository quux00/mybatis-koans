package net.thornydev.mybatis.test.koan14;

import net.thornydev.mybatis.test.domain.Address;

public interface AddressMapper {

  Address getAddressById(int id);

}
