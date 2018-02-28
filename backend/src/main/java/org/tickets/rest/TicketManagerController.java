package org.tickets.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tickets.rest.errors.BadRequestAlertException;
import org.tickets.rest.util.HeaderUtil;
import org.tickets.service.TicketService;
import org.tickets.service.dto.TicketDTO;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TicketManagerController {

    private static final String ENTITY_NAME = "ticket";

    @Autowired
    private final TicketService ticketService;

    public TicketManagerController (TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getTickets() {
        List<TicketDTO> ticketList = ticketService.findAll();

        return ResponseEntity.ok(ticketList);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/tickets")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        if (ticketDTO.getTicketid() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TicketDTO result =  ticketService.save(ticketDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tickets : Updates an existing ticket.
     *
     * @param ticketDTO the ticket to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticket,
     * or with status 400 (Bad Request) if the ticket is not valid,
     * or with status 500 (Internal Server Error) if the ticket couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tickets")
    public ResponseEntity<TicketDTO> updateTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        if (ticketDTO.getId() == null) {
            return createTicket(ticketDTO);
        }

        TicketDTO result = ticketService.save(ticketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickets/:id : get the "id" ticket.
     *
     * @param id the id of the ticket to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticket, or with status 404 (Not Found)
     */
    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        TicketDTO ticketDTO = ticketService.findOne(id);

        return Optional
            .ofNullable( ticketDTO )
            .map(ticket -> ResponseEntity.ok().body(ticket) )          //200 OK
            .orElseGet( () -> ResponseEntity.notFound()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "No se ha encontrado registro con identificador " + id, "No se ha encontrado registro con identificador " + id))
                .build() );  //404 Not found
    }

    /**
     * DELETE  /tickets/:id : delete the "id" ticket.
     *
     * @param id the id of the ticket to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        Optional<TicketDTO> ticketDTO = Optional.ofNullable(ticketService.findOne(id));
        ResponseEntity<Void> response = ResponseEntity.ok()
                                    .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                                    .build();         //200 OK;        

        if (ticketDTO.isPresent()) {
            ticketService.delete(id);
        } else {
            response = ResponseEntity.notFound()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "No se ha encontrado registro con identificador " + id, "No se ha encontrado registro con identificador " + id))
            .build();  //404 Not found
        }
           
        return response;
    }
}