package project.movie4you.movie;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


import static java.util.stream.Collectors.toList;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String director;
    private String scriptwriter;
    private float price;
    private Status status;
    private int yearOfProduction;
    private String category;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Award_Movie")
    private List<Award> awards = new ArrayList<>();

    public Movie(long id, String title, String director, String scriptwriter, float price, Status status, int yearOfProduction, String category, String description) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.scriptwriter = scriptwriter;
        this.price = price;
        this.status = status;
        this.yearOfProduction = yearOfProduction;
        this.category = category;
        this.description = description;
    }

    public Movie() {

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getScriptwriter() {
        return scriptwriter;
    }

    public float getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<Award> getAwards() {
        return awards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Float.compare(movie.price, price) == 0 && yearOfProduction == movie.yearOfProduction && Objects.equals(title, movie.title) && Objects.equals(director, movie.director) && Objects.equals(scriptwriter, movie.scriptwriter) && status == movie.status && Objects.equals(category, movie.category) && Objects.equals(description, movie.description) && Objects.equals(awards, movie.awards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, scriptwriter, price, status, yearOfProduction, category, description, awards);
    }

    public void update(String title, String director, String scriptwriter, float price, Status status, int yearOfProduction, String category, String description, List<Award> awards) {
        this.title = title;
        this.director = director;
        this.scriptwriter = scriptwriter;
        this.price = price;
        this.status = status;
        this.yearOfProduction = yearOfProduction;
        this.category = category;
        this.description = description;
        this.awards = awards;
    }

    public void addAward(Award award) {
        this.awards.add(award);
    }

    public void deleteById(long awardid) {

        awards.removeIf(award -> awardid==award.getId());
    }
}
