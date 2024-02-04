package org.example.view;

import org.example.controller.SkillController;
import org.example.pojo.Skill;

import java.util.Scanner;

public class SkillView implements Menu {

    Scanner scanner = new Scanner(System.in);
    SkillController skillController = new SkillController();

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
                    Skill skill = new Skill();
                    System.out.println("Enter new name");
                    skill.setName(scanner.next());
                    skillController.create(skill);
                    System.out.println("Skill is created");
                    break;
                case 2:
                    System.out.println("Enter skill's id");
                    Long id = scanner.nextLong();
                    System.out.println(skillController.get(id));
                    break;
                case 3:
                    System.out.println("it's all skills");
                    System.out.println(skillController.getAll());
                    break;
                case 4:
                    Skill skillForUpdate = new Skill();
                    System.out.println("Enter skill's id for update");
                    skillForUpdate.setId(scanner.nextLong());
                    System.out.println("Enter new name");
                    skillForUpdate.setName(scanner.next());
                    skillController.update(skillForUpdate);
                    System.out.println("Skill is updated");
                    break;
                case 5:
                    System.out.println("Enter skill's id");
                    Long id3 = scanner.nextLong();
                    skillController.delete(id3);
                    System.out.println("Skill is deleted");
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
