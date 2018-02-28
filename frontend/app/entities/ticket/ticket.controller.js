(function() {
    'use strict';

    angular
        .module('ticketManager')
        .controller('TicketController', TicketController);

    TicketController.$inject = ['Ticket'];

    function TicketController(Ticket) {

        var vm = this;

        vm.tickets = [];

        loadAll();

        function loadAll() {
            Ticket.query(function(result) {
                vm.tickets = result;
                vm.searchQuery = null;
            });
        }
    }
})();