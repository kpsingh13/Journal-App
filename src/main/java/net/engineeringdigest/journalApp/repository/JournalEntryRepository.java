package net.engineeringdigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;

// @Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId>{

    
}
