(function() {
    'use strict';

    angular
        .module('ticketManager')
        .factory('Principal', Principal);

    Principal.$inject = [];

    function Principal () {
        var _identity = null,
            _authenticated = false,
            _username = "";

        var service = {
            authenticate: authenticate,
            hasAnyAuthority: hasAnyAuthority,
            hasAuthority: hasAuthority,
            isAuthenticated: isAuthenticated,
            isIdentityResolved: isIdentityResolved,
            username: username
        };

        return service;

        function username() {
            return _username;
        }

        function authenticate (identity) {
            _identity = identity;
            _authenticated = identity !== null;

            if (_authenticated) {
                _username = _identity.sub;
            }
        }

        function hasAnyAuthority (authorities) {
            if (!_authenticated || !_identity || !_identity.authorities) {
                return false;
            }

            for (var i = 0; i < authorities.length; i++) {
                if (hasAuthority(authorities[i])) {
                    return true;
                }
            }

            return false;
        }

        function hasAuthority (authority) {
            if (!_authenticated) {
                return false;
            }

            return _id.authorities && _id.authorities.indexOf(authority) !== -1;
        }

        function isAuthenticated () {
            return _authenticated;
        }

        function isIdentityResolved () {
            return angular.isDefined(_identity);
        }
    }
})();
