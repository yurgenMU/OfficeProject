package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
//@Table(name="projectsUsers")
public class Projectsusers {
    private int projectId;
    private int userId;

    @Column(name = "projectId", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

        Projectsusers that = (Projectsusers) o;

        if (projectId != that.projectId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + userId;
        return result;
    }


}
