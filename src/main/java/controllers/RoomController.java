package controllers;

import model.Project;
import model.Room;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.RoomService;
import service.UserService;
import validator.RoomValidator;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {
    private RoomService roomService;
    private UserService userService;

    @Autowired
    private RoomValidator roomValidator;

    @Autowired
    @Qualifier(value = "userService")
    private void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    @Qualifier(value = "roomService")
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }


    @RequestMapping(value = "OfficeProject/rooms/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String listRooms(Model model) {
        List<Room> room = roomService.getAll();
        model.addAttribute("rooms", room);
        return "listRoom";
    }

    @RequestMapping(value = "OfficeProject/rooms/add", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddPage(Model model) {
        roomService.createAddPageModel(model);
        return "newRoom";
    }


    @RequestMapping(value = "OfficeProject/rooms/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addNew(
            @ModelAttribute("room") Room room,
            BindingResult bindingResult,
            @RequestParam(value = "userId", required = false) ArrayList<Integer> selectedUsers,
            Model model) {
        roomValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            List<User> users = userService.getAll();
            model.addAttribute("room", new Room());
            model.addAttribute("users", users);
            return "newRoom";
        }
        if (selectedUsers != null) {
            roomService.setupNewRoomParams(room, selectedUsers);
        } else {
            roomService.add(room);
        }
        return "redirect: /OfficeProject/OfficeProject/rooms/all";

    }

//    @RequestMapping(value = "OfficeProject/rooms", params = {"userId"}, method = RequestMethod.GET)
//    private String getRoomByUser(Model model, @RequestParam("userId") int id) {
//        roomService.createByUserModel(id, model);
//        return "userProject";
//
//    }

//    @RequestMapping(value = "OfficeProject/rooms/edit", method = RequestMethod.POST)
//    private String updateExisting(@ModelAttribute("mproject") Project mproject) {
//        roomService.add(mproject);
//        return "projects";
//    }


    @RequestMapping(value = "OfficeProject/rooms/edit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditPage(Model model, @RequestParam("roomId") int id) {
        roomService.createEditPageModel(id, model);
        return "editRoom";
    }

    @RequestMapping(value = "OfficeProject/rooms/removeFrom", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUsersFromRoom(@RequestParam("roomId") int id,
                                          Model model,
                                          @RequestParam(value = "auserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            roomService.deleteUsersOperation(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/rooms/edit?roomId=" + id;
    }


    @RequestMapping(value = "OfficeProject/rooms/addInto", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUsersIntoRoom(@RequestParam("roomId") int id,
                                       Model model,
                                       @RequestParam(value = "nuserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            roomService.addToExisting(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/rooms/edit?roomId=" + id;
    }


    @RequestMapping(value = "OfficeProject/rooms/changeName", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeRoomName(@RequestParam("roomId") int id,
                                     @ModelAttribute("room") Room room,
                                     BindingResult bindingResult,
                                     Model model
    ) {
        roomValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            roomService.createEditPageModel(id, model);
            return "redirect: /OfficeProject/rooms/edit?roomId=" + id;
        }
        roomService.changeName(id, room.getName());
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/rooms/edit?roomId=" + id;

    }


    @RequestMapping(value = "OfficeProject/usersByRoom/{id}", method = RequestMethod.GET)
    private String getUsers(Model model, @PathVariable("id") int id) {
        roomService.createByProjectModel(id, model);
        return "userProject";
    }


    @RequestMapping(value = "OfficeProject/rooms/remove", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteRoom(@RequestParam("roomId") int Id) {
        roomService.remove(Id);
        return "redirect:/";
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public UserService getUserService() {
        return userService;
    }
}
