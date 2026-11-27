package ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
   private final Scanner scanner = new Scanner(System.in);

   public String readLine(String prompt) {
      System.out.print(prompt);
      return scanner.nextLine().trim();
   }

   public int readInt(String prompt) {
      while (true) {
         try {
            return Integer.parseInt(readLine(prompt));
         } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
         }
      }
   }

   public BigDecimal readBigDecimal(String prompt) {
      while (true) {
         try {
            return new BigDecimal(readLine(prompt));
         } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please try again.");
         }
      }
   }

   public <T> T selectFromList(String title, List<T> options, java.util.function.Function<T, String> displayMapper) {
      System.out.println(title);
      for (int i = 0; i < options.size(); i++) {
         System.out.printf("  %d. %s\n", i + 1, displayMapper.apply(options.get(i)));
      }
      int choice = readInt("Choose (1-" + options.size() + "): ");
      if (choice >= 1 && choice <= options.size()) {
         return options.get(choice - 1);
      }
      System.out.println("Invalid choice, defaulting to first option.");
      return options.get(0);
   }

   public void printLine(String line) {
      System.out.println(line);
   }

   public void printHeader(String title) {
      String line = "=".repeat(60);
      System.out.println(line);
      System.out.println("  " + title);
      System.out.println(line);
   }

   public void printSeparator() {
      System.out.println("-".repeat(60));
   }

   public void close() {
      scanner.close();
   }
}