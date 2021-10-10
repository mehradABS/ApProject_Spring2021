package events.auth;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.auth.RegistrationEventVisitor;
import responses.Response;

import java.time.LocalDate;

public class RegistrationEvent extends Event {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate birth;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((RegistrationEventVisitor)eventVisitor).checkEvent(this);
    }

    @Override
    public String getVisitorType() {
        return "RegistrationEventVisitor";
    }
}
