package com.can.app.swim.swimapp.controllers;

import com.can.app.swim.swimapp.entity.Children;
import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.repository.ChildrenRepository;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.can.app.swim.swimapp.helpers.ExceptionsUtil.throwRuntimeException;

@RestController
@RequestMapping("/api/childrens")
public class ChildrenController {

    private ChildrenRepository childrenRepository;
    private UserRepository userRepository;

    @Autowired
    public ChildrenController(ChildrenRepository childrenRepository,
                              UserRepository userRepository){
        this.childrenRepository = childrenRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/parent/{parentId}")
    public Children addChildren2(@RequestBody Children children,
                                 @PathVariable("parentId") Long parentId){
        User parent = userRepository.findById(parentId)
                .orElseThrow(throwRuntimeException("Parent not found!"));
        children.setParent(parent);
        childrenRepository.save(children);
        return children;
    }

}
