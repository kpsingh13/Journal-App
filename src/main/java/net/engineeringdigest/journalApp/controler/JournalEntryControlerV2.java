package net.engineeringdigest.journalApp.controler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

import java.util.Map;
import java.util.Optional;




@RestController
@RequestMapping("/journal")
public class JournalEntryControlerV2 {

    private Map<Long, JournalEntry> JournalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesofUsers(@PathVariable String userName){
        
        User user= userService.findByUserName(userName);

        List<JournalEntry> all= user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try{

           

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid){
        
        Optional<JournalEntry> journalEntry= journalEntryService.findById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }


    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid){     // '?' here represents Wild card pattern. In future it can return the objects of soome other class also, not just Entity class.
       journalEntryService.deleteById(myid);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry old= journalEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent():old.getContent());
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }



}
