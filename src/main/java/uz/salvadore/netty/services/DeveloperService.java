package uz.salvadore.netty.services;

import uz.salvadore.netty.models.Developer;

import java.util.List;

public interface DeveloperService {

    List<Developer> findAll();

}
