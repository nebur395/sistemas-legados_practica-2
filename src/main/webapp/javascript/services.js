angular.module('musicPsApp')

// 'auth' service manage the authentication function of the page with the server
    .factory('auth', function ($state, $http) {

        var _authenticated = false;

        return {
            //return true if the user is authenticated
            isAuthenticated: function () {
                return _authenticated;
            },

            //authenticate the [identity] user
            authenticate: function (valor) {
                _authenticated = valor;
            },

            //logout function
            logout: function () {
                auth.authenticate(false);
                $state.go('starter');
            },

            //send the login info to the server
            login: function (user, password, callback) {
                var that = this;
                $http({
                    method: 'GET',
                    url: 'login',
                    headers: {
                        "user": user,
                        "pass": password
                    }
                }).success(function (data) {
                    that.authenticate(true);
                    $state.go('home');

                }).error(function (data) {
                    callback(data);
                });
            }
        };
    });
