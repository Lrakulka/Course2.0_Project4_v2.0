package com.epam.tag;

import com.epam.model.Bill;
import com.epam.model.Card;
import lombok.Setter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.io.IOException;
import java.util.List;

/**
 * @author asd
 * Handling tag cardsTable
 * Localization provided automatically by method getMessage.
 */
@Setter
public class ClientTable extends RequestContextAwareTag {
    private static final long serialVersionUID = -3905900903200282870L;

    private List<Bill> bills;
    private CsrfToken csrf;

    @Override
    public int doStartTagInternal() {
        StringBuilder tableBuilder = new StringBuilder(
                "<table border=2 cellpadding=8>");
        int i;
        for (Bill bill : bills) {
            i = 0;
            if (bill.getCards().size() > 1) {
                tableBuilder.append("<tr><td rowspan=" +
                        bill.getCards().size() + ">" +
                        bill.getName() + "</td><td rowspan=" +
                        bill.getCards().size() + ">" + bill.getScore() + "</td><td rowspan=" +
                        bill.getCards().size() + ">" + (bill.getActive()
                                ? getMessage("client.label.unblocked")
                                : getMessage("client.label.blocked")) + "</td>");
            } else {
                tableBuilder.append("<tr><td>" + bill.getName() +
                        "</td><td rowspan=" +
                        bill.getCards().size() + ">" + bill.getScore() + "</td><td rowspan=" +
                        bill.getCards().size() + ">" + (bill.getActive()
                                ? getMessage("client.label.unblocked")
                                : getMessage("client.label.blocked")) + "</td>");
            }
            for (Card card : bill.getCards()) {
                if (i > 0) {
                    tableBuilder.append("<tr>");
                }
                tableBuilder.append("<td>" + card.getName() + "</td>");
                if (card.getDeleted()) {
                    tableBuilder.append("<td>" + getMessage("client.label.deleted") + "</td>");
                } else {
                    tableBuilder.append("<td>" + (card.getActive() 
                            ? getMessage("client.label.unblocked")
                            : getMessage("client.label.blocked")) + "</td>");
                }

                tableBuilder.append(
                        "<td><form name=ClientTable action=/actionWithClientCard method=post>");
                if (csrf != null) {
                    tableBuilder.append("<input type=hidden name=" + csrf.getParameterName()
                            + " value=" + csrf.getToken() + " />");
                }
                tableBuilder.append("<button name=actionAndCardId" +
                        " value=" + card.getId() + (card.getActive() ? "+block>"
                            + getMessage("client.button.block") : "+unblock>"
                            + getMessage("button.unblock"))
                        + "</button>");
                tableBuilder.append("</form></td>");

                tableBuilder.append(
                        "<td><form name=ClientTable action=/actionWithClientCard method=post>");
                if (csrf != null) {
                    tableBuilder.append("<input type=hidden name=" + csrf.getParameterName()
                            + " value=" + csrf.getToken() + " />");
                }
                tableBuilder.append("<button name=actionAndCardId" +
                        " value=" + card.getId() + (card.getDeleted() ? "+undelete>"
                            + getMessage("button.undelete") : "+delete>"
                            + getMessage("button.delete"))
                        + "</button>");
                tableBuilder.append("</form></td>");

                tableBuilder.append("<td><form name=fillBill action=/fillClientBill method=post>");
                if (csrf != null) {
                    tableBuilder.append("<input type=hidden name=" + csrf.getParameterName()
                            + " value=" + csrf.getToken() + " />");
                }
                tableBuilder.append("<p>" + getMessage("client.label.moneyName") +
                        ": <input size=10 type=number step=0.01 name=moneyCount /></p>" +
                        "<button name=billId value=" +
                        bill.getId() + ">" + getMessage("client.button.fill") +
                        "</button></form></td>" +
                        // next form
                        "<td><form name=sentMoney action=/sentMoney method=post>");
                if (csrf != null) {
                    tableBuilder.append("<input type=hidden name=" + csrf.getParameterName()
                            + " value=" + csrf.getToken() + " />");
                }
                tableBuilder.append("<input type=hidden name=nativeCardId" +
                "	value=" + card.getId() + " /><p>" +
                getMessage("client.label.cardName") + ": <input size=6 type=text name=cardName /></p><p>" +
                getMessage("client.label.password") + ": <input size=6 type=password name=passWord /></p><p>" +
                getMessage("client.label.moneyName") + ": <input size=10 type=number step=0.01 min = 0.01" +
                " name=moneyCount /></p><p><button name=billId value=" + bill.getId() + ">" +
                getMessage("client.button.sent") + "</button></p></form></td>");
                i++;
                if ((i > 0) && (i != bill.getCards().size())) {
                    tableBuilder.append("</tr>");
                }
            }
            tableBuilder.append("</tr>");
        }
        tableBuilder.append("</table>");
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
