package org.tickets.service.mapper;

import org.tickets.domain.Ticket;
import org.tickets.service.dto.TicketDTO;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity Ticket and its DTO called TicketDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class TicketMapper {

    public TicketDTO ticketToTicketDTO(Ticket ticket) {
        TicketDTO ticketDTO = null;

        if (ticket != null) {
            ticketDTO = new TicketDTO(ticket);
        }
        
        return ticketDTO;
    }

    public List<TicketDTO> ticketsToTicketDTOs(List<Ticket> tickets) {
        return tickets.stream()
            .filter(Objects::nonNull)
            .map(this::ticketToTicketDTO)
            .collect(Collectors.toList());
    }

    public Ticket ticketDTOToTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        } else {
            Ticket ticket = new Ticket();
            ticket.setId(ticketDTO.getTicketid());
            ticket.setCodenumber(ticketDTO.getCodenumber());
            ticket.setCompany(ticketDTO.getCompany());
            
            return ticket;
        }
    }

    public List<Ticket> ticketDTOsToTickets(List<TicketDTO> ticketDTOs) {
        return ticketDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::ticketDTOToTicket)
            .collect(Collectors.toList());
    }

    public Ticket ticketFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
