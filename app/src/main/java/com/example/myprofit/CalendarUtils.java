package com.example.myprofit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {

    // A static variable to hold the currently selected date
    public static LocalDate selectedDate;
    // Method to format a LocalDate into "dd MMMM yyyy" format
    public static Object formattedData(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }
    // Method to format a LocalDate into "MMM dd, yyyy" format
    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return date.format(formatter);
    }
    // Method to format a LocalTime into "hh: mm: ss a" format
    public static Object formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh: mm: ss a");
        return time.format(formatter);
    }
    // Method to format a LocalDate into "MMMM yyyy" format
    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
    // Method to generate an array of dates representing the days in a month
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();// יכאן אנו יוצרים רשימה חדשה שתאחסן את כל תאריכי החודש.
        YearMonth yearMonth = YearMonth.from(date);// אנו משתמשים במחלקת YearMonth כדי לקבל את השנה והחודש מתוך התאריך שהתקבל כפרמטר.
        //אנו משיגים את מספר הימים בחודש הנוכחי.
        int daysInMonth = yearMonth.lengthOfMonth();
        //אנו מקבלים את התאריך הראשון בחודש.
        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();// אנו משיגים את היום בשבוע של התאריך הראשון בחודש.
        // אנו ממלאים את הרשימה עם תאריכים, כולל ימי המילוי בתחילת ובסופו של החודש.
        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)//בתנאי הזה בודקים אם התאריך הנוכחי נמצא לפני תחילת החודש או אחרי סוף החודש.
            {
                daysInMonthArray.add(null);// Add null for padding days
            }
            else
            {
                // Add the actual date
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return  daysInMonthArray;//אנו מחזירים את רשימת תאריכי החודש שמילאנו.
    }
    // Method to generate an array of dates representing the days in a week
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();// יצירת רשימה שבה נאחסן את כל תאריכי השבוע
        LocalDate current = sundayForDate(selectedDate);// אנו משיגים את תאריך יום ראשון הקרוב ביותר לתאריך שהתקבל.
        LocalDate endDate = current.plusWeeks(1);// אנו משיגים את תאריך הסיום, שהוא שבוע אחרי היום ראשון הנוכחי.
        while (current.isBefore(endDate))// אנו משתמשים בלולאת כל עוד כדי להוסיף את כל ימי השבוע לרשימה
        {
            days.add(current);// אנו מוסיפים את היום הנוכחי לרשימה
            // אנו מזיזים את התאריך הנוכחי ליום הבא בשבוע, כדי להתקדם בלולאה ולהוסיף גם את הימים הבאים.
            current =current.plusDays(1);
        }
        return days;
    }
    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate onWeekAgo = current.minusWeeks(1);// אנו משיגים את התאריך לפני שבוע מהתאריך הנתון.
        // אנו משתמשים בלולאת כל עוד כדי לחפש את יום ראשון הקרוב ביותר.
        while (current.isAfter(onWeekAgo))
        {
            //אם היום הנוכחי הוא יום ראשון, אנו מחזירים את התאריך הזה
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;// אנו מזיזים את התאריך הנוכחי ליום הקודם, על מנת להמשיך לחפש יום ראשון.
            current = current.minusDays(1);// Move to the previous day
        }
        return null;// אם לא מוצאים יום ראשון (מה שאינו צפוי בשימוש רגיל), אנו מחזירים null
    }
}
