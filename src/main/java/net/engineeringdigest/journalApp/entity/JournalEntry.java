package net.engineeringdigest.journalApp.entity;

import java.time.LocalDateTime;
import java.util.Date;

// import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries") //this defines a row in mongodb database
// @Getter
// @Setter   // or use @Data to handle any data entry exit
@Data
@NoArgsConstructor // added becaus @data annotation  does not handle no args constructor, so specifying it explicityly, used for deserialization -> json to POJO.
public class JournalEntry {
 
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    // public LocalDateTime getDate() {
    //     return date;
    // }
 
    // public void setDate(LocalDateTime date) {
    //     this.date = date;
    // }

    // public ObjectId getId() {
    //     return id;
    // }

    // public void setId(ObjectId id) {
    //     this.id = id;
    // }

    // public String getTitle() {
    //     return title;
    // }

    // public void setTitle(String title) {
    //     this.title = title;
    // }

    // public String getContent() {
    //     return content;
    // }

    // public void setContent(String content) {
    //     this.content = content;
    // }


}
