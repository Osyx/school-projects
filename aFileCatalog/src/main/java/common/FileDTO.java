package common;

import server.model.User;

import java.io.Serializable;

/**
 * Specifies a read-only view of n account.
 */
public interface FileDTO extends Serializable {
    String getName();

    long getFileSize();

    String getOwner();

    boolean isPrivateAccess();

    boolean isWritePermission();

    java.io.File getContent();
}