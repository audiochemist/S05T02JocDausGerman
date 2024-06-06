package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.UserEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.UserRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.UserEntityServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityService implements UserEntityServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper userModelMapper;

    @Override
    public UserEntityDTO findById(String id) throws UserNotFound {
        return userRepository.findById(id).map(this::getUserDTOFrom).orElseThrow(() -> new UserNotFound("The user was not found"));
    }

    @Override
    public List<UserEntityDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new GameNotFound("No games found in the system");
        }
        return users.stream().map(this::getUserDTOFrom).collect(Collectors.toList());
    }

    private UserEntityDTO getUserDTOFrom(UserEntity user) {
        return userModelMapper.map(user, UserEntityDTO.class);
    }

    private UserEntity getUserEntityFrom(UserEntityDTO userDTO) {
        return userModelMapper.map(userDTO, UserEntity.class);
    }
}
