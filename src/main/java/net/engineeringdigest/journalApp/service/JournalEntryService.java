package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    
    public void saveEntry(JournalEntry journalEntry, String userName){

        User user = userService.findByUserName(userName);
        journalEntry.setDate((LocalDateTime.now()));
        JournalEntry save= journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(save);
        userService.saveEntry(user);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);

        // journalEntryRepository.deleteById(id);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
        
    }

    

    

}
