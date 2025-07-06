package Quick.service.BusinessRegistry;
import java.util.List;
import Quick.dto.RegisterBusinessDto;
import Quick.dto.ServiceDto;

public interface BuisnessRegistry {

    void registerBusiness(RegisterBusinessDto registerServices);

    List<ServiceDto> getServicesByCategory(String categoryName, String postalCode);

    List<String> getImages(long service);

    List<ServiceDto> getServiceByUserId(Long userId);

    void updateService(ServiceDto service);

}