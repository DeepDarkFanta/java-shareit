package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    //private final ItemDao itemDao;

    private final ItemRepository itemRepository;

    public ItemDto addItem(Long id, ItemDto itemDto) {
        try {
            return ItemDto.toItemDto(
                    itemRepository.save(
                            new Item()
                                    .setOwner(id)
                                    .setAvailable(itemDto.getAvailable())
                                    .setName(itemDto.getName())
                                    .setDescription(itemDto.getDescription())
                    )
            );
        } catch (RuntimeException e) {
            throw new NotFoundException();
        }
    }

    public ItemDto updateItem(Long ownerId, ItemDto itemDto, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundException::new);
        if (!Objects.equals(item.getOwner(), ownerId)) throw new NotFoundException();

        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());

        return ItemDto.toItemDto(
                itemRepository.save(item)
        );
    }

    public ItemDto getItem(Long itemId) {
        return ItemDto.toItemDto(
                itemRepository.findById(itemId)
                        .orElseThrow(NotFoundException::new)
        );
    }

    public List<ItemDto> getItems(Long id) {
        return itemRepository.findAllByOwner(id).stream()
                .map(ItemDto::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String text) {
        if (text.isBlank()) return List.of();
        System.out.println("tezt: " + text);
        return itemRepository.search(text).stream()
                .map(ItemDto::toItemDto)
                .collect(Collectors.toList());
    }
}
