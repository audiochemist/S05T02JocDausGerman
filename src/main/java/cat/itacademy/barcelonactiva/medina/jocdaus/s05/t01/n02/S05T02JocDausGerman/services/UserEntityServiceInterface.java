package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.UserEntityDTO;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.UserNotFound;

import java.util.List;

public interface UserEntityServiceInterface {

    UserEntityDTO findById(String id) throws UserNotFound;
    List<UserEntityDTO> findAll();
}
