package com.pl.ski_jumping.controllers;

import com.pl.ski_jumping.model.SkiJumper;
import com.pl.ski_jumping.service.SkiJumperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class HomeController {

    @Autowired
    SkiJumperService skiJumperService;

    private List<SkiJumper> skiJumpers;
    private SkiJumper skiJumper;


    @RequestMapping(value = "jumpers", method = RequestMethod.GET)
    public ResponseEntity<List<SkiJumper>> findByName(@RequestParam("name") String name) {
        skiJumpers = skiJumperService.findByName(name);

        return new ResponseEntity<List<SkiJumper>>(skiJumpers,HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/{rank}", method = RequestMethod.GET)
    public ResponseEntity<SkiJumper> findByRank(@PathVariable("rank") int rank) {
        skiJumper = skiJumperService.findByRank(rank);

        return new ResponseEntity<SkiJumper>(skiJumper,HttpStatus.OK);
    }


    @RequestMapping(path = "jumpers", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody SkiJumper skiJumper){
        skiJumperService.save(skiJumper);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(path = "jumpers", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestParam("rank")int rank, @RequestBody SkiJumper currencySkiJumper){
        skiJumper = skiJumperService.findByRank(rank);

        if(skiJumper.equals(null))return new ResponseEntity(HttpStatus.NOT_FOUND);

        skiJumper = currencySkiJumper;
        skiJumperService.update(skiJumper);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "jumpers/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll(){
        skiJumperService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "jumpers/{rank}", method = RequestMethod.DELETE)
    public ResponseEntity deleteByRank(@PathVariable("rank")int rank){
        skiJumperService.deleteByRank(rank);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "jumpers/all", method = RequestMethod.GET)
    public ResponseEntity<List<SkiJumper>> findall() {
        skiJumpers = skiJumperService.findAll();
        return new ResponseEntity<List<SkiJumper>>(skiJumpers, HttpStatus.OK);
    }

}

