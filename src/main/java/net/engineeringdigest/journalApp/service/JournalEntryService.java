package net.engineeringdigest.journalApp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry jounralEntry,String userName){
        try{
            User user= userService.findByUserName(userName);
            JournalEntry saved= journalEntryRepository.save(jounralEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry",e);
        }
        

    }

    public void saveEntry(JournalEntry jounralEntry){
        journalEntryRepository.save(jounralEntry);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        boolean removed=false;
        try{
            User user= userService.findByUserName(userName);
            removed=user.getJournalEntries().removeIf(x ->x.getId().equals(id));
            if(removed){
                userService.saveUser(user );
                journalEntryRepository.deleteById(id);
            }
            return removed;
        } catch(Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occured during while deleting the entry",e);
        }
       
    }


}
