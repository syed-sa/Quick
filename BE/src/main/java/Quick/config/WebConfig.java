package Quick.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;
import Quick.constants.AppConstants;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${basepath}")
    private String basePath;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add trailing slash - this is crucial!
        String imageLocation = "file:" + basePath + AppConstants.USER_DATA + AppConstants.IMAGE_FOLDER;
        if (!imageLocation.endsWith("/")) {
            imageLocation += "/";
        }
        
        System.out.println("📢 basePath = " + basePath);
        System.out.println("📂 Hosting static files from: " + imageLocation);
        
        // Verify the directory exists
        String directoryPath = basePath + AppConstants.USER_DATA + AppConstants.IMAGE_FOLDER;
        File imageDir = new File(directoryPath);
        if (imageDir.exists()) {
            System.out.println("✅ Image directory exists: " + imageDir.getAbsolutePath());
        } else {
            System.err.println("❌ Image directory does not exist: " + imageDir.getAbsolutePath());
        }
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imageLocation)
                .setCachePeriod(3600)
                .resourceChain(true);
    }
}