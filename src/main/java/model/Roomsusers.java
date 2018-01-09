package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class Roomsusers {
    private int roomId;
    private int userId;

    @Column(name = "roomId", nullable = false)
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

        Roomsusers that = (Roomsusers) o;

        if (roomId != that.roomId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + userId;
        return result;
    }
}
