(function() {
    'use strict';

    angular
        .module('ticketManager')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('tickets', {
            parent: 'app',
            url: '/tickets',
            views: {
                'content@': {
                    templateUrl: '/entities/ticket/tickets.html',
                    controller: 'TicketController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
