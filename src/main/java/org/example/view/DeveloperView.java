package org.example.view;

import org.example.controller.DeveloperController;
import org.example.pojo.Developer;
import org.example.pojo.Status;

import java.util.Scanner;

public class DeveloperView implements Menu {
    Scanner scanner = new Scanner(System.in);
    DeveloperController developerController = new DeveloperController();

    @Override
    public void showMenu() {
        int choice;
        do {
            System.out.println("What do you want to do with Developer?\n" +
                    "1. create\n" +
                    "2. get by id\n" +
                    "3. get all\n" +
                    "4. update by id\n" +
                    "5. delete by id\n" +
                    "6. Exit this menu\n" +
                    "enter number of your choice:"
            );
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Developer developer = new Developer();
                    System.out.println("Enter new first name");
                    developer.setFirstName(scanner.next());
                    System.out.println("Enter new last name");
                    developer.setLastName(scanner.next());
                    developer.setStatus(Status.ACTIVE);
                    developerController.create(developer);
                    System.out.println("Developer is created - View");
                    break;
                case 2:
                    System.out.println("Enter developer's id");
                    Long id = scanner.nextLong();
                    System.out.println(developerController.get(id));
                    break;
                case 3:
                    System.out.println("it's all developers");
                    System.out.println(developerController.getAll());
                    break;
                case 4:
                    Developer developerForUpdate = new Developer();
                    System.out.println("Enter id developer for update");
                    developerForUpdate.setId(scanner.nextLong());
                    System.out.println("Enter new first name");
                    developerForUpdate.setFirstName(scanner.next());
                    System.out.println("Enter new last name");
                    developerForUpdate.setLastName(scanner.next());
                    developerController.update(developerForUpdate);
                    System.out.println("Developer is updated");
                    break;
                case 5:
                    System.out.println("Enter developer's id");
                    Long id3 = scanner.nextLong();
                    developerController.delete(id3);
                    System.out.println("Developer is deleted");
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Enter correct number");
                    choice = scanner.nextInt();
            }
            System.out.println("Anything else?");
        } while (choice != 6);
    }
}
