package com.pl.skijumping.rest;

import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.entity.Country;
import com.pl.skijumping.service.CountryService;
import com.pl.skijumping.service.SkiJumperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

//    @RequestMapping(value = "jumpers/getNewFisCode", method = RequestMethod.GET)
//    public int getJumperFisCode() {
//        return skiJumperService.getFisCode();
//    }

    @RequestMapping(value = "countries", method = RequestMethod.GET)
    public ResponseEntity<List<CountryDTO>> getCountriesList() {
        Optional<List<CountryDTO>> countryDTOList = countryService.getCountries();
        return countryDTOList.map(countryDTOS -> new ResponseEntity<>(countryDTOS, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(countryDTOList.get(), HttpStatus.NOT_FOUND));
    }


//    @RequestMapping(value = "jumpers/countries-by-pattern", method = RequestMethod.GET)
//    public ResponseEntity<List<Country>> getCountryListByPattern(@RequestParam("pattern") String pattern) {
//        return new ResponseEntity<List<Country>>(skiJumperService.getCountriesByPattern(pattern), HttpStatus.OK);
//    }


//    @RequestMapping(value = "jumpers/get-jumpers-by-patterns", method = RequestMethod.GET)
//    public ResponseEntity<List<SkiJumper>> getJumperByPatterns(
////    public String getJumperByPatterns(
//                                        @RequestParam(value = "rank", required = false) Integer rank,
//                                        @RequestParam(value = "bib", required = false) Integer bib,
//                                        @RequestParam(value = "fis_code", required = false) Integer fis_code,
//                                        @RequestParam(value = "name", required = false) String name,
//                                        @RequestParam(value = "surname", required = false) String surname,
//                                        @RequestParam(value = "nationality", required = false) String nationality) {
//
//        String querry = QuerryPattern.getQuerry(rank, bib, fis_code, name, surname, nationality);
//
////        return querry;
//
//        return new ResponseEntity<List<SkiJumper>>(skiJumperService.getJumpersByPattenr(querry), HttpStatus.OK);
//    }
}

