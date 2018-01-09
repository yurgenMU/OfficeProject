package model;

import javax.persistence.*;
import java.sql.Date;

@Embeddable
public class Usersdates {
    private int userId;

    @Basic
    @Temporal(TemporalType.TIME)
    private Date date;


    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Column(name = "userId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usersdates that = (Usersdates) o;

        if (userId != that.userId) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }


}
