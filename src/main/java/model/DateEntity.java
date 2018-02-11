package model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dates")
public class DateEntity extends AbstractEntity {


    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private Set<User> users = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "dates")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateEntity that = (DateEntity) o;

        if (id != that.id) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    private String getMonth(int index) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July"
                , "August", "September", "October", "November", "December"};
        return months[index - 1];
    }
}
