package com.si.udriveservice.service.mock;

import com.si.udriveservice.model.dto.BasicIdDTO;
import com.si.udriveservice.model.dto.DriverDTO;
import com.si.udriveservice.model.entity.Driver;
import com.si.udriveservice.model.enums.StatusEnum;
import com.si.udriveservice.model.mapper.DriverMapper;
import com.si.udriveservice.repository.DriverRepository;
import com.si.udriveservice.service.MockUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverMock {
//    @Autowired
//    private DriverRepository repository;
//
//    @Autowired
//    private DriverMapper mapper;
//
//    @Autowired
//    private UniversityMock universityMock;
//
//    public Driver mockEntity() {
//        Driver entity = mapper.toEntity(mockDto());
//        entity.setStatus(StatusEnum.ACTIVE);
//        return repository.save(entity);
//    }
//
//    public Driver mockEntity(StatusEnum status) {
//        Driver entity = mapper.toEntity(mockDto());
//        entity.setStatus(status);
//        return repository.save(entity);
//    }
//
//    public DriverDTO mockDto() {
//        return DriverDTO.builder()
//                .email(String.format("%s@email.com", RandomStringUtils.randomAlphabetic(8)))
//                .cpf(MockUtils.VALID_CPFS.get(RandomUtils.nextInt(0, 6)))
//                .university(new BasicIdDTO(universityMock.mockEntity().getId()))
//                .cnh(MockUtils.VALID_DRIVERS_LICENCE.get(RandomUtils.nextInt(0, 6)))
//                .phoneNumber(RandomStringUtils.randomNumeric(11))
//                .fullName("Driver")
//                .build();
//    }
}
