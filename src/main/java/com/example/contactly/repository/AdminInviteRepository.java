package com.example.contactly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.contactly.entity.AdminInvite;

public interface AdminInviteRepository extends JpaRepository<AdminInvite, Long> {
    AdminInvite findByInviteLink(String inviteLink);
}