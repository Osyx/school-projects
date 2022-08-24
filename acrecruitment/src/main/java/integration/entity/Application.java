package integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * The entity for info about the job applications.
 * Contains info about when it was registered and what status the application has.
 */
@Entity(name = "application")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private long applicationId;

    @Column(name = "app_date", nullable = false)
    private Date appDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    public Application() {  }

    public Application(Person person, Date appDate) {
        this.person = person;
        this.appDate = appDate;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
