package model;

import common.*;
import integration.Integration;
import integration.entity.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class which takes the calls from the controller regarding the job applications and then
 * handles the logic to convert this to a format that the integration layer can understand.
 */
@Transactional(value = Transactional.TxType.MANDATORY)
public class JobApplication {

    private static final Logger LOG = Logger.getLogger(Integration.class.getName());
    private final Integration integration = new Integration();

    /**
     * Fetches all job applications.
     * @param lang the language for which we want the return values in.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> getJobApplications(String lang) throws SystemException {
        int numberOfApplications = 0;
        try {
            List<JobApplicationDTO> jobApplications = new ArrayList<>();
            List<Person> personList = integration.getPersonsByRole("applicant");
            for (Person person : personList) {
                List<ExperienceDTO> experiences = createExperienceDTOs(person, lang);
                List<AvailabilityDTO> availabilities = createAvailabilityDTOs(person);
                Application application = person.getApplication();
                ApplicationDTO applicationDTO = createApplicationDTO(application, lang);
                PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);
                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                        personPublicDTO,
                        availabilities,
                        experiences,
                        applicationDTO
                );
                jobApplications.add(jobApplicationDTO);
                if(numberOfApplications++ >= 1000)
                    return jobApplications;
            }
            return jobApplications;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
        }
    }

    /**
     * Fetches all job applications submitted by persons with a certain name.
     * @param personDTO A DTO containing the name that we want the applicants to have.
     * @param lang the language for which we want the return values in.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> getJobApplicationsByName(PersonDTO personDTO, String lang) throws SystemException {
        int numberOfApplications = 0;
        try {
            List<JobApplicationDTO> jobApplications = new ArrayList<>();
            List<Person> personList = integration.getPersonsByRole("applicant");
            for (Person person : personList) {
                if(!personDTO.getName().equals(person.getName()))
                    continue;
                else
                if(personDTO.getSurname() != null && !personDTO.getSurname().equals(person.getSurname()))
                    continue;
                List<ExperienceDTO> experiences = createExperienceDTOs(person, lang);
                List<AvailabilityDTO> availabilities = createAvailabilityDTOs(person);
                Application application = person.getApplication();
                ApplicationDTO applicationDTO = createApplicationDTO(application, lang);
                PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);
                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                        personPublicDTO,
                        availabilities,
                        experiences,
                        applicationDTO
                );
                jobApplications.add(jobApplicationDTO);
                if(numberOfApplications++ >= 1000)
                    return jobApplications;
            }
            return jobApplications;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
        }
    }

    /**
     * Fetches all job applications that has a certain experience.
     * @param experienceDTO An DTO containing the experience that we want the applicants to have.
     * @param lang the language for which we want the return values in.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> getJobApplicationsByExperience(ExperienceDTO experienceDTO, String lang) throws SystemException {
        int numberOfApplications = 0;
        try {
            List<JobApplicationDTO> jobApplications = new ArrayList<>();
            List<Person> personList = integration.getPersonsByRole("applicant");
            for (Person person : personList) {
                boolean hasExperience = false;
                List<ExperienceDTO> experiences = new ArrayList<>();
                switch(lang) {
                    case "sv":
                        for (PersonExperience personExperience : person.getPersonExperiences()) {
                            if(personExperience.getExperience().getName_sv().equals(Util.capitalize(experienceDTO.getName())))
                                hasExperience = true;
                            experiences.add(new ExperienceDTO(
                                    personExperience.getExperience().getName_sv(),
                                    personExperience.getYearsOfExperience()
                            ));
                        }
                        break;
                    default:
                        for (PersonExperience personExperience : person.getPersonExperiences()) {
                            if(personExperience.getExperience().getName_en().equals(experienceDTO.getName()))
                                hasExperience = true;
                            experiences.add(new ExperienceDTO(
                                    personExperience.getExperience().getName_en(),
                                    personExperience.getYearsOfExperience()
                            ));
                        }
                        break;
                }
                if(!hasExperience)
                    continue;

                List<AvailabilityDTO> availabilities = createAvailabilityDTOs(person);
                Application application = person.getApplication();
                ApplicationDTO applicationDTO = createApplicationDTO(application, lang);
                PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);
                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                        personPublicDTO,
                        availabilities,
                        experiences,
                        applicationDTO
                );
                jobApplications.add(jobApplicationDTO);
                if(numberOfApplications++ >= 1000)
                    return jobApplications;
            }
            return jobApplications;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
        }
    }

    /**
     * Fetches all job applications by the date the application was registered.
     * @param appDTO an applicationDTO containing the date that we want the applicants to have.
     * @param lang the language for which we want the return values in.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> getJobApplicationsByAppDate(ApplicationDTO appDTO, String lang) throws SystemException {
        int numberOfApplications = 0;
        try {
            List<JobApplicationDTO> jobApplications = new ArrayList<>();
            List<Person> personList = integration.getPersonsByRole("applicant");
            for (Person person : personList) {
                Application application = person.getApplication();
                if(!application.getAppDate().toString().equals(appDTO.getDate()))
                    continue;
                ApplicationDTO applicationDTO = createApplicationDTO(application, lang);
                List<ExperienceDTO> experiences = createExperienceDTOs(person, lang);
                List<AvailabilityDTO> availabilities = createAvailabilityDTOs(person);
                PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);
                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                        personPublicDTO,
                        availabilities,
                        experiences,
                        applicationDTO
                );
                jobApplications.add(jobApplicationDTO);
                if(numberOfApplications++ >= 1000)
                    return jobApplications;
            }
            return jobApplications;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
        }
    }

    /**
     * Fetches all job applications by availability.
     * @param availabilityDTO the availability that we want the applicants to have.
     * @param lang the language for which we want the return values in.
     * @return All job applications in a <code>List</code> of JobApplicationDTOs.
     * @throws SystemException in case something goes from when fetching from the database.
     */
    public List<JobApplicationDTO> getJobApplicationsByAvailability(AvailabilityDTO availabilityDTO, String lang) throws SystemException {
        int numberOfApplications = 0;
        try {
            List<JobApplicationDTO> jobApplications = new ArrayList<>();
            List<Person> personList = integration.getPersonsByRole("applicant");
            for (Person person : personList) {
                boolean isAvailable = false;
                List<AvailabilityDTO> availabilities = new ArrayList<>();
                for (Availability availability : person.getAvailabilities()) {
                    if(availabilityDTO.getToDate() != null){
                        if((availability.getToDate().before(Date.valueOf(availabilityDTO.getToDate()))
                                || availability.getToDate().equals(Date.valueOf(availabilityDTO.getToDate())))
                                && (availability.getFromDate().after(Date.valueOf(availabilityDTO.getFromDate()))
                                || availability.getFromDate().equals(Date.valueOf(availabilityDTO.getFromDate())))
                        )
                            isAvailable = true;
                    } else {
                        if(availability.getFromDate().equals(Date.valueOf(availabilityDTO.getFromDate()))
                        || availability.getFromDate().after(Date.valueOf(availabilityDTO.getFromDate())))
                            isAvailable = true;
                    }
                    availabilities.add(new AvailabilityDTO(
                            availability.getFromDate(),
                            availability.getToDate()
                    ));
                }
                if(!isAvailable)
                    continue;

                List<ExperienceDTO> experiences = createExperienceDTOs(person, lang);
                Application application = person.getApplication();
                ApplicationDTO applicationDTO = createApplicationDTO(application, lang);
                PersonPublicDTO personPublicDTO = new PersonPublicDTO(person);
                JobApplicationDTO jobApplicationDTO = new JobApplicationDTO(
                        personPublicDTO,
                        availabilities,
                        experiences,
                        applicationDTO
                );
                jobApplications.add(jobApplicationDTO);
                if(numberOfApplications++ >= 1000)
                    return jobApplications;
            }
            return jobApplications;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            throw new SystemException(Messages.SYSTEM_ERROR.name(), e.getMessage());
        }
    }

