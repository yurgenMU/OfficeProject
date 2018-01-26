package controllers;

import calendarService.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.DateService;
import service.UserService;

import java.util.Calendar;

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


}
