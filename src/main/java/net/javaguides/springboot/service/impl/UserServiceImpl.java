package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repositary.UserRepositary;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepositary userRepositary;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto)
    {
        //convert user JPA entity into userDTO
//        User user1 = UserMapper.mapToUser(userDto);
//        User user1 = modelMapper.map(userDto, User.class);
        Optional<User> optionalUser = userRepositary.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistException("Email Already exist");
        }
        User user1 = AutoUserMapper.MAPPER.mapToUser(userDto);
//        User savedUser = userRepositary.save(user1);

        User savedUser = userRepositary.save(user1);

        //convert userDTO into user JPA entity
//        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
//        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);
        return  savedUserDto;
    }




@Override
public UserDto getUserById(Long userId) {
        User user = userRepositary.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

//        return UserMapper.mapToUserDto(user);
//    return modelMapper.map(user, UserDto.class);
    return AutoUserMapper.MAPPER.mapToUserDto(user);
        }

@Override
        public List<UserDto> getAllUsers()
        {
                List<User> users = userRepositary.findAll();
//                return users.stream().map(UserMapper::mapToUserDto).
//                        collect(Collectors.toList());
//            return users.stream().map((user)->modelMapper.map(user, UserDto.class)).
//                    collect(Collectors.toList());
            return users.stream().map((user)->AutoUserMapper.MAPPER.mapToUserDto(user)).
                    collect(Collectors.toList());
        }

    @Override
    public UserDto updateUser(UserDto user) {

    User existingUser = userRepositary.findById(user.getId()).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", user.getId())
    );
    existingUser.setFirstName(user.getFirstName());
    existingUser.setLastName(user.getLastName());
    existingUser.setEmail(user.getEmail());
    User updatesUser = userRepositary.save(existingUser);
//        return UserMapper.mapToUserDto(updatesUser);
//        return modelMapper.map(updatesUser, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(updatesUser);
    }

    @Override
    public void deleteuser(Long userId) {

        User existingUser = userRepositary.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User","id", userId)
        );

        userRepositary.deleteById(userId);
    }
}
