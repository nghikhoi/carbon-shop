package uit.carbon_shop.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("uit.carbon_shop")
public class CarbonShopApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CarbonShopApplication.class, args);
    }

}
