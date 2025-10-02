// 代码生成时间: 2025-10-02 23:00:41
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ColorPickerController {

    private static final String[] AVAILABLE_COLORS = {"red", "green", "blue", "yellow", "purple"};

    @GetMapping("/color")
    public ResponseEntity<String> getSelectedColor(@RequestParam String color) {
        // Validate input color
        if (color == null || !isValidColor(color)) {
            // If color is not valid, throw an exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided color is not valid.");
        }

        // Return the selected color
        return ResponseEntity.ok("Selected color: " + color);
    }

    /**
     * Checks if a given color is one of the available colors.
     *
     * @param color The color to check.
     * @return true if the color is valid, false otherwise.
     */
    private boolean isValidColor(String color) {
        for (String availableColor : AVAILABLE_COLORS) {
            if (availableColor.equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }
}