package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DefaultColor;
import com.hong.smartref.data.enumerate.DefaultUserName;
import com.hong.smartref.data.enumerate.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String nicknameColor;

    @Column(nullable = false)
    private boolean isPremium;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isValid;

    private String locationName;

    @Column(nullable = false)
    private int ticketCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodFavorite> foodFavoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FridgeUser> fridgeUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipeList = new ArrayList<>();

    public static User create(
            String email,
            String encodedPassword
    ) {
        User user = new User();
        user.email = email.toLowerCase();     // ⭐ 정규화
        user.password = encodedPassword;
        user.nickname = DefaultUserName.getRandomUserName();
        user.nicknameColor = DefaultColor.getRandomColor();
        user.locationName = null;
        user.role = Role.USER;

        // 기본 정책
        user.isPremium = false;
        user.isValid = false;
        user.ticketCount = 10;               // 회원가입 시 10장
        user.createdAt = LocalDateTime.now();

        return user;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}

