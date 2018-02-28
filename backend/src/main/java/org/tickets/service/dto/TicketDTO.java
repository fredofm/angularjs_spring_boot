package org.tickets.service.dto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.springframework.hateoas.ResourceSupport;
import org.tickets.domain.Ticket;
import org.tickets.rest.TicketManagerController;

public class TicketDTO extends ResourceSupport {

	private Long ticketid;
    private String codenumber;
	private String company;

    @JsonCreator
    public TicketDTO() {

    }
	
	public TicketDTO(Ticket ticket) {

        this.ticketid = ticket.getId();
        this.codenumber = ticket.getCodenumber();
        this.company = ticket.getCompany();

        this.add(linkTo(methodOn(TicketManagerController.class).getTicket(this.ticketid)).withSelfRel());
	}

	public Long getTicketid() {
        return ticketid;
    }

    public void setTicketid(Long ticketid) {
        this.ticketid = ticketid;
    }

    public String getCodenumber() {
        return codenumber;
    }

    public void setCodenumber(String codenumber) {
        this.codenumber = codenumber;
    }

    public String getCompany() {
        return company;
    }
	
    public void setCompany(String company) {
        this.company = company;
    }
}