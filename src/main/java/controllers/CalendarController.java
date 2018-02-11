package controllers;

import calendarService.Month;
import model.DateEntity;
import model.Project;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.DateService;
import service.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Controller
public class CalendarController {
    private UserService userService;
    private DateService dateService;

    @Autowired
    @Qualifier(value = "userService")
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier(value = "dateService")
    private void setDateService(DateService dateService) {
        this.dateService = dateService;
    }


    /**
     * Probable implementation using Java instead of JavaScript
     *
     * @param model instance of Model interface
     * @return
     */
//    @GetMapping("/calendar")
//    public String getCalendar(Model model) {
//        Calendar c = Calendar.getInstance();
//        int y = c.get(Calendar.YEAR);
//        int m = c.get(Calendar.MONTH) + 1;
//        Month month = new Month(y, m);
//        int[][] days = month.getDays();
//        int height = month.getHeight();
//        String monthName = month.getMonthName(m);
//        String[] months = month.getMonths();
//        monthName.length();
//        model.addAttribute("days", days);
//        model.addAttribute("height", height);
//        model.addAttribute("year", y);
//        model.addAttribute("monthName", monthName);
//        model.addAttribute("months", months);
//        return "/resources/pages/calendar12.html";
//    }
    @RequestMapping(value = "OfficeProject/dates", params = {"userId"}, method = RequestMethod.GET)
    public String getUserDates(Model model, @RequestParam("userId") int id) {
        User user = userService.get(id);
        Set<DateEntity> dates = user.getDates();

        model.addAttribute("user", user);
        model.addAttribute("myDates", dates);
        return "userDates";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @RequestMapping(value = "OfficeProject/dates", params = {"userId"}, method = RequestMethod.POST)
    public String addNew(@ModelAttribute("duedate") Date date, @ModelAttribute("userId") int userId, Model model) {
        if (userService.isActual(userId)) {
            dateService.setNewDate(userId, date);
            getUserDates(model, userId);
            return "redirect: /OfficeProject/dates?id=" + userId;
        }
        return "error";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "OfficeProject/dates", method = RequestMethod.GET)
    public String getDatePage() {
        return "dateUsers";
    }


    @RequestMapping(value = "OfficeProject/users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsersByDate(@RequestParam("adate") Date date, Model model) {
        Set<User> users = dateService.getUsersByDate(date);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (users != null) {
            model.addAttribute("users", users);
            model.addAttribute("month", getMonth(localDate.getMonthValue()));
            model.addAttribute("day", localDate.getDayOfMonth());
        }
        return "dateUsers";
    }


    private String getMonth(int index) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July"
                , "August", "September", "October", "November", "December"};
        return months[index - 1];
    }

}
