package com.project.mylittleshop.DTO.DTOMappers;

import com.project.mylittleshop.DTO.AddressDTO;
import com.project.mylittleshop.entity.Address;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AddressDTOMapper implements Function<Address, AddressDTO> {
    
    @Override
    public AddressDTO apply(Address address) {
        return new AddressDTO(
                address.getCountry(), address.getCity(), address.getStreet(), address.getPostCode()
        );
    }
}
