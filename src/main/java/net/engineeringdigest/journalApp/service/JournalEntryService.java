package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    
    @Transactional //transactional context
    public void saveEntry(JournalEntry journalEntry, String userName){

        try{
        User user = userService.findByUserName(userName);
        journalEntry.setDate((LocalDateTime.now()));
        JournalEntry save= journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(save);
        userService.saveEntry(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occur while saving the entry",e);
        }
        

    }

    public void updateEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed =false;
        try{
                
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));

            if(removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
           
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("journal not found!...");
        }
         return removed;
    }

    public List<JournalEntry> findByUserName(String userName){
        return journalEntryRepository.findAll();
    }
    

    

}
