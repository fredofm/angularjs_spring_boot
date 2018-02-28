(function() {
    'use strict';

    angular
        .module('ticketManager')
        .factory('Auth', Auth);

    Auth.$inject = ['$rootScope', '$state', 'Principal', 'AuthServerProvider'];

    function Auth ($rootScope, $state, Principal, AuthServerProvider) {
        var service = {
            authorize: authorize,
            login: login,
            logout: logout,
            loginWithToken: loginWithToken
        };

        return service;

        function authorize (force) {
            var authReturn = Principal.identity(force).then(authThen);

            return authReturn;

            function authThen () {
                var isAuthenticated = Principal.isAuthenticated();

                if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                    $state.go('accessdenied');
                }
            }
        }

        function login (credentials, callback) {

            AuthServerProvider.login(credentials)
                .then(loginThen)
                .catch(function (err) {
                    this.logout();
                    return callback(err);
                }.bind(this));

                function loginThen(data) {
                    Principal.authenticate(data);
                    return callback(true);
                }
        }

        function loginWithToken(jwt, rememberMe) {
            return AuthServerProvider.loginWithToken(jwt, rememberMe);
        }

        function logout () {
            AuthServerProvider.logout();
            Principal.authenticate(null);
        }
    }
})();
