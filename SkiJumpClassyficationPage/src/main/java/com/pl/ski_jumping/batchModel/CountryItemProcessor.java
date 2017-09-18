package com.pl.ski_jumping.batchModel;

import com.pl.ski_jumping.model.Country;
import org.springframework.batch.item.ItemProcessor;


public class CountryItemProcessor implements ItemProcessor<Country, Country> {
    @Override
    public Country process(Country country) throws Exception {
        return country;
    }
}
