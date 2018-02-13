package eu.ase.taglib;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class TagError extends TagSupport {

private String mColor = "red";

public void setColor(String aColor) {
  mColor = aColor;
} // setColor

public int doStartTag() throws JspException {
  try {
    JspWriter out = pageContext.getOut();
    out.print("<font color=\"");
    out.print(mColor);
    out.print("\"><b>Error: ");
  } catch (IOException ex) {
    throw new JspTagException(ex.getMessage());
  }
  return EVAL_BODY_INCLUDE; // other return: SKIP_BODY
} // doStartTag()

public int doEndTag() throws JspException {
  try {
    JspWriter out = pageContext.getOut();
    out.println("</b></font><br/>");
  } catch (IOException ex) {
    throw new JspTagException(ex.getMessage());
  }
  return EVAL_PAGE; // other return: SKIP_PAGE
} // doEndTag()

} // TagError
