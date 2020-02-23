package com.can.app.swim.swimapp.repository;

import com.can.app.swim.swimapp.entity.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "*")
public interface ChildrenRepository extends JpaRepository<Children, Long> {


}
