package md.donesk.trendshop.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private final String message;
    private final String details;
}
