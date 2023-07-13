package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemDao itemDao;

    public ItemDto addItem(Long id, ItemDto itemDto) {
        return itemDao.addItem(id, itemDto);
    }

    public ItemDto updateItem(Long id, ItemDto itemDto, Long itemId) {
        return itemDao.updateItem(id, itemDto, itemId);
    }

    public ItemDto getItem(Long itemId) {
        return itemDao.getItem(itemId);
    }

    public List<ItemDto> getItems(Long id) {
        return itemDao.getItems(id);
    }

    public List<ItemDto> searchItems(String text) {
        if (text.isEmpty()) return List.of();
        return itemDao.searchItems(text);
    }
}
