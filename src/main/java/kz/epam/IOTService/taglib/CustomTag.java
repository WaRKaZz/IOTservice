package kz.epam.IOTService.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CustomTag extends SimpleTagSupport {
    private static final String DOUBLE_SPACE = "<br><br>";
    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(DOUBLE_SPACE);
    }
}
