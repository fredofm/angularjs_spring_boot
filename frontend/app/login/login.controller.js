(function () {
    'use strict';
 
    angular
        .module('ticketManager')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', '$scope', 'Auth', '$sessionStorage'];
 
    function LoginController($state, $scope, Auth, $sessionStorage) {
        var vm = this;
 
        vm.login = login;
 
        initController();
 
        function initController() {
            // reset login status
            Auth.logout();
        };
 
        function login(event) {
            event.preventDefault();

            vm.loading = true;
            Auth.login({username: vm.username, password: vm.password}, function (result) {
                if (result === true) {
                    $state.go('home');
                } else {
                    vm.error = 'Username or password is incorrect';
                    vm.loading = false;
                }
            });
        };
    }
})();