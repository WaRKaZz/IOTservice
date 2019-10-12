package kz.epam.iotservice.taglib;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CustomTag extends SimpleTagSupport {
    private static final String DOUBLE_SPACE = "<br><br>";

    @Override
    public void doTag() throws IOException {
        getJspContext().getOut().print(DOUBLE_SPACE);
    }
}
