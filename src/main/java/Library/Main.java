package Library;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("________________________________________________________");
            System.out.println("|   [1] užsisakyti knyga                                |");
            System.out.println("|   [2] kiek yra laisvų knygų                           |");
            System.out.println("|   [3] kokios knygos yra paimtos ir kada bus grąžintos |");
            System.out.println("|   [4] prasistesti knygos grąžinimą                    |");
            System.out.println("|   [5] exit                                            |");
            System.out.println("|_______________________________________________________|");
            System.out.println("Please enter [1],[2],[3], [4] or [5]:");
            String input = sc.next();
            MongoClient client = MongoClientProvider.getClient();
            MongoDatabase database = client.getDatabase("Library_DB");
            MongoCollection<Book> collection = database.getCollection("books", Book.class);
            Iterator<Book> iterator = collection.find().iterator();
            switch (input) {
                case "1" -> {
                    System.out.println("*** Registration ***");
                        System.out.println("Enter book's title:");
                        String bookTitle = sc.next();
                        System.out.println("Enter author's name:");
                        String authorName = sc.next();
                        try {
                            iterator = collection.find(Filters.and(Filters.eq("title", bookTitle), Filters.eq("author", authorName),Filters.eq("user", null) )).iterator();
                            if( iterator.hasNext()){
                                System.out.println("This book is found and it is available, registration in process...");
                                System.out.println("Enter your name:");
                                String name = sc.next();
                                System.out.println("Enter your surname:");
                                String surname = sc.next();
                                collection.updateOne(Filters.and(Filters.eq("title", bookTitle), Filters.eq("author", authorName)), Updates.set("user", new User (name,surname)));
                                collection.updateOne(Filters.and(Filters.eq("title", bookTitle), Filters.eq("author", authorName)), Updates.set("dateTaken", LocalDateTime.now()));
                                collection.updateOne(Filters.and(Filters.eq("title", bookTitle), Filters.eq("author", authorName)), Updates.set("dateReturn", main.returnDate(LocalDateTime.now(),31)));
                                System.out.println("Registration completed:" + "\n Book Title: " + bookTitle + "\n Author: " + authorName + "\n Return date: " + main.returnDate(LocalDateTime.now(),31) );
                            } else {
                                System.out.println("This book is not found or not available now. Please press [2] from menu and check for available books.");
                            }

                        } catch (Exception e) {
                            System.out.println("Something went wrong, try again.");
                        }
                }
                case "2" -> {
                    iterator = collection.find(Filters.eq("user", null)).iterator();
                    int count = 0;
                    while(iterator.hasNext()){
                        System.out.println(iterator.next());
                        count++;
                    }
                    System.out.println("Total number of books available now: " + count );
                }
                case "3" -> {
                    iterator = collection.find(Filters.not(Filters.eq("user", null))).iterator();
                    int count = 0;
                    while(iterator.hasNext()){
                        System.out.println(iterator.next());
                        count++;
                    }
                    System.out.println("Total number of books taken: " + count );
                }
                case "4" -> {
                    System.out.println("*** Updating Return Date ***");
                    System.out.println("Enter book's ISBN number:");
                    String isbn = sc.next();
                    try {
                        iterator = collection.find(Filters.and(Filters.eq("isbn", Integer.valueOf(isbn)), Filters.not(Filters.eq("user", null)))).iterator();
//                        iterator = collection.find(Filters.eq("isbn", Integer.valueOf(isbn))).iterator();
                        if(iterator.hasNext()){
                            collection.updateOne(Filters.eq("isbn", Integer.valueOf(isbn)), Updates.set("dateReturn", main.returnDate(LocalDateTime.now(),31)));
                            System.out.println("Update completed: \n New Return date is: " +  main.returnDate(LocalDateTime.now(),31));
                        } else {
                            System.out.println("This book is not found among taken books. Please try again.");
                        }

                    } catch (Exception e) {
                        System.out.println("Something went wrong, try again.");
                    }

                }
                case "5" -> {
                    System.exit(0);
                }
                default -> System.out.println("something went wrong, try again.");
            }
        } while (true);
    }
    public LocalDateTime returnDate (LocalDateTime date, int days){
        return date.plusDays(days);
    }
}
