package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.exceptions.DuplicateException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;

import java.util.*;

@Repository
public class UserDao {
    private final HashMap<Long, User> userHashMap = new HashMap<>();

    private Long userId = 0L;
    public User addUser(User user) {
        if (userHashMap.entrySet().stream()
                .anyMatch(x -> Objects.equals(x.getValue().getEmail(), user.getEmail()))
        ) throw new DuplicateException();
        user.setId(++userId);
        userHashMap.put(user.getId(), user);
        return user;
    }

    public User getUser(Long userId) {

        if (userHashMap.containsKey(userId)) {
            return userHashMap.get(userId);
        } else {
            throw new NotFoundException();
        }
    }

    public User updateUser(User user, Long userId) {
        if (!userHashMap.containsKey(userId)) throw new NotFoundException();
        User updateUser = userHashMap.get(userId);

        if (user.getName() == null) {
            if (userHashMap.entrySet().stream()
                    .anyMatch(x -> Objects.equals(x.getValue().getEmail(), user.getEmail())) &&
                    !Objects.equals(updateUser.getEmail(), user.getEmail())
            ) throw new DuplicateException();
            updateUser.setEmail(user.getEmail());
        }
        if (user.getEmail() == null) updateUser.setName(user.getName());

        if (user.getEmail() != null && user.getName() != null) {
            updateUser.setName(user.getName());
            updateUser.setEmail(user.getEmail());
        }
        return updateUser;
    }

    public void deleteUser(Long userId) {
        if (userHashMap.containsKey(userId)) {
            userHashMap.remove(userId);
        } else {
            throw new NotFoundException();
        }
    }

    public List<User> getUsers() {
        return new ArrayList<>(new ArrayList<>(userHashMap.values()));
    }
}
