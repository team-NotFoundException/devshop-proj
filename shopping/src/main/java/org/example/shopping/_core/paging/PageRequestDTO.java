package org.example.shopping._core.paging;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageRequestDTO {

    @Min(0)
    private int page = 0;

    @Min(0)
    @Max(50)
    private int size = 10;

    private String sort = "id";
    private Sort.Direction direction = Sort.Direction.DESC;

    public PageRequest toPageable() {
        return PageRequest.of(page, size, Sort.by(direction, sort));
    }
}
