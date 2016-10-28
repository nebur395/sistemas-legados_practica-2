angular.module('musicPsApp')

    .controller('starterCtrl', ['$scope', '$state', 'auth', function ($scope, $state, auth) {

        // inputs visual variables
        $scope.userName = "";
        $scope.password = "";
        $scope.userNameMaxLength = false;
        $scope.passwordMaxLength = false;
        $scope.logging = false;

        // feedback handling variables
        $scope.errorMsg = "";
        $scope.error = false;

        // hide the error login message when is true respectively
        $scope.hideError = function () {
            $scope.errorMsg = "";
            $scope.error = false;
        };

        // show the error login message when is false respectively
        var showError = function (error) {
            $scope.errorMsg = error;
            $scope.error = true;
        };

        // Watches to control input variables length
        $scope.$watch('userName', function () {
            if ($scope.userName.length > 79) {
                $scope.userName = $scope.userName.slice(0, 79);
            } else if ($scope.userName.length == 79) {
                $scope.userNameMaxLength = true;
            } else {
                $scope.userNameMaxLength = false;
            }
        });
        $scope.$watch('password', function () {
            if ($scope.password.length > 79) {
                $scope.password = $scope.password.slice(0, 79);
            } else if ($scope.password.length == 79) {
                $scope.passwordMaxLength = true;
            } else {
                $scope.passwordMaxLength = false;
            }
        });

        // send the login form to the auth service
        $scope.signIn = function () {
            var user = $scope.userName.replace(/ /g,"_");
            var password = $scope.password.replace(/ /g,"_");
            activeProgress(true);
            auth.login(user, password, activeProgress, showError);
        };

        var activeProgress = function (active) {
            $scope.logging = active;
        }
    }]);
