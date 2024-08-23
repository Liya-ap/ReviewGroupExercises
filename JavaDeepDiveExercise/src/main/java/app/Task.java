package app;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
}
