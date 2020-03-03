package TemplateModel;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.HandlesTypes;

@Data
@Component
@SelfAnn(value = 6)
public class User {

    private Long id;

    private String name;

}
