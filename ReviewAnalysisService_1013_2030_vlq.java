// 代码生成时间: 2025-10-13 20:30:37
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

// 评价分析系统服务组件
@Service
public class ReviewAnalysisService {

    // 注入评价存储库
    @Autowired
    private ReviewRepository reviewRepository;

    // 获取所有评价
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 添加评价
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    // 评价分析方法
    public List<ReviewAnalysisResult> analyzeReviews() {
        // 获取所有评价并分析
        return getAllReviews().stream()
            .collect(Collectors.groupingBy(review -> review.getRating()))
            .entrySet().stream()
            .map(entry -> new ReviewAnalysisResult(
                entry.getKey(),
                entry.getValue().size()
            ))
            .collect(Collectors.toList());
    }

    // 内部类：评价分析结果
    public static class ReviewAnalysisResult {
        private final int rating;
        private final int count;

        public ReviewAnalysisResult(int rating, int count) {
            this.rating = rating;
            this.count = count;
        }

        // getter方法
        public int getRating() {
            return rating;
        }

        public int getCount() {
            return count;
        }
    }
}
