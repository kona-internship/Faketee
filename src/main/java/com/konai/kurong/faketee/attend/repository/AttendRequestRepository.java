package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.AttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendRequestRepository extends JpaRepository<AttendRequest, Long>, QuerydslAtdReqRepository {
}
