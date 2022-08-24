package server.integration;

import common.FileDTO;
import common.FileError;
import common.LogInDetails;
import common.UserError;
import server.model.File;
import server.model.User;

import javax.persistence.*;
import java.util.List;

public class FileDAO {
    private final EntityManagerFactory factory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public FileDAO() {
        factory = Persistence.createEntityManagerFactory("factory");
    }

    private EntityManager beginTransaction() {
        EntityManager em = factory.createEntityManager();
        entityManagerThreadLocal.set(em);
        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        return em;
    }

    private void commitTransaction() throws FileError {
        try {
            entityManagerThreadLocal.get().getTransaction().commit();
        } catch(RollbackException e) {
            throw new FileError("File probably already exists", e);
        }
    }

    public void createFile(FileDTO file) throws FileError {
        try {
            EntityManager em = beginTransaction();
            em.persist(file);
            commitTransaction();
        }catch (Exception e){
            throw new FileError("Error creating file...", e);
        }
    }

    public void deleteFile(User user, String fileName) throws FileError {
        try {
            EntityManager em = beginTransaction();
            TypedQuery delFile = em.createNamedQuery("deleteFile", File.class);
            delFile.setParameter("fileName", fileName);
            delFile.setParameter("username", user.getUsername());
            //delFile.setParameter("password", user.getPassword());
            delFile.executeUpdate();

            if(getFile(fileName, user) != null)
                throw new FileError("Something went wrong during delete, file still exists...");
        } finally {
            commitTransaction();
        }
    }

    public User createUser(LogInDetails logInDetails) throws UserError, FileError {
        User user;
            if(searchUser(logInDetails.getUsername()) != null)
                throw new UserError("Account already exists...");
            EntityManager em = beginTransaction();
            user = new User(logInDetails.getUsername(), logInDetails.getPassword());
            em.persist(user);
            commitTransaction();
        return user;
    }

    public void deleteUser(User user) throws FileError {
        try {
            EntityManager em = beginTransaction();
            TypedQuery query = em.createNamedQuery("deleteUser", User.class);
            query.setParameter("username", user.getUsername());
            query.setParameter("password", user.getPassword());
            query.executeUpdate();
            em.detach(user);
        } finally {
            commitTransaction();
        }
    }

    public User checkLogin(LogInDetails logInDetails) throws UserError, FileError {
        User user;
        try {
            EntityManager em = beginTransaction();
            try {
                TypedQuery query = em.createNamedQuery("loginUser", User.class);
                query.setParameter("username", logInDetails.getUsername());
                query.setParameter("password", logInDetails.getPassword());
                user = (User) query.getSingleResult();
            } catch (NoResultException noSuchAccount) {
                throw new UserError("Wrong login details!", noSuchAccount);
            }
        } finally {
            commitTransaction();
        }
        return user;
    }

    public void togglePrivate(String fileName, User user) throws FileError {
        try {
            EntityManager em = beginTransaction();
            try {
                TypedQuery query = em.createNamedQuery("togglePrivate", User.class);
                query.setParameter("fileName", fileName);
                query.setParameter("username", user.getUsername());
                //query.setParameter("password", user.getPassword());
                query.executeUpdate();
            } catch (NoResultException noSuchAccount) {
                throw new FileError("Couldn't toggle private permission, maybe " + user.getUsername() + " doesn't have access to this file or the file might not exist.", noSuchAccount);
            }
        } finally {
            commitTransaction();
        }
    }

    public List listFiles(User user) {
        if(user == null){
            try {
                EntityManager em = beginTransaction();
                try {
                    return em.createNamedQuery("freeFindAllFilesAvailable", File.class).getResultList();
                } catch (NoResultException noSuchAccount) {
                    return null;
                }
            } finally {
               // commitTransaction();
            }
        }
        try {
            EntityManager em = beginTransaction();
            try {
                TypedQuery files = em.createNamedQuery("findAllFilesAvailable", File.class);
                files.setParameter("username", user.getUsername());
                //files.setParameter("password", user.getPassword());
                return files.getResultList();
            } catch (NoResultException noSuchAccount) {
                return null;
            }
        } finally {
           // commitTransaction();
        }
    }

    public File getFile(String fileName, User user) throws FileError {
            EntityManager em = beginTransaction();
            try {
                TypedQuery files = em.createNamedQuery("retrieveFile", File.class);
                files.setParameter("username", user.getUsername());
                files.setParameter("fileName", fileName);
                //files.setParameter("password", user.getPassword());
                return (File) files.getSingleResult();
            } catch (NoResultException noSuchAccount) {
                throw new FileError("Either server can't find file or user does not have permission to retrieve it.", noSuchAccount);
            } finally {
                commitTransaction();
            }

    }

    private User searchUser(String username) throws UserError, FileError {
        if (username == null) {
            throw new UserError("No username entered...");
        }
        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("checkUser", User.class)
                        .setParameter("username", username).getSingleResult();
            } catch (NoResultException noSuchAccount) {
                return null;
            }
        } finally {
            commitTransaction();
        }
    }
}
