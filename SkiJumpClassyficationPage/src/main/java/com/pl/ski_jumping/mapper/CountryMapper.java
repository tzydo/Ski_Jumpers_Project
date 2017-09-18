package com.pl.ski_jumping.mapper;

import com.pl.ski_jumping.model.Country;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CountryMapper implements FieldSetMapper<Country> {
    @Override
    public Country mapFieldSet(FieldSet fieldSet) throws BindException {

        return new Country().builder()
                .name(fieldSet.readString("name"))
                .build();

    }
}
