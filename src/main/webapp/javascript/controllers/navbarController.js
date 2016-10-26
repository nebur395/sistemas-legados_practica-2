angular.module('musicPsApp')

    .controller('navbarCtrl', ['$scope', '$state', 'auth', function ($scope, $state, auth) {

        $scope.logged = function () {
            return auth.isAuthenticated();
        };

        $scope.logout = function () {
            auth.logout();
        }
    }]);
