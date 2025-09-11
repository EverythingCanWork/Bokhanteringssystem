
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> bookTitles = new ArrayList<>();
        ArrayList<String> bookAuthors = new ArrayList<>();
        ArrayList<String> bookISBN = new ArrayList<>();
        ArrayList<Boolean> bookAvailable = new ArrayList<>(); // true = tillgänglig
        // Lån (index motsvarar varandra)
        ArrayList<String> borrowerNames = new ArrayList<>();
        ArrayList<String> borrowedBooks = new ArrayList<>(); // ISBN för lånad bok
        ArrayList<String> userNames = new ArrayList<>();
        ArrayList<String> phoneNumbers = new ArrayList<>();

        bookTitles.add("Harry Potter");
        bookTitles.add("Sagan om ringen");
        bookTitles.add("1984");
        bookAuthors.add("J.K. Rowling");
        bookAuthors.add("Tolkien");
        bookAuthors.add("Orwell");
        bookISBN.add("111");
        bookISBN.add("222");
        bookISBN.add("333");
        bookAvailable.add(true);
        bookAvailable.add(true);
        bookAvailable.add(false); // 1984 är utlånad
        // Fördefinierade användare
        userNames.add("Anna");
        userNames.add("Erik");
        phoneNumbers.add("070-1234567");
        phoneNumbers.add("070-7654321");
        // Fördefinierat lån
        borrowerNames.add("Anna");
        borrowedBooks.add("333"); // Anna har lånat 1984
        mainLoop:
        while (true) {
            int mainMenuSelection = displayMainMenu();
            switch (mainMenuSelection) {
                case 1:
                    bookMenu(scanner, bookTitles, bookAuthors, bookISBN, bookAvailable);
                    break;
                case 2:
                    loanMenu(scanner, bookTitles, bookAuthors, bookISBN, bookAvailable, borrowerNames, borrowedBooks);
                    break;
                case 3:
                    UsersMenu(scanner, phoneNumbers, userNames);
                    break;
                case 4:
                    //statistik
                case 0:
                    System.out.println("exiting...");
                    break mainLoop;
            }
        }
    }

    public static int displayMainMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== BIBLIOTEKSSYSTEM ===");
        System.out.println("1. Bokhantering");
        System.out.println("2. Lånehantering");
        System.out.println("3. Hantera användare");
        System.out.println("4. Visa statistik");
        System.out.println("0. Avsluta");
        return scanner.nextInt();
    }

    public static void bookMenu(Scanner scanner,
                                ArrayList<String> bookTitles,
                                ArrayList<String> bookAuthors,
                                ArrayList<String> bookISBN,
                                ArrayList<Boolean> bookAvailable){
        System.out.println("\n=== BOKHANTERINGSSYSTEM ===");
        System.out.println("1. Lägg till bok");
        System.out.println("2. Visa böcker");
        System.out.println("3. Sök efter bok");
        System.out.println("4. Ta bort bok");
        System.out.println("0. Gå tillbaka");
        int menuSelection = scanner.nextInt();
        scanner.nextLine();
        switch (menuSelection) {
            case 1:
                System.out.println("Titel: ");
                String titleAdd = scanner.nextLine();
                System.out.println("Författare: ");
                String authorAdd = scanner.nextLine();
                System.out.println("ISBN: ");
                String isbnADD = scanner.nextLine();
                addBook(bookTitles, bookAuthors, bookISBN, titleAdd, authorAdd, isbnADD);
                break;
            case 2:
                displayAllBooks(bookTitles, bookAuthors, bookISBN, bookAvailable);
                break;
            case 3:
                System.out.println("please enter a search word: ");
                String searchTerm = scanner.nextLine();
                int bookIndex = searchBook(bookTitles, bookAuthors,searchTerm);
                if(bookIndex == -1){
                    System.out.println("book does not exist");
                }else {
                    System.out.println("book exists");
                    System.out.printf("The books name is %s the author is %s and the ISBN number is %s", bookTitles.get(bookIndex), bookAuthors.get(bookIndex), bookISBN.get(bookIndex));
                }
                break;
            case 4:
                //lägg till ta bort bok här
                break;
            case 0:
                break;
        }
    }

    public static void UsersMenu(Scanner scanner,
                                     ArrayList<String> phoneNumbers,
                                     ArrayList<String> userNames){
        System.out.println("\n=== Hantera användare ===");
        System.out.println("1. Lägg till användare");
        System.out.println("2. lista användare");
        System.out.println("3. Sök efter användare");
        System.out.println("4. Ta bort användare");
        System.out.println("0. Gå tillbaka");
        int menuSelection = scanner.nextInt();
        scanner.nextLine();
        switch (menuSelection) {
            case 1:
                System.out.println("Namn: ");
                String userNameAdd = scanner.nextLine();
                System.out.println("Telefon: ");
                String phoneNumber = scanner.nextLine();
                registerUser(phoneNumbers, userNames, phoneNumber, userNameAdd);
                break;
            case 2:
                displayAllUsers(userNames,phoneNumbers);
                break;
            case 3:
                System.out.println("Write a name you want to search for: \n");
                String userNameSearch = scanner.nextLine();
                int userIndex = searchUser(userNames, userNameSearch);
                if (userIndex == -1){
                    System.out.println("User does not exist");
                } else {
                    System.out.printf("%s exist, his phone number is %s", userNames.get(userIndex), phoneNumbers.get(userIndex));
                }
                break;
            case 0:
                break;
        }
    }


    public static void loanMenu(Scanner scanner,
                                ArrayList<String> bookTitles,
                                ArrayList<String> bookAuthors,
                                ArrayList<String> bookISBN,
                                ArrayList<Boolean> bookAvailable,
                                ArrayList<String> borrowerNames,
                                ArrayList<String> borrowedBooks){
        System.out.println("\n=== LÅNEHANTERING ===");
        System.out.println("1. Visa böcker");
        System.out.println("2. Låna bok");
        System.out.println("3. Lämmna tillbaka bok");
        System.out.println("4. Visa Lånade Böcker");
        System.out.println("0. Gå tillbaka");
        int menuSelection = scanner.nextInt();
        scanner.nextLine();
        switch (menuSelection) {
            case 1:
                displayAllBooks(bookTitles, bookAuthors, bookISBN, bookAvailable);
                break;
            case 2:
                System.out.println("please enter your name");
                String borrowerName = scanner.nextLine();
                System.out.println("please enter the index of the book you want to borrow");
                int bookIndex = scanner.nextInt() -1;
                borrowBook( bookAvailable, borrowerNames, borrowedBooks, bookIndex, borrowerName, bookTitles, bookISBN);
                break;
            case 3:
                System.out.println("Enter isbn number: ");
                String isbnNumber = scanner.nextLine();
                returnBook(bookAvailable, borrowerNames, borrowedBooks, isbnNumber, bookISBN);
            case 4:
                displayBorrowedBooks(borrowerNames, borrowedBooks, bookISBN, bookTitles);
            case 0:
                break;
        }
    }

    // Person 1, Marco
    public static void addBook(ArrayList<String> titles,
                               ArrayList<String> authors,
                               ArrayList<String> isbn,
                               String title,
                            String author, String isbnNumber) {

        titles.add(title);
        authors.add(author);
        isbn.add(isbnNumber);
    }

    public static void displayAllBooks(ArrayList<String> titles,
                                       ArrayList<String> authors, ArrayList<String> isbn, ArrayList<Boolean> bookAvailable) {

        System.out.println("Display all books");

        for (int i = 0; i < titles.size(); i++){

            String availableOutput = bookAvailable.get(i) ? " Available%n" : " Not available%n";

            System.out.printf(titles.get(i) + " " + authors.get(i) + " " + isbn.get(i) + availableOutput);
        }
    }

    public static int searchBook(ArrayList<String> titles,
                                 ArrayList<String> authors, String searchTerm) {

        int bookIndex = -1;

        if (titles.contains(searchTerm)){
            bookIndex = titles.indexOf(searchTerm);

        } else {
            if (authors.contains(searchTerm)){
                bookIndex = authors.indexOf(searchTerm);
            }
        }

        return bookIndex;
    }

    //PERSON2
    public static boolean borrowBook(ArrayList<Boolean> bookAvailable,
                                    ArrayList<String> borrowerNames,
                                    ArrayList<String> borrowedBooks,
                                    int bookIndex,
                                    String borrowerName,
                                    ArrayList<String> bookTitles,
                                    ArrayList<String> bookISBN
                                    ){

                    if(bookIndex<0 || bookIndex>= bookTitles.size()){
                        System.out.println("---Invalid book index!---");
                        return false;
                    }

                 if(bookAvailable.get(bookIndex)){ //if the book is free in available arrayList
                        bookAvailable.set(bookIndex, false);//set is as false
                        borrowerNames.add(borrowerName);   //add borrowersName to the list of borrowers
                        borrowedBooks.add(bookISBN.get(bookIndex));//Add bookindex to borrowedbooks list
                        System.out.println("---Done!---");
                        return true;
                      }else{
                          System.out.println("---This book is already borrowed!---");
                          return false;
                      }

    }

    public static boolean returnBook(ArrayList<Boolean> bookAvailable,
                                    ArrayList<String> borrowerNames,
                                    ArrayList<String> borrowedBooks,
                                    String isbnNumber,
                                    ArrayList<String>bookISBN){


            int bookIndex= bookISBN.indexOf(isbnNumber); //hitta bookindex i isbn list

            int loanedBookIndex=borrowedBooks.indexOf(isbnNumber); //hitta loanedbookindex in borrowedbooks
            if(!bookAvailable.get(loanedBookIndex)){
                bookAvailable.set(bookIndex, true);
                borrowerNames.remove(loanedBookIndex);
                borrowedBooks.remove(loanedBookIndex);
                return true;
            }else {
                return false;
            }

    }


    public static void displayBorrowedBooks(ArrayList<String>
    borrowers, ArrayList<String> borrowedBooks, ArrayList<String> isbn, ArrayList<String> titles){

        // shows a list of borrower-names and book-titles
        // get book-title from index, and index from bookISBN
        // marco

        int indexToFind = -1;

        if (borrowers.size() == 0){
            System.out.println("---- No Books Borrowed ----");
        } else {

            System.out.println("---- Borrowed Books ----");

            for (int i = 0; i < borrowers.size(); i++){

                String isbnToFind = borrowedBooks.get(i);

                // get index of borrowed book by comparing isbn
                for(int k = 0; k < isbn.size(); k++){

                    if (isbnToFind.equals(isbn.get(k))){
                        indexToFind = k;
                    }
                }

                // get title from index
                String titleToFind = titles.get((indexToFind));

                System.out.printf("%s\t\t%s", borrowers.get(i), titleToFind);
            }
        }
        System.out.println();
    }


