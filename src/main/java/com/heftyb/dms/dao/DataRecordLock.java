package com.heftyb.dms.dao;

import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class DataRecordLock {

    /**
     * Boolean representing lock on database record
     */
    protected boolean isLocked;

    /**
     * Id of user if isLocked=true, else null value
     */
    protected long lockedBy;

    /**
     * Randomly generated UUID to verify integrity of
     * record lock. To be compared before updating records.
     */
    protected UUID lockId;
}
