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
    private String csrfInput;
    
    @Override
    public int doStartTagInternal() {
        csrfInput = "";
        StringBuilder tableBuilder = new StringBuilder("<table border=2 cellpadding=8>");
        int i;
        for (Bill bill : bills) {
            i = 0;
            if (bill.getCards().size() > 1) {
                tableBuilder.append("<tr><td rowspan=")
                        .append(bill.getCards().size()).append(">")
                        .append(bill.getName()).append("</td><td rowspan=")
                        .append(bill.getCards().size()).append(">")
                        .append(bill.getScore()).append("</td><td rowspan=")
                        .append(bill.getCards().size()).append(">")
                        .append(bill.getActive()
                            ? "<form name=blockBill action=/blockBill " +
                              "method=post><button name=billId" +
                              " value=" + bill.getId() + " >" +
                                getMessage("button.block") + "</button>" +
                                csrfToken() +"</form>"
                            : getMessage("client.label.blocked"))
                        .append( "</td>");
            } else {
                tableBuilder.append("<tr><td>").append( bill.getName())
                        .append("</td><td rowspan=").append(bill.getCards().size()).append(">")
                        .append(bill.getScore()).append("</td><td rowspan=")
                        .append(bill.getCards().size()).append( ">")
                        .append(bill.getActive()
                            ? "<form name=blockBill action=/blockBill " +
                              "method=post><button name=billId" +
                              " value=" + bill.getId() + " >" +
                                getMessage("button.block") + "</button>" +
                                csrfToken() + "</form>"
                            : getMessage("client.label.blocked"))
                        .append( "</td>");
            }
            for (Card card : bill.getCards()) {
                if (i > 0) {
                    tableBuilder.append("<tr>");
                }
                tableBuilder.append("<td>").append(card.getName()).append( "</td>");
                if (card.getDeleted()) {
                    tableBuilder.append("<td>").append(getMessage("client.label.deleted"))
                            .append( "</td>");
                } else {
                    tableBuilder.append("<td>").append((card.getActive()
                                ? getMessage("client.label.unblocked")
                                : getMessage("client.label.blocked")))
                            .append( "</td>");
                }
                tableBuilder
                        .append("<td><form name=ClientTable")
                        .append(" action=/actionWithClientCard method=post>")
                        .append(csrfToken())
                        .append("<button name=actionAndCardId")
                        .append(!bill.getActive() ? " disabled" : " ")
                        .append(" value=").append(card.getId())
                        .append(card.getActive()
                            ? "+block>" + getMessage("client.button.block")
                            : "+unblock>" + getMessage("button.unblock"))
                        .append( "</button>").append("</form></td>");

                tableBuilder.append(
                        "<td><form name=ClientTable action=/actionWithClientCard method=post>")
                        .append(csrfToken())
                        .append("<button name=actionAndCardId")
                        .append(!bill.getActive() ? " disabled " : "")
                        .append("value=").append(card.getId())
                        .append(card.getDeleted()
                                    ? "+undelete>" + getMessage("button.undelete")
                                    : "+delete>" + getMessage("button.delete"))
                        .append( "</button>").append("</form></td>");

                tableBuilder.append("<td><form name=fillBill action=/fillClientBill method=post>")
                        .append(csrfToken())
                        .append("<p>").append( getMessage("client.label.moneyName"))
                        .append(": <input size=10 type=number name=moneyCount ")
                        .append(" step=").append(getMessage("min.step"))
                        .append(" min=").append(getMessage("min.step"))
                        .append(" /></p>")
                        .append("<button name=billId value=").append(bill.getId())
                        .append((!bill.getActive() || !card.getActive())
                                ? " disabled " : "")
                        .append( ">").append(getMessage("client.button.fill"))
                        .append("</button></form></td>");

                tableBuilder.append("<td><form name=sentMoney action=/sentMoney method=post>")
                        .append(csrfToken())
                        .append("<input type=hidden name=nativeCardId").append(" value=")
                        .append(card.getId()).append(" /><p>")
                        .append(getMessage("client.label.cardName"))
                        .append(": <input size=6 type=text name=cardName /></p><p>")
                        .append(getMessage("client.label.password"))
                        .append(": <input size=6 type=password name=passWord /></p><p>")
                        .append(getMessage("client.label.moneyName"))
                        .append(": <input size=10 type=number name=moneyCount ")
                        .append(" step=").append(getMessage("min.step"))
                        .append(" min=").append(getMessage("min.step"))
                        .append(" /></p>")
                        .append("<p><button name=billId value=")
                        .append(bill.getId()).append((!bill.getActive() || !card.getActive())
                                ? " disabled " : " ")
                        .append( ">").append(getMessage("client.button.sent"))
                        .append( "</button></p></form></td>");
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
     * Create hidden input with csrf token
     * @return if csrf not null return hidden input with csrf token if also empty line
     */
    private String csrfToken() {
        if (csrf != null && csrfInput.isEmpty()) {
            csrfInput = "<input type=hidden name=" +
                    csrf.getParameterName() + " value=" +
                    csrf.getToken() + " />";
        }
        return csrfInput;
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
