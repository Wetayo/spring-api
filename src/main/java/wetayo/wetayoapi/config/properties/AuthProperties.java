package wetayo.wetayoapi.config.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Component @EqualsAndHashCode
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private Map<String,String> apiKey;
    private String authorization;
}
