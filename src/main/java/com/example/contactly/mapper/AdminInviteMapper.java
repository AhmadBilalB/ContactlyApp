package com.example.contactly.mapper;

import com.example.contactly.dto.AdminInviteDTO;
import com.example.contactly.entity.AdminInvite;

public class AdminInviteMapper {

    public static AdminInvite toEntity(AdminInviteDTO adminInviteDTO) {
        return AdminInvite.builder()
                .inviteLink(adminInviteDTO.getInviteLink())
                .recipientEmail(adminInviteDTO.getRecipientEmail())
                .build();
    }

    public static AdminInviteDTO toDTO(AdminInvite adminInvite) {
        return AdminInviteDTO.builder()
                .inviteLink(adminInvite.getInviteLink())
                .recipientEmail(adminInvite.getRecipientEmail())
                .build();
    }
}
