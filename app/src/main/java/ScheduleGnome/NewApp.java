package ScheduleGnome;

import java.util.ArrayList;
import java.util.Scanner;

public class NewApp {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<User>();
        Search searchTool = new Search(true);

        Scanner in = new Scanner(System.in);

        User currentUser = new NewApp().setUser(in, users);
        if (currentUser == null)
            return;

        boolean quit = false;
        do {
            Schedule currentSchedule = new NewApp().setSchedule(in, currentUser);
            if (currentSchedule == null)
                return;
            quit = new NewApp().editSchedule(in, currentSchedule, searchTool);
        } while (!quit);

    }

    void addCourse(Scanner in, Schedule currentSchedule, Search searchTool) {
        searchTool.querySearch();
        String results = searchTool.resultToString();
        while (true) {
            System.out.println(currentSchedule.getCalendar());
            System.out.println(results);
            String input = getInput("Input add [number] to add to schedule\n" +
                    "Input return to go back", in);

            if (input.startsWith("add ")) {
                int id = Integer.parseInt(input.substring(4));
                currentSchedule.addEvent(searchTool.getResults().get(id));
                System.out.println("Course added to schedule!");
            }

            switch (input) {
                case "return":
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    boolean editSchedule(Scanner in, Schedule currentSchedule, Search searchTool) {
        String input = getInput("Type ? [search query] to search courses to add\n" +
                "Type remove [course code] to remove courses\n" +
                "Type return to go back\n" +
                "Type quit to quit", in);

        if (input.startsWith("? ")) {
            searchTool.setSearched(input.substring(2));
            addCourse(in, currentSchedule, searchTool);
            return editSchedule(in, currentSchedule, searchTool);
        } else if (input.startsWith("remove ")) {
            Course toRemove = currentSchedule.getCourse(input.substring(8));
            if (toRemove != null)
                currentSchedule.deleteEvent(toRemove);
            else
                System.out.println("invalid course code");
        }

        switch (input) {
            case "return":
                return false;
            case "quit":
                System.out.println("Have a great day :)");
                return true;
            default:
                System.out.println("Invalid input");
                break;
        }
        return false;
    }

    Schedule setSchedule(Scanner in, User currentUser) {
        while (true) {
            switch (getInput("Type edit to select saved schedules\n" +
                    "Type new to create a new schedule\n" +
                    "Type quit to quit", in)) {
                case "edit":
                    if (currentUser.getSavedSchedules().isEmpty()) {
                        System.out.println("You don't have any schedules, you might want to create one");
                        break;
                    }
                    return selectCurrentSchedule(in, currentUser);
                case "new":
                    return new Schedule(getInput("What would you like to name your new schedule?", in), true);
                case "quit":
                    System.out.println("Have a great day :)");
                    return null;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    Schedule selectCurrentSchedule(Scanner in, User currentUser) {
        System.out.println(currentUser.printScheduleNames());

        Schedule schedule = currentUser.getSavedSchedules().get(getInput("Which shedule would you like to edit?", in));

        if (schedule != null)
            return schedule;

        System.out.println("Sorry, that is not the name of a current schedule");
        return selectCurrentSchedule(in, currentUser);
    }

    User setUser(Scanner in, ArrayList<User> users) {
        System.out.println("Welcome to ScheduleGnome!");

        User currentUser = null;

        while (currentUser == null) {
            switch (getInput("Type new to create a new user\n" +
                    "Type login to login\n" +
                    "Type quit to quit", in)) {
                case "new":
                    currentUser = createUser(in);
                    users.add(currentUser);
                    break;
                case "login":
                    currentUser = login(in, users);
                    break;
                case "quit":
                    System.out.println("Have a great day :)");
                    return null;
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }

        System.out.println("You have logged in as " + currentUser.getUsername() + "!");

        return currentUser;
    }

    User createUser(Scanner in) {
        String username = getInput("Please enter your new username:", in);

        String password = getInput("Please enter your new password:", in);
        String passwordConfirm = getInput("Please enter your password again:", in);

        if (password.equals(passwordConfirm)) {
            return new User(username, password);
        }
        System.out.println("Your passwords do not match");
        return createUser(in);
    }

    User login(Scanner in, ArrayList<User> users) {
        String username = getInput("Please enter your username:", in);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.checkPassword(getInput("Please enter your password:", in))) {
                    return user;
                }
                System.out.println("Sorry, your password is incorrect");
                return null;
            }
        }

        System.out.println("Sorry, there is no user named " + username);

        return null;
    }

    String getInput(String prompt, Scanner in) {
        System.out.println(prompt);
        String input = in.nextLine();
        while (input.isBlank()) {
            input = in.nextLine();
        }
        return input;
    }
}
