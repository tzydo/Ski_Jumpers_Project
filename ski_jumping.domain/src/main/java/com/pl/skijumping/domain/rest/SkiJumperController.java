package com.pl.skijumping.domain.rest;

import com.pl.skijumping.domain.dto.SkiJumperDTO;
import com.pl.skijumping.domain.service.SkiJumperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class SkiJumperController {

    private final SkiJumperService skiJumperService;
    public SkiJumperController(SkiJumperService skiJumperService) {
        this.skiJumperService = skiJumperService;
    }

    @RequestMapping(value = "jumpers/name/{name}", method = RequestMethod.GET)
    private ResponseEntity<List<SkiJumperDTO>> getByName(@PathVariable("name") String name) {
        if (name == null || name.equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<List<SkiJumperDTO>> skiJumperDTOList = skiJumperService.findByName(name);
        return skiJumperDTOList.map(
                skiJumperDTOS -> new ResponseEntity<>(skiJumperDTOS, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "jumpers/rank/{rank}", method = RequestMethod.GET)
    private ResponseEntity<SkiJumperDTO> getByRank(@PathVariable("rank") int rank) {
        Optional<SkiJumperDTO> skiJumperDTO = skiJumperService.findByRank(rank);
        return skiJumperDTO.map(
                skiJumperDTO1 -> new ResponseEntity<>(skiJumperDTO1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "jumpers/add", method = RequestMethod.POST)
    private ResponseEntity addJumper(@RequestBody SkiJumperDTO skiJumperDTO) {
        if (skiJumperDTO == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            skiJumperService.save(skiJumperDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(value = "jumpers/rank/{rank}}", method = RequestMethod.DELETE)
    ResponseEntity deleteJumperByRank(@RequestParam("rank") int rank) {
        skiJumperService.deleteByRank(rank);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "jumpers-list", method = RequestMethod.GET)
    private ResponseEntity<List<SkiJumperDTO>> getAllJumpers() {
        Optional<List<SkiJumperDTO>> skiJumperDTOList = skiJumperService.findAll();
        return skiJumperDTOList
                .map(skiJumperDTOS -> new ResponseEntity<>(skiJumperDTOS, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "jumpers", method = RequestMethod.PUT)
    public ResponseEntity<SkiJumperDTO> update(@RequestParam("rank") int rank,
                                               @RequestBody Optional<SkiJumperDTO> currencySkiJumperDTO) {
        if(!currencySkiJumperDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<SkiJumperDTO> skiJumperDTO = skiJumperService.findByRank(rank);
        if(!skiJumperDTO.isPresent() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        skiJumperService.update(skiJumperDTO.get());
        return new ResponseEntity<>(skiJumperDTO.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "jumpers/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        if(skiJumperService.deleteAll()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(value = "count/jumpers", method = RequestMethod.GET)
    public ResponseEntity<Long> getCountJumpers() {
        return new ResponseEntity<>(skiJumperService.getJumpersCount(), HttpStatus.OK);
    }
}
