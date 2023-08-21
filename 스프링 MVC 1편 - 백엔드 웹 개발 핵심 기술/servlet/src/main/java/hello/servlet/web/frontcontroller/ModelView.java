package hello.servlet.web.frontcontroller;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ModelView {

    private final String viewName;

    private final Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

}
