package com.onlib.core.dto;

import com.onlib.core.model.LibraryUser;

public class UserWithoutPasswordDto implements Comparable<UserWithoutPasswordDto> {
    public Long id;
    public String name;

    public UserWithoutPasswordDto() {};

    public UserWithoutPasswordDto(LibraryUser user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    @Override
    public int compareTo(UserWithoutPasswordDto user) {
        return id.compareTo(user.id);
    }
}
