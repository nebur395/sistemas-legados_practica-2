angular.module('musicPsApp')

    .controller('homeCtrl', ['$scope', '$state', 'auth', function ($scope, $state, auth) {

        // inputs visual variables
        $scope.date = "";
        $scope.description = "";
        $scope.assignee = "";
        $scope.dateMaxLength = false;
        $scope.descriptionMaxLength = false;
        $scope.assigneeMaxLength = false;

        $scope.filterMode = function (mode) {
            switch (mode) {
                case 1:        //all filter
                    break;
                case 2:        //general filter
                    break;
                default:       //specific filter
                    ;
            }
        };

        // Watches to control input variables length
        $scope.$watch('date', function () {
            if ($scope.date.length > 4) {
                $scope.date = $scope.date.slice(0, 4);
            } else if ($scope.date.length == 4) {
                $scope.dateMaxLength = false;
            } else if ($scope.date.length >= 1 && $scope.date.length < 4) {
                $scope.dateMaxLength = true;
            } else {
                $scope.dateMaxLength = false;
            }
        });
        $scope.$watch('description', function () {
            if ($scope.description.length > 79) {
                $scope.description = $scope.description.slice(0, 79);
            } else if ($scope.description.length == 79) {
                $scope.descriptionMaxLength = true;
            } else {
                $scope.descriptionMaxLength = false;
            }
        });
        $scope.$watch('assignee', function () {
            if ($scope.assignee.length > 79) {
                $scope.assignee = $scope.assignee.slice(0, 79);
            } else if ($scope.assignee.length == 79) {
                $scope.assigneeMaxLength = true;
            } else {
                $scope.assigneeMaxLength = false;
            }
        });


        // send the register form to the auth service
        $scope.addTask = function () {
            // check if the both passwords match
            if ($scope.password !== $scope.rePassword) {
                showError('Invalid passwords');
            } else {
                var userObject = {
                    user: $scope.userName,
                    email: $scope.email,
                    pass: $scope.password,
                    repass: $scope.rePassword

                };
                auth.register(userObject, showError);
            }
        }
    }]);
