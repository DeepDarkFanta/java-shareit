package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ItemDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    public static ItemDto toItemDto(Item item) {
        return new ItemDto()
                .setAvailable(item.getAvailable())
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription());
    }
}
