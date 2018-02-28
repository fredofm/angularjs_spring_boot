(function() {
    'use strict';
    angular
        .module('ticketManager')
        .factory('Ticket', Ticket);

    Ticket.$inject = ['$resource', 'BACKEND_URL_BASE'];

    function Ticket ($resource, BACKEND_URL_BASE) {
        var resourceUrl =  BACKEND_URL_BASE + 'api/tickets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
