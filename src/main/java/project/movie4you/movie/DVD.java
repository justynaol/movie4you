package project.movie4you.movie;


import javax.persistence.*;

@Entity
public class DVD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Movie movie;
    @Enumerated(EnumType.STRING)
    private Status status;

    public DVD(Movie movie, Status status) {
        this.movie = movie;
        this.status = status;
    }

    public DVD() {

    }

    public void update(Status status) {
        this.status = status;
    }

}
