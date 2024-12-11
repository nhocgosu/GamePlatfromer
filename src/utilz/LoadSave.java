package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

    // Phương thức tải hình ảnh từ các tài nguyên đã định nghĩa sẵn
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        // Lấy InputStream cho tài nguyên theo tên tệp được định nghĩa trong class
        try (InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName)) {  // Sử dụng tên tệp đã cho

            if (is == null) {
                System.out.println("Resource not found: " + fileName);
                return null;  // Trả về null nếu không tìm thấy tài nguyên
            }

            // Đọc hình ảnh từ InputStream
            img = ImageIO.read(is);
            if (img == null) {
                System.out.println("Failed to load image: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    // Phương thức tải dữ liệu level từ hình ảnh
    public static int[][] GetLevelData() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        // Lấy hình ảnh của dữ liệu level từ file
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);  // Gọi phương thức GetSpriteAtlas với tên file của dữ liệu level

        if (img == null) {
            System.out.println("Failed to load level data.");
            return lvlData;  // Trả về mảng trống nếu không thể tải dữ liệu
        }

        // Xử lý hình ảnh để lấy dữ liệu từ pixel
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();  // Lấy giá trị màu đỏ (color.getRed() sẽ giúp lấy giá trị độ sáng)
                if (value >= 48) {
                    value = 0;  // Gán giá trị = 0 nếu độ sáng lớn hơn hoặc bằng 48
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;  // Trả về mảng dữ liệu
    }
}

