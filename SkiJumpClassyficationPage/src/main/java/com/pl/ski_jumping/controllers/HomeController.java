package com.pl.ski_jumping.controllers;

import com.pl.ski_jumping.model.Country;
import com.pl.ski_jumping.utils.QuerryPattern;
import com.pl.ski_jumping.model.SkiJumper;
import com.pl.ski_jumping.service.SkiJumperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class HomeController {

    @Autowired
    SkiJumperService skiJumperService;

    private List<SkiJumper> skiJumpers;
    private SkiJumper skiJumper;


    @RequestMapping(value = "jumpers", method = RequestMethod.GET)
    public ResponseEntity<List<SkiJumper>> findByName(@RequestParam("name") String name) {
        skiJumpers = skiJumperService.findByName(name);

        return new ResponseEntity<List<SkiJumper>>(skiJumpers, HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/{rank}", method = RequestMethod.GET)
    public ResponseEntity<SkiJumper> findByRank(@PathVariable("rank") int rank) {
        skiJumper = skiJumperService.findByRank(rank);

        return new ResponseEntity<SkiJumper>(skiJumper, HttpStatus.OK);
    }


    @RequestMapping(path = "jumpers", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody SkiJumper skiJumper) {
        skiJumperService.save(skiJumper);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(path = "jumpers", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestParam("rank") int rank, @RequestBody SkiJumper currencySkiJumper) {
        skiJumper = skiJumperService.findByRank(rank);

        if (skiJumper.equals(null)) return new ResponseEntity(HttpStatus.NOT_FOUND);

        skiJumper = currencySkiJumper;
        skiJumperService.update(skiJumper);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        skiJumperService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "jumpers/{rank}", method = RequestMethod.DELETE)
    public ResponseEntity deleteByRank(@PathVariable("rank") int rank) {
        skiJumperService.deleteByRank(rank);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "jumpers/all", method = RequestMethod.GET)
    public ResponseEntity<List<SkiJumper>> findall() {
        skiJumpers = skiJumperService.findAll();
        return new ResponseEntity<List<SkiJumper>>(skiJumpers, HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/getCountJumpers", method = RequestMethod.GET)
    public int getCountJumpers() {
        return skiJumperService.getJumpersCount();
    }


    @RequestMapping(value = "jumpers/getNewFisCode", method = RequestMethod.GET)
    public int getJumperFisCode() {
        return skiJumperService.getFisCode();
    }


    @RequestMapping(value = "jumpers/countries", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountriesList() {
        return new ResponseEntity<List<Country>>(skiJumperService.getCountries(), HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/countries-by-pattern", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountryListByPattern(@RequestParam("pattern") String pattern) {
        return new ResponseEntity<List<Country>>(skiJumperService.getCountriesByPattern(pattern), HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/get-jumpers-by-patterns", method = RequestMethod.GET)
    public ResponseEntity<List<SkiJumper>> getJumperByPatterns(@RequestParam(value = "rank", required = false) Integer rank,
                                    @RequestParam(value = "bib", required = false) Integer bib,
                                    @RequestParam(value = "fis_code", required = false) Integer fis_code,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "surname", required = false) String surname,
                                    @RequestParam(value = "nationality", required = false) String nationality) {

        String querry = QuerryPattern.getQuerry(rank, bib, fis_code, name, surname, nationality);
       return new ResponseEntity<List<SkiJumper>>(skiJumperService.getJumpersByPattenr(querry), HttpStatus.OK);
    }
}

