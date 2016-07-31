/**
 * 
 */
package com.epam.tag;
import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import com.epam.model.Bill;
import com.epam.model.User;
import lombok.Setter;


/**
 * @author asd
 * Handling tag clientTable
 */
@Setter
public class ClientsTable extends TagSupport {
    private static final long serialVersionUID = 8721266929256929800L;

    private List<User> clients;
	private String blockButtonInfo;
	private String unBlockButtonInfo;
    private String deleteButtonInfo;
    private String unDeleteButtonInfo;
    private String parameterName;
    private String token;
    private String textBlocked;
    private String textUnBlocked;
    private String textDeleted;
    
    public int doStartTag() {
		StringBuilder tableBuilder = new StringBuilder(
				"<form name=\"ClientTable\" action=\"/actionWithClientBill\" " +
						"method=\"post\">" + "<table border=\"2\" cellpadding=\"8\">" +
						"<input type=\"hidden\" name=\"" + parameterName + "\"" +
						"	value=\"" + token + "\" />");
		int i;
		for (User user : clients) {
			i = 0;
			if (user.getBills().size() > 1) {
				tableBuilder.append("<tr><td rowspan=" +
						user.getBills().size() + ">" +
						user.getLastName() + " " + user.getFirstName() + "</td>");
			} else {
				tableBuilder.append("<tr><td>" + user.getFirstName() +
                        " " + user.getLastName() + "</td>");
			}
			for (Bill bill : user.getBills()) {
				if (i > 0) {
					tableBuilder.append("<tr>");
				}
				tableBuilder.append("<td>" + bill.getName() +
						"</td><td>" + bill.getScore() + "</td>");
                if (bill.getDeleted()) {
                    tableBuilder.append("<td>" + textDeleted + "</td>");
                } else {
                    tableBuilder.append("<td>" + (bill.getActive() ? textUnBlocked : textBlocked) + "</td>");
                }
				if (bill.getActive()) {
					tableBuilder.append("<td><button name=\"actionAndBillId\"" +
                            " value=\"" + bill.getId() + "+block\">" + blockButtonInfo + "</button></td>");
				} else {
					tableBuilder.append("<td><button name=\"actionAndBillId\"" +
                            " value=\"" + bill.getId() + "+unblock\">" + unBlockButtonInfo + "</button></td>");
				}
                if (bill.getDeleted()) {
                    tableBuilder.append("<td><button name=\"actionAndBillId\"" +
                            " value=\"" + bill.getId() + "+undelete\">" + unDeleteButtonInfo + "</button></td>");
                } else {
                    tableBuilder.append("<td><button name=\"actionAndBillId\"" +
                            " value=\"" + bill.getId() + "+delete\">" + deleteButtonInfo + "</button></td>");
                }
				i++;
				if ((i > 0) && (i != user.getBills().size())) {
					tableBuilder.append("</tr>");
				}
			}
			tableBuilder.append("</tr>");
		}
		tableBuilder.append("</table></form>");
		try {
			pageContext.getOut().write(tableBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