//    //Person3

     public static void registerUser(ArrayList<String> userNames,
                                    ArrayList<String> phoneNumbers, String name, String
                                            phoneNumber){

        if(name == null) {
            System.out.print("Namn är obligatoriskt.");
        }
        userNames.add(name.trim());
        phoneNumbers.add(phoneNumber == null ? "" : phoneNumber.trim());
        System.out.println("Resgistrerad: " + name);
        System.out.println();

    }
     public static void displayAllUsers(ArrayList<String> userNames,
                                       ArrayList<String> phoneNumbers) {
        System.out.println("Visar alla användare: ");
        for (int i = 0; i < userNames.size(); i++) {
            System.out.printf(userNames.get(i) + " " + phoneNumbers.get(i));
            System.out.println();
        }
     }
         public static int searchUser(ArrayList<String> userNames, String name) {
        String trimName = name.toLowerCase();

        int result = -1;

        for (int i = 0; i < userNames.size(); i++) {
            if (userNames.get(i).toLowerCase().equals(trimName)) {
                System.out.println("Hittade användaren: " + userNames.get(i));
                result = i;
                break;
            }
        }
        if (result == -1){
            System.out.println("Ingen användare hittades.");
        }

        return result;
    }


//person4
    public static int countAvailableBooks ( ArrayList<Boolean> bookAvailable){

    int availableCounter = 0;

    for(int i=0; i<bookAvailable.size(); i++){
       //available=true
      if(bookAvailable.get(i)){
          availableCounter++  ;
      }
    }
    return availableCounter;
  }

    public static int countBorrowedBooks(ArrayList<Boolean>
    bookAvailable){
        // just count available == false, /marco
        int counter = 0;

        for ( boolean isAvailable : bookAvailable){
            if (!isAvailable){
                counter++;
            }
        }

        return counter;
    }

    public static void displayLibraryStatistics(ArrayList<String> titles, ArrayList<Boolean> available, ArrayList<String> userNames) {
        int totalBooks = titles.size(); //hejhej
        int totalAvailable = 0;
        int totalBorrowed = 0;
        System.out.println("Statistik: ");
        System.out.println();
        System.out.println("------------------------");
        for (int i = 0; i < totalBooks; i++) {
            if ( available.get(i) ) {
                totalAvailable++;
                System.out.println(titles.get(i) + " - Tillgänglig");
            } else {
                totalBorrowed++;
                System.out.println(titles.get(i) + "- Utlånad av: " + userNames.get(i) + ".");
            }
            System.out.println("Antal böcker:" + totalBooks + ". Utlåndade: " + totalBorrowed + ". Tillgängliga: " + totalAvailable + ".");
            System.out.println("------------------------");
        }
    }
}