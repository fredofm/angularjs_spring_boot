(function() {
    'use strict';

    angular
        .module('ticketManager')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('login', {
            url: '/login',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();