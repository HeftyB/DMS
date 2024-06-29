package com.heftyb.dms.users;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRoleId implements Serializable {
    private User user;
    private Role role;

    public UserRoleId() {
    }

    public UserRoleId(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId)) return false;
        UserRoleId that = (UserRoleId) o;
        return getUser().equals(that.getUser()) && getRole().equals(that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getRole());
    }
}
