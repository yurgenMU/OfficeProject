package validator;

import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.RoomService;

public class RoomValidator implements Validator {

    @Autowired
    private RoomService roomService;

    @Override
    public void validate(Object o, Errors errors) {
        Room room = (Room) o;
        Room proxyRoom = (Room) roomService.getByName(room.getName());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if ((proxyRoom != null) &&
                (proxyRoom.getId() != room.getId())) {
            errors.rejectValue("name", "Duplicate.room");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Room.class.equals(aClass);
    }

}
