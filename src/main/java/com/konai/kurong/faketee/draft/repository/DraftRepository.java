package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftRepository extends JpaRepository<Draft, Long> {
}
