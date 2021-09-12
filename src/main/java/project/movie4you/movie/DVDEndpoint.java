package project.movie4you.movie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@RequestMapping("/dvd")
public class DVDEndpoint {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private DVDRepository dvdRepository;

    @PostMapping
    ResponseEntity<Void> create(@RequestBody DVDDefinition dvdDefinition) {
        Movie movie = movieRepository.getById(dvdDefinition.movieid);
        DVD dvd = new DVD(movie, dvdDefinition.status);
        dvdRepository.save(dvd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable long id, @RequestBody DVDDefinition dvdDefinition) {
        DVD dvd = dvdRepository.getById(id);
        dvd.update(dvdDefinition.status);
        //todo zapisać kto wypożyczył / usunąć wypożyczającego jeśli oddał
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable long id) {
        dvdRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    private static class DVDDefinition {
        long movieid;
        Status status;

    }
}
