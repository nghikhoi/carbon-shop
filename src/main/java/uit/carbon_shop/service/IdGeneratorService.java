package uit.carbon_shop.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {

    public long generateId() {
        return new Random().nextLong();
    }

}
