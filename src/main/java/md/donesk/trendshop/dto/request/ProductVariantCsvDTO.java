package md.donesk.trendshop.dto.request;


import lombok.Data;

@Data
public class ProductVariantCsvDTO {
    private String productCode;
    private String size;
    private String color;
    private Integer stockLevel;
}