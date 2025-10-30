package md.donesk.trendshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderPageResponse {
    private List<OrderResponse> orders;
    private int totalPages;
    private long totalItems;
    private int currentPage;
}
