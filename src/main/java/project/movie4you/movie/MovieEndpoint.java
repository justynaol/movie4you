package project.movie4you.movie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.movie4you.movie.MovieEndpoint.MovieDefinition.AwardDefinition;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
       Movie movie = new Movie(movieDefinition.title, movieDefinition.director, movieDefinition.scriptwriter,
               movieDefinition.price, movieDefinition.status, movieDefinition.yearOfProduction, movieDefinition.category,
               movieDefinition.description);
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody MovieDefinition movieDefinition) {
        Movie movie = movieRepository.getById(id);

        List<Award> awards = new ArrayList<>();
        for (AwardDefinition award : movieDefinition.awards) {
            awards.add(new Award(award.name, award.organizationName, award.receivedDate));
        }
//        List<Award> awards = movieDefinition.awards
//                .stream()
//                .map(award -> new Award(award.name, award.organizationName, award.receivedDate))
//                .collect(toList());

        movie.update(movieDefinition.title, movieDefinition.director, movieDefinition.scriptwriter, movieDefinition.price,
                movieDefinition.status, movieDefinition.yearOfProduction, movieDefinition.category,
                movieDefinition.description, awards);
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/awards")
    @Transactional
    public ResponseEntity<Void> addAward(@PathVariable long id, @RequestBody AwardDefinition awardDefinition) {
        Movie movie = movieRepository.getById(id);
        movie.addAward(new Award(awardDefinition.name, awardDefinition.organizationName, awardDefinition.receivedDate));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/awards/{awardid}")
    @Transactional
    public ResponseEntity<Void> deleteAward(@PathVariable long awardid, @PathVariable long id) {
        Movie movie = movieRepository.getById(id);
        movie.deleteById(awardid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        movieRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class MovieDefinition {
        String title;
        String director;
        String scriptwriter;
        float price;
        Status status;
        int yearOfProduction;
        String category;
        String description;
        List<AwardDefinition> awards = new ArrayList<>();

        public static MovieDefinition from(Movie movie) {
            MovieDefinition movieDefinition = new MovieDefinition();
            movieDefinition.title = movie.getTitle();
            movieDefinition.director = movie.getDirector();
            movieDefinition.scriptwriter = movie.getScriptwriter();
            movieDefinition.price = movie.getPrice();
            movieDefinition.status = movie.getStatus();
            movieDefinition.yearOfProduction = movie.getYearOfProduction();
            movieDefinition.category = movie.getCategory();
            movieDefinition.description = movie.getDescription();
            movieDefinition.awards = movie.getAwards().stream().map($ -> new AwardDefinition($)).collect(toList());
            return movieDefinition;
        }

        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
        public static class AwardDefinition {
            String name;
            String organizationName;
            Date receivedDate;

            public AwardDefinition(Award award) {
                this.name = award.getName();
                this.organizationName = award.getOrganizationName();
                this.receivedDate = award.getReceivedDate();
            }
        }
    }
}
