(function() {
    'use strict';

    angular
        .module('ticketManager')
        .factory('AuthServerProvider', AuthServerProvider);

    AuthServerProvider.$inject = ['$http', '$sessionStorage', '$q', 'BACKEND_URL_BASE', 'jwtHelper'];

    function AuthServerProvider ($http, $sessionStorage, $q, BACKEND_URL_BASE, jwtHelper) {
        var service = {
            getToken: getToken,
            login: login,
            loginWithToken: loginWithToken,
            storeAuthenticationToken: storeAuthenticationToken,
            logout: logout
        };

        return service;

        function getToken () {

            return $sessionStorage.authenticationToken;
        }

        function login (credentials) {

            var data = {
                username: credentials.username,
                password: credentials.password
            };
            
            return $http.post(BACKEND_URL_BASE + 'login', data)
                        .then(authenticateSuccess);

            /*return $http({
                method : "GET",
                url : BACKEND_URL_BASE + 'login',
                data: data
            }).then(authenticateSuccess(response)), function myError(response) {
                $scope.myWelcome = response.statusText;
            };*/

            function authenticateSuccess (response) {
                var bearerToken = response.headers('Authorization');
                if (angular.isDefined(bearerToken) && bearerToken.slice(0, 7) === 'Bearer ') {
                    var jwt = bearerToken.slice(7, bearerToken.length);
                    service.storeAuthenticationToken(jwt);
                    return jwtHelper.decodeToken(bearerToken);
                }
            }
        }

        function loginWithToken(jwt) {
            var deferred = $q.defer();

            if (angular.isDefined(jwt)) {
                this.storeAuthenticationToken(jwt);
                deferred.resolve(jwt);
            } else {
                deferred.reject();
            }

            return deferred.promise;
        }

        function storeAuthenticationToken(jwt) {
            $sessionStorage.authenticationToken = jwt;
        }

        function logout () {
            delete $sessionStorage.authenticationToken;
        }
    }
})();