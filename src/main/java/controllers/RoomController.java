package controllers;//package controllers;

import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.RoomService;

import java.util.List;

@Controller
public class RoomController {
    private RoomService roomService;


    @Autowired
    @Qualifier(value = "roomService")
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    private String listUsers(Model model) {
        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "listRoom";
    }

    @RequestMapping(value = "rooms/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("room") Room room, Model model) {
        roomService.add(room);
        return "redirect:/";

    }

    @RequestMapping(value = "rooms/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("room") Room room) {
        roomService.add(room);
        return "redirect:/";
    }

    @RequestMapping(value = "rooms/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "room";
    }

    @RequestMapping(value = "rooms/edit/{id}", method = RequestMethod.GET)
    private String getEditPage(Model model, @PathVariable("id") int roomId) {
        Room room = (Room) roomService.get(roomId);
        model.addAttribute("room", room);
        return "update";
    }

    @RequestMapping(value = "rooms/remove/{id}", method = RequestMethod.GET)
    private String delete(@PathVariable("id") int Id){
        roomService.remove(Id);
        return "redirect:/";
    }
}
