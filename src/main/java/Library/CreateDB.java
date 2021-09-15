package Library;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CreateDB {
    public static void main(String[] args) {
        Main main = new Main();
        MongoClient client = MongoClientProvider.getClient();
        MongoDatabase database = client.getDatabase("Library_DB");
        MongoCollection<Book> collection = database.getCollection("books", Book.class);

        collection.insertMany(List.of(new Book(null, "Book1", "Author1", "Fantasy", 11111, new User ("Andrius", "Baltrunas"), LocalDateTime.now(), main.returnDate(LocalDateTime.now(), 31 )),
                new Book(null, "Book2", "Author2", "Fiction", 22222,null, null, null),
                new Book(null, "Book3", "Author3", "Historic", 33333, null, null,null),
                new Book(null, "Book4", "Author4", "Fiction", 44444, null, null,null),
                new Book(null, "Book5", "Author5", "Fantasy", 55555, null, null,null)));

        client.close();
    }
}
