package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.exceptions.DuplicateException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        try {
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new DuplicateException();
        }
    }

    public User getUser(Long userId) {
        try {
            return userRepository.findById(userId)
                    .orElseThrow(NotFoundException::new);
        } catch (RuntimeException e) {
            throw new NotFoundException();
        }
    }

    public User updateUser(User user, Long userId) {
        User userFromBD = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        if (user.getEmail() != null) {
            userFromBD.setEmail(
                    user.getEmail()
            );
        }

        if (user.getName() != null) {
            userFromBD.setName(
                    user.getName()
            );
        }
        return userRepository.save(userFromBD);
    }


    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
