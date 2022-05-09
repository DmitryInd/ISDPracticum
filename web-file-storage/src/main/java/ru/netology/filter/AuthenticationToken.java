package ru.netology.filter;

import java.util.Objects;

public class AuthenticationToken {
    private String name;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationToken that = (AuthenticationToken) o;
        return Objects.equals(name, that.name) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    public AuthenticationToken(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public AuthenticationToken() {
        this.changeTo(getAnonymousToken());
    }

    public static AuthenticationToken getAnonymousToken() {
        return new AuthenticationToken("Anonymous", "none");
    }

    public boolean isAnonymous() {
        return Objects.equals(this, getAnonymousToken());
    }

    public void changeTo(AuthenticationToken token) {
        this.name = token.getName();
        this.role = token.getRole();
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}
