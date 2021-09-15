package Library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private ObjectId id;
    private String title;
    private String author;
    private String description;
    private int isbn;
    private User user;
    private LocalDateTime dateTaken;
    private LocalDateTime dateReturn;
}
