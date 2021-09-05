package project.movie4you.movie;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JoinTable(name = "AwardMovie")
    private List<Award> awards = new ArrayList<>();

    public Movie(String title, String director, String scriptwriter, float price, Status status, int yearOfProduction, String category, String description, List<Award> awards) {
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

    public Movie() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Award> getAwards() {
        return awards;
    }
}
