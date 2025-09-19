// 代码生成时间: 2025-09-19 13:38:23
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class ImageResizerComponent {

    private static final String TEMP_DIRECTORY = "temp/";

    public void resizeImage(MultipartFile file, int width, int height) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        // 保存临时文件
        File tempFile = saveTempFile(file);

        // 读取图片
        BufferedImage image = ImageIO.read(tempFile);

        // 检查图片是否为空
        if (image == null) {
            throw new IllegalArgumentException("The file is not a valid image.");
        }

        // 调整图片尺寸
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        resizedImage.getGraphics().drawImage(image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);

        // 保存调整后的图片
        File resizedFile = new File(TEMP_DIRECTORY + file.getOriginalFilename());
        ImageIO.write(resizedImage, "jpg", resizedFile);

        // 删除临时文件
        tempFile.delete();
    }

    private File saveTempFile(MultipartFile file) throws IOException {
        // 创建临时目录
        File tempDir = new File(TEMP_DIRECTORY);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // 保存文件
        File tempFile = new File(TEMP_DIRECTORY + file.getOriginalFilename());
        try (OutputStream out = new FileOutputStream(tempFile)) {
            out.write(file.getBytes());
        }

        return tempFile;
    }
}
