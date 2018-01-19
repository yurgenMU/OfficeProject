package CalendarService;

import java.util.Calendar;

public class Month {
    private final static String[] months = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};

    private int monthValue;
    private int yearValue;

    public String[] getMonths() {
        return months;
    }

    public Month(int yearValue, int monthValue) {
        this.monthValue = monthValue;
        this.yearValue = yearValue;
    }

    public int getHeight() {
        return height;
    }

    private int height;

    public Month(String year, String month) {
        this.monthValue = getMonthIndex(month);
        this.yearValue = Integer.parseInt(year);
    }


    public int getMonthValue() {
        return monthValue;
    }

    public int getYearValue() {
        return yearValue;
    }

    public int[][] getDays() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearValue);
        c.set(Calendar.MONTH, monthValue - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int numberOfDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = c.get(Calendar.DAY_OF_WEEK) - 1;
        int width = 7;
        if ((numberOfDays == 28) && (firstDay == 1))
            height = 4;
        else height = 5;
        int[][] days = new int[height][width];
        int actualDay = 1;
        for (int i = 0; i < height; i++) {
            Outer:
            for (int j = 0; j < width; j++) {
                if (actualDay <= numberOfDays) {
                    if ((i == 0) && (j < firstDay - 1)) {
                        days[i][j] = 0;
                    } else {
                        days[i][j] = actualDay;
                        actualDay++;
                    }
                }
            }
        }
        return days;

    }

    public static int getMonthIndex(String name) {
        int index = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(name)) {
                index = i + 1;
            }
        }
        return index;
    }


    public static String getMonthName(int index) {
        String name = "";
        for (int i = 0; i < months.length; i++) {
            if (i == index) {
                name = months[i - 1];
            }
        }
        return name;
    }

}
