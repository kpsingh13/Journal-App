package net.engineeringdigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;

// @Component
public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findByUserName(String username);

    User deleteByUserName(String username);
}
