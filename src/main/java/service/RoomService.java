package service;

import DAO.RoomDAO;
import model.AbstractEntity;
import model.Room;
import model.User;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomService implements EntityService {
    private RoomDAO roomDAO;


    private UserService userService;

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public void setupNewRoomParams(Room room, ArrayList<Integer> selectedUsers) {
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y -> {
            y.setRoom(room);
            userService.edit(y);
        });
        room.setUsers(users);
    }

    public void deleteUsersOperation(int id, ArrayList<Integer> selectedUsers) {
        Room room = get(id);
        selectedUsers.stream().forEach(x -> room.removeUser(x));
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y ->
                userService.removeRoom(y.getId()
                ));
//        edit(room);
    }

    public void changeName(int id, String newName) {
        Room room = get(id);
        room.setName(newName);
        edit(room);
    }

    public void addToExisting(int id, List<Integer> selectedUsers) {
        Room room = get(id);
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y -> {
            y.setRoom(room);
            room.addUser(y);
            userService.edit(y);
        });
        edit(room);
    }

    public Model createEditPageModel(int id, Model model) {
        Room room = get(id);
        Set<User> ausers = room.getUsers();
        List<User> nusers = userService.getAll();
        nusers.removeAll(ausers);
        model.addAttribute("room", room);
        model.addAttribute("actualUsers", ausers);
        model.addAttribute("nUsers", nusers);
        model.addAttribute("auserId", new ArrayList<Integer>());
        model.addAttribute("nuserId", new ArrayList<Integer>());
        return model;
    }

    /**
     * Creates Model instance with attriibutes required for getting room
     * for actual user
     *
     * @param id    Actual user identifier
     * @param model Model instance that will be returned
     * @return
     */
    public Model createByUserModel(int id, Model model) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        model.addAttribute("room", user.getRoom());
        return model;
    }

    public Model createByProjectModel(int id, Model model) {
        Room room = get(id);
        Set<User> users = room.getUsers();
        model.addAttribute("room", room);
        model.addAttribute("users", users);
        return model;
    }

    public Model createAddPageModel(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("room", new Room());
        model.addAttribute("users", users);
        model.addAttribute("userId", new ArrayList<Integer>());
        return model;
    }


    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        roomDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Room get(int id) {
        return roomDAO.getEntity(id);
    }

    @Override
    public AbstractEntity getByName(String name) {
        return roomDAO.getEntityByName(name);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        roomDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public List<Room> getAll() {
        return roomDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        Room room = get(id);
        Set<User> users = room.getUsers();
        users.stream().forEach(x -> {
            userService.removeRoom(x.getId());
        });
        edit(room);
        roomDAO.removeEntity(id);
        ;
    }


}
