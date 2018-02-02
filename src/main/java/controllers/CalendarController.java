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

    @GetMapping("/calendar")
    public String getCalendar(Model model) {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        Month month = new Month(y, m);
        int[][] days = month.getDays();
        int height = month.getHeight();
        String monthName = month.getMonthName(m);
        String[] months = month.getMonths();
        monthName.length();
        model.addAttribute("days", days);
        model.addAttribute("height", height);
        model.addAttribute("year", y);
        model.addAttribute("monthName", monthName);
        model.addAttribute("months", months);
        return "/resources/pages/calendar12.html";
    }


    @RequestMapping(value = "OfficeProject/dates",params = {"userId"},  method = RequestMethod.GET)
    private String getUserDates(Model model, @RequestParam("userId") int id) {
        User user = userService.get(id);
        Set<DateEntity> dates = user.getDates();
        model.addAttribute("user", user);
        model.addAttribute("myDates", dates);
        return "userDates";

    }

    @RequestMapping(value = "OfficeProject/dates",params = {"userId"},  method = RequestMethod.POST)
    private String addNew(@ModelAttribute("duedate") Date date, @ModelAttribute("userId") int userId, Model model) {
        User user = userService.get(userId);
        Set<DateEntity> dates = user.getDates();
        DateEntity dateEntity = new DateEntity();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        DateEntity existing = dateService.findByDate(sqlDate);

        if (existing == null){
            dateEntity.setDate(sqlDate);
        } else
            dateEntity = existing;
        dates.add(dateEntity);
        user.setDates(dates);
        userService.edit(user);
        return getUserDates(model, userId);

    }

}
