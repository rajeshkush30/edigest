package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional//Treat as a single operation if any line fail all operation fail
    public JournalEntry saveEntry(JournalEntry journalEntry, String username)
    {
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            return saved;
        } catch (Exception e) {
            throw new RuntimeException("An error occur when save entry "+e);
        }
    }

    public List<JournalEntry> getEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public void deleteEntryById(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateEntry(JournalEntry journalEntry)
    {
        return journalEntryRepository.save(journalEntry);
    }
}
