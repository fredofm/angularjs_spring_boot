(function() {
    'use strict';

    angular
        .module('ticketManager', [
            'ui.router'
            ,'ngResource'
            ,'ngSessionStorage'
            ,'angular-jwt'
            //,'ui.bootstrap'
        ])
        .constant('BACKEND_URL_BASE','http://localhost:8085/');
})();
