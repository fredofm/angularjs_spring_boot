(function() {
    'use strict';

    angular
        .module('ticketManager')
        .factory('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$sessionStorage'];

    function authInterceptor ($sessionStorage) {
        var service = {
            request: request
        };

        return service;

        function request (config) {
           //if (!config || !config.url || /^http/.test(config.url)) return config;
           if (!config || !config.url) return config;

            /*jshint camelcase: false */
            config.headers = config.headers || {};
            var token = $sessionStorage.authenticationToken;
            if (token) {
                config.headers.Authorization = 'Bearer ' + token;
            }
            return config;
        }
    }
})();
