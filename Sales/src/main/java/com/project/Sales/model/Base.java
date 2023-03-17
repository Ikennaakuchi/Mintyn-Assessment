package com.project.Sales.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Base implements Serializable {

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name="last_updated")
    private LocalDateTime updatedAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updatedAt(){
        this.updatedAt = LocalDateTime.now();
    }


    public abstract void setCreatedAt(LocalDateTime now);
    public abstract void setUpdatedAt(LocalDateTime now);
}
