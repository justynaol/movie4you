package project.movie4you.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String organizationName;
    private Date receivedDate;

    public Award(String name, String organizationName, Date receivedDate) {
        this.name = name;
        this.organizationName = organizationName;
        this.receivedDate = receivedDate;
    }

    public Award() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Award award = (Award) o;
        return id == award.id && Objects.equals(name, award.name) && Objects.equals(organizationName, award.organizationName) && Objects.equals(receivedDate, award.receivedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, organizationName, receivedDate);
    }
}
