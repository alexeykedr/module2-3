package org.example.view;

import org.example.controller.SpecialtyController;
import org.example.model.Specialty;

import java.util.Scanner;

public class SpecialtyView implements Menu {
    Scanner scanner = new Scanner(System.in);
    SpecialtyController specialtyController = new SpecialtyController();


    @Override
    public void showMenu() {
        int choice;
        do {


            System.out.println("What do you want to do with it?\n" +
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
                    Specialty specialty = new Specialty();
                    System.out.println("Enter new name");
                    specialty.setName(scanner.next());
                    specialtyController.create(specialty);
                    System.out.println("Specialty is created");
                    break;
                case 2:
                    System.out.println("Enter specialty's id");
                    Long id = scanner.nextLong();
                    System.out.println(specialtyController.get(id));
                    break;
                case 3:
                    System.out.println("it's all specialtys");
                    System.out.println(specialtyController.getAll());
                    break;
                case 4:
                    Specialty specialtyForUpdate = new Specialty();
                    System.out.println("Enter specialty's id for update");
                    specialtyForUpdate.setId(scanner.nextLong());
                    System.out.println("Enter new name");
                    specialtyForUpdate.setName(scanner.next());
                    specialtyController.update(specialtyForUpdate);
                    System.out.println("Specialty is updated");
                    break;
                case 5:
                    System.out.println("Enter specialty's id");
                    Long id3 = scanner.nextLong();
                    specialtyController.delete(id3);
                    System.out.println("Specialty is deleted");
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