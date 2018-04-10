package com.pl.skijumping.controllers;

import com.pl.skijumping.service.SkiJumperService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1/")
public class HomeController {

    private final SkiJumperService skiJumperService;

    public HomeController(SkiJumperService skiJumperService) {
        this.skiJumperService = skiJumperService;
    }



//    @RequestMapping(path = "jumpers", method = RequestMethod.PUT)
//    public ResponseEntity update(@RequestParam("rank") int rank, @RequestBody SkiJumper currencySkiJumper) {
////        skiJumper = skiJumperService.findByRank(rank);
//
//        if (skiJumper.equals(null)) return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        skiJumper = currencySkiJumper;
////        skiJumperService.update(skiJumper);
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @Transactional
//    @RequestMapping(value = "jumpers/delete", method = RequestMethod.DELETE)
//    public ResponseEntity deleteAll() {
//        skiJumperService.deleteAll();
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }



//    @RequestMapping(value = "jumpers/getCountJumpers", method = RequestMethod.GET)
//    public int getCountJumpers() {
//        return skiJumperService.getJumpersCount();
//    }


//    @RequestMapping(value = "jumpers/getNewFisCode", method = RequestMethod.GET)
//    public int getJumperFisCode() {
//        return skiJumperService.getFisCode();
//    }


//    @RequestMapping(value = "jumpers/countries", method = RequestMethod.GET)
//    public ResponseEntity<List<Country>> getCountriesList() {
//        return new ResponseEntity<List<Country>>(skiJumperService.getCountries(), HttpStatus.OK);
//    }


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

