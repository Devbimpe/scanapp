package com.scanapp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

        @Column(name = "id")
        private  Long id ;

        @Column(name = "first_name")
        @NotEmpty(message = "Please provide your first name")
        private String firstName;


        @Column(name = "last_name")
        @NotEmpty(message = "Please provide your last name")
        private String lastName;

        @Column(name = "email", nullable = false, unique = true)
        @Email(message = "Please provide a valid e-mail")
        @NotEmpty(message = "Please provide an e-mail")
        private String email;


        @Column(name="password")
        private String password;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

        @Column(name="role")
        private String role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
}