    /**
     * Fetches the available experiences from the database.
     * @param lang the language for which we want the return values in.
     * @return A list of experienceDTOs containing all experiences available.
     * @throws SystemException when an error occurs during the fetch of experiences.
     */
    public List<String> getExperiences(String lang) throws SystemException {
        return integration.getExperiences(lang);
    }

    /**
     * Registers a new job application.
     * @param personDTO The DTO of the person to be the applicant.
     * @param userDTO The DTO of the user registering.
     * @param experienceDTOs The list of experiences that the applicant has.
     * @param availabilityDTOs The list of dates that the applicant is available.
     * @param applicationDTO The dates of when the applicant registered the applications.
     * @throws SystemException in case that there is an error when registering the application to the database.
     */
    public void registerJobApplication(PersonDTO personDTO, UserDTO userDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        integration.registerJobApplication(personDTO, userDTO, experienceDTOs, availabilityDTOs, applicationDTO);
    }

    /**
     * Register a new job application, made for the REST endpoints where a user isn't necessary.
     * @param personDTO The applicant that is applying for a job.
     * @param experienceDTOs The previous experiences that the applicant has.
     * @param availabilityDTOs The time slots where the applicant can work.
     * @param applicationDTO The details concerning this application, e.g. registration date.
     * @throws SystemException in case of an error during registration.
     */
    public void registerRESTJobApplication(PersonDTO personDTO, List<ExperienceDTO> experienceDTOs, List<AvailabilityDTO> availabilityDTOs, ApplicationDTO applicationDTO) throws SystemException {
        integration.registerRESTJobApplication(personDTO, experienceDTOs, availabilityDTOs, applicationDTO);
    }

