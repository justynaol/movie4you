package project.movie4you.movie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/movie")
public class MovieEndpoint {

    private final MovieRepository movieRepository;

    public MovieEndpoint(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @GetMapping
    public ResponseEntity<List<MovieDefinition>> list() {
        List<MovieDefinition> movies = movieRepository.findAll().stream()
                .map(movie -> MovieDefinition.from(movie))
                .collect(toList());
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

   @PostMapping
    public ResponseEntity<Void> create(@RequestBody MovieDefinition movieDefinition) {
        List<Award> awards = movieDefinition.awards.stream()
                .map(id -> new Award(id))
                .collect(toList());
       Movie movie = new Movie(movieDefinition.id, movieDefinition.title, movieDefinition.director, movieDefinition.scriptwriter, movieDefinition.price, movieDefinition.status, movieDefinition.yearOfProduction, movieDefinition.category, movieDefinition.description, awards);
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody MovieDefinition movieDefinition) {
        Movie movie = movieRepository.getById(id);
        movie.update(movieDefinition.title, movieDefinition.director, movieDefinition.scriptwriter, movieDefinition.price, movieDefinition.status, movieDefinition.yearOfProduction, movieDefinition.category, movieDefinition.description, movieDefinition.awards);
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        movieRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class MovieDefinition {
        long id;
        String title;
        String director;
        String scriptwriter;
        float price;
        Status status;
        int yearOfProduction;
        String category;
        String description;
        List<Long> awards = new ArrayList<>();

        public static MovieDefinition from(Movie movie) {
            MovieDefinition movieDefinition = new MovieDefinition();
            movieDefinition.id = movie.getId();
            movieDefinition.title = movie.getTitle();
            movieDefinition.director = movie.getDirector();
            movieDefinition.scriptwriter = movie.getScriptwriter();
            movieDefinition.price = movie.getPrice();
            movieDefinition.status = movie.getStatus();
            movieDefinition.yearOfProduction = movie.getYearOfProduction();
            movieDefinition.category = movie.getCategory();
            movieDefinition.description = movie.getDescription();
            movieDefinition.awards = movie.getAwards().stream().map($ -> $.getId()).collect(toList());
            return movieDefinition;
        }
    }
}
