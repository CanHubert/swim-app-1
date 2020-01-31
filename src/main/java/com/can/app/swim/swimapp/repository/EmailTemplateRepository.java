package com.can.app.swim.swimapp.repository;

import com.can.app.swim.swimapp.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    EmailTemplate findByName(String name);
}
