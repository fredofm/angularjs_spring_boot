(function() {
    'use strict';

    angular
        .module('ticketManager')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', '$scope', 'Auth', 'Principal'];

    function NavbarController ($state, $scope, Auth, Principal) {
        var vm = this;

        vm.logout = logout;
        vm.user = Principal.username;

        vm.isAuthenticated = Principal.isAuthenticated;

        //vm.$state = $state;

        function logout() {
            Auth.logout();
            
            $scope.username = "";

            $state.go('home');
        }
    }
})();
