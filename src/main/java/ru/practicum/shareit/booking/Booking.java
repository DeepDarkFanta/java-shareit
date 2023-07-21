package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.booking.enums.StatusBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Entity
@Data
public class Booking {

    @Id
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @Column
    private Long item;

    @Column
    private Long booker;

    @Enumerated(EnumType.STRING)
    private StatusBooking status;

}
