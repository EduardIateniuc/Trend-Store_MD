package md.donesk.trendshop.controller;

import md.donesk.trendshop.exception.ErrorResponse;
import md.donesk.trendshop.service.CsvImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/trend/import")
public class ImportController {

    private final CsvImportService csvImportService;

    public ImportController(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> importProducts(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importProductsFromCsv(file);
            return ResponseEntity.ok("Products imported successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Import failed", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("File processing failed", e.getMessage()));
        }
    }

    @PostMapping("/variants")
    public ResponseEntity<String> importVariants(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importVariantsFromCsv(file);
            return ResponseEntity.ok("Variants imported successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
        }
    }
}
