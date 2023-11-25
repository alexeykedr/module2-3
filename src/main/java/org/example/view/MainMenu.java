package org.example.view;

import java.util.Scanner;

public class MainMenu implements Menu {
    Scanner scanner = new Scanner(System.in);
    DeveloperView developerView = new DeveloperView();
    SkillView skillView = new SkillView();
    SpecialtyView specialtyView = new SpecialtyView();

    @Override
    public void showMenu() {
        int choice;
        do {
            System.out.println("You can work with next entities\n" +
                    "1. Developer\n" +
                    "2. Skill\n" +
                    "3. Specialty\n" +
                    "4. Exit this menu\n" +
                    "enter number of your choice:"
            );
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    developerView.showMenu();
                    break;
                case 2:
                    skillView.showMenu();
                    break;
                case 3:
                    specialtyView.showMenu();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Enter correct number");
                    choice = scanner.nextInt();
            }
        } while (choice != 4);

    }
}
