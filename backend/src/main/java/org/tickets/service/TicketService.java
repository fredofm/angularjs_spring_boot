package org.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tickets.repository.TicketRepository;
import org.tickets.service.dto.TicketDTO;
import org.tickets.service.mapper.TicketMapper;

/**
 * Service class for managing users.
 */
@Service
public class TicketService {

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final TicketMapper ticketMapper;

    public TicketService (TicketRepository ticketRepository, TicketMapper ticketMapper)  {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

	public List<TicketDTO> findAll() {
		return ticketMapper.ticketsToTicketDTOs(ticketRepository.findAll());
	}

	public TicketDTO save(TicketDTO ticketDTO) {
        return ticketMapper.ticketToTicketDTO(ticketRepository.save(ticketMapper.ticketDTOToTicket(ticketDTO)));
	}

	public TicketDTO findOne(Long id) {
		return ticketMapper.ticketToTicketDTO(ticketRepository.findOne(id));
	}

	public void delete(Long id) {
        ticketRepository.delete(id);
	}
}
