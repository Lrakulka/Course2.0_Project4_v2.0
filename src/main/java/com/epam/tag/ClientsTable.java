package com.epam.tag;

import com.epam.model.Bill;
import com.epam.model.User;
import lombok.Setter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.io.IOException;
import java.util.List;


/**
 * @author asd
 * Handling tag clientTable.
 * Localization provided automatically by method getMessage.
 */
@Setter
public class ClientsTable extends RequestContextAwareTag {
    private static final long serialVersionUID = 8721266929256929800L;

    private List<User> clients;
    private CsrfToken csrf;

    public int doStartTagInternal() {
		StringBuilder tableBuilder = new StringBuilder(
				"<form name=ClientsTable action=/actionWithClientBill " +
						"method=post>" + "<table border=2 cellpadding=8>");
        if (csrf != null) {
            tableBuilder.append("<input type=hidden name=" + csrf.getParameterName()
                    + " value=" + csrf.getToken() + " />");
        }
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
                    tableBuilder.append("<td>" + getMessage("admin.label.deleted") + "</td>");
                } else {
                    tableBuilder.append("<td>" + (bill.getActive() ? getMessage("admin.label.unblocked")
                            : getMessage("admin.label.blocked")) + "</td>");
                }
                tableBuilder.append("<td><button name=actionAndBillId" +
                        " value=" + bill.getId() + (bill.getActive() ? "+block>"
                                + getMessage("button.block") : "+unblock>"
                                + getMessage("button.unblock"))
                        + "</button></td>");
                tableBuilder.append("<td><button name=actionAndBillId" +
                        " value=" + bill.getId() + (bill.getDeleted() ? "+undelete>"
                                + getMessage("button.undelete") : "+delete>"
                                + getMessage("button.delete"))
                        + "</button></td>");
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

    /**
     * Get localized message
     * @param code of the message
     * @return localized message
     */
	private String getMessage(String code) {
        return getRequestContext().getMessageSource().getMessage(
                code, null, getRequestContext().getLocale());
    }
}
