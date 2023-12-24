package Socialmedia.UserAuthenticationService.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U>{

    @CreatedBy
    public U createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDate;

    @LastModifiedBy
    public U lasModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastModifiedDate;
}
