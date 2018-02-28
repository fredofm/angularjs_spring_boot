(function() {
    'use strict';

    angular
        .module('ticketManager')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider ) {

        $urlRouterProvider.otherwise('home');

        $stateProvider.state('app', { 
            abstract: true,

            views: {
                'navbar@': {
                    templateUrl: '/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
