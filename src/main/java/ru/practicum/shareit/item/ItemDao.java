package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemDao {
    private final HashMap<Long, Item> itemHashMap = new HashMap<>();
    private Long id = 0L;

    @Autowired
    private final UserDao userDao;
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName())
                .setAvailable(itemDto.getAvailable())
                .setOwner(userDao.getUser(userId))
                .setDescription(itemDto.getDescription())
                .setId(++id);

        itemHashMap.put(item.getId(), item);
        return itemDto.setId(item.getId());
    }

    public ItemDto updateItem(Long id, ItemDto itemDto, Long itemId) {
        Item item = itemHashMap.get(itemId);
        if (!Objects.equals(item.getOwner().getId(), id)) throw new NotFoundException();

        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        itemHashMap.put(itemId, item);
        return Item.toItemDto(item);
    }

    public ItemDto getItem(Long itemId) {
        Item item = itemHashMap.get(itemId);
        if (item == null) throw new NotFoundException();
        return Item.toItemDto(item);
    }

    public List<ItemDto> getItems(Long id) {
        return itemHashMap.values().stream()
                .filter(x -> Objects.equals(x.getOwner().getId(), id))
                .map(Item::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String text) {
        return itemHashMap.values().stream()
                .filter(x -> x.getDescription().toLowerCase().contains(text.toLowerCase()) ||
                        x.getName().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .map(Item::toItemDto).
                collect(Collectors.toList());
    }
}
