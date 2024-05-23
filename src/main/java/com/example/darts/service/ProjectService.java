package com.example.darts.service;

import com.example.darts.model.binding.ProjectCreateBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.Project;
import com.example.darts.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final CloudinaryService cloudinaryService;
    public void post(ProjectCreateBindingModel bindingModel, Account account) {
        String photoUrl = null;
        if(bindingModel.getPhoto() != null){
            photoUrl = cloudinaryService.uploadImage(bindingModel.getPhoto());
        }

        repository.save(new Project(bindingModel, account, photoUrl));
    }

    public Project getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found!"));
    }
}