    /**
     * Accept or decline a job application.
     * @param applicationDTO A DTO encapsulating the job application to be changed and has the <code>accepted</code> value changed to the new value.
     * @throws SystemException in case of an error during update of the application status.
     */
    public void acceptOrDeclineJobApplication(ApplicationDTO applicationDTO) throws SystemException {
        integration.acceptOrDeclineJobApplication(applicationDTO);
    }

    // Private helper functions

    private List<ExperienceDTO> createExperienceDTOs(Person person, String lang) {
        List<ExperienceDTO> experiences = new ArrayList<>();
        switch(lang) {
            case "sv":
                for (PersonExperience personExperience : person.getPersonExperiences()) {
                    experiences.add(new ExperienceDTO(
                            personExperience.getExperience().getName_sv(),
                            personExperience.getYearsOfExperience()
                    ));
                }
                break;
            default:
                for (PersonExperience personExperience : person.getPersonExperiences()) {
                    experiences.add(new ExperienceDTO(
                            personExperience.getExperience().getName_en(),
                            personExperience.getYearsOfExperience()
                    ));
                }
                break;
        }
        return experiences;
    }

    private List<AvailabilityDTO> createAvailabilityDTOs(Person person) {
        List<AvailabilityDTO> availabilities = new ArrayList<>();
        for (Availability availability : person.getAvailabilities()) {
            if(availability.getToDate() == null) {
                availabilities.add(new AvailabilityDTO(
                        availability.getFromDate()
                ));
            } else {
                availabilities.add(new AvailabilityDTO(
                        availability.getFromDate(),
                        availability.getToDate()
                ));
            }
        }
        return availabilities;
    }

    private ApplicationDTO createApplicationDTO(Application application, String lang) {
        String accepted;
        switch(lang) {
            case "sv":
                accepted = "Under övervägande";
                if (application.isAccepted() != null)
                    accepted = application.isAccepted() ? "Accepterad" : "Avvisad";
                break;
            default:
                accepted = "Under consideration";
                if (application.isAccepted() != null)
                    accepted = application.isAccepted() ? "Accepted" : "Rejected";
                break;
        }
        return new ApplicationDTO(
                application.getApplicationId(),
                application.getAppDate(),
                accepted
        );
    }
}
