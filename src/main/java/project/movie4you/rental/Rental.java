package project.movie4you.rental;

import project.movie4you.movie.DVD;
import project.movie4you.user.User;

import javax.persistence.*;


@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private DVD dvd;

    public Rental(User user, DVD dvd) {
        this.user = user;
        this.dvd = dvd;
    }

    public Rental() {

    }
}
