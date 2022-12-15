package freelance.domain.models.entity;

import freelance.domain.models.objetValue.Email;
import freelance.domain.models.objetValue.Password;
import freelance.domain.models.objetValue.Profile;
import freelance.domain.models.objetValue.UserId;

import java.time.LocalDateTime;
import java.util.*;

public class User extends Auditable{
    UserId id;

    public User(UserId id, String firstName, String lastName, Email email, Password passWord, Set<Profile> profiles, boolean isActive,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.profiles = profiles;
        this.isActive = isActive;
    }

    public User(UserId id, String firstName, String lastName, Email email, Password passWord, Set<Profile> profiles, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.profiles = profiles;
        this.isActive = isActive;
    }
    public User(String firstName, String lastName, String email, String passWord, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
        this.passWord = new Password(passWord);
        this.isActive = isActive;
    }
    String firstName;
    String lastName;
    Email email;
    Password passWord;
    Set<Profile> profiles;
    boolean isActive;

    public void update(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPassWord(String passWord) {
        this.passWord = new Password(passWord);
    }

    public User addProfile(Profile profile) {
        if(profiles==null)
            profiles= new HashSet<>();
        if(!profiles.contains(profile)){
            this.profiles.add(profile);
        }
       return this;
    }
    public User removeProfile(Profile profile) {
        if(profiles==null)
            profiles= new HashSet<>();
        if(profiles.contains(profile)){
            this.profiles.remove(profile);
        }
       return this;
    }
  public  boolean hasProfile(Profile profile){
        if(this.profiles!=null){
            return this.profiles.contains(profile);
        }
        return false;
  }
    public String getFirstName() {
        return firstName;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Profile> getProfiles() {
        return Collections.unmodifiableSet(profiles);
    }

    public String getLastName() {
        return lastName;
    }

    public Password getPassWord() {
        return passWord;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserId getId() {
        return id;
    }
}
