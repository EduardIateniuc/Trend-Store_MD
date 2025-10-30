package md.donesk.trendshop.dto.response;
import lombok.Data;


import java.util.List;


@Data
public class ProductPageResponse {
    private List<?> products;
    private long totalElements;
    private int totalPages;
    private int currentPage;

    public ProductPageResponse(List<?> products, long totalElements,
                               int totalPages, int currentPage) {
        this.products = products;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }


}
