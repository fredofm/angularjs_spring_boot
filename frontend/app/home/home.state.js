(function() {
    'use strict';

    angular
        .module('ticketManager')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider ) {
        
        $stateProvider.state('home', { 
            parent: 'app',
            url: '/home',
            views: {
                'content@': {
                    templateUrl: '/home/home.html'
                }
            }
        });
    }
})();