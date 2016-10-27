angular.module('musicPsApp')

    .controller('homeCtrl', ['$scope', '$state', 'taskService', function ($scope, $state, taskService) {

        // inputs visual variables
        $scope.date = "";
        $scope.description = "";
        $scope.assignee = "";
        $scope.dateMaxLength = false;
        $scope.descriptionMaxLength = false;
        $scope.assigneeMaxLength = false;

        $scope.taskGeneralList = [];    // list of general tasks
        $scope.taskSpecificList = [];   //list of specific tasks

        $scope.viewAll = true;
        $scope.viewGeneral = false;
        $scope.viewSpecific = false;

        // server request for the list of general tasks
        $scope.getGeneralList = function () {
            taskService.getGeneralList(function (tasks) {
                $scope.taskGeneralList = tasks;
            });
        };
        $scope.getGeneralList();
        sortCustom($scope.taskGeneralList);
        // server request for the list of specific tasks
        $scope.getSpecificList = function () {
            taskService.getSpecificList(function (tasks) {
                $scope.taskSpecificList = tasks;
            });
        };
        $scope.getSpecificList();
        sortCustom($scope.taskSpecificList);

        $scope.taskAllList = $scope.taskGeneralList.concat($scope.taskSpecificList);
        sortCustom($scope.taskAllList);

        $scope.filterMode = function (mode) {
            switch (mode) {
                case 1:        //all filter
                    $scope.viewAll = true;
                    $scope.viewGeneral = false;
                    $scope.viewSpecific = false;
                    break;
                case 2:        //general filter
                    $scope.viewAll = false;
                    $scope.viewGeneral = true;
                    $scope.viewSpecific = false;
                    break;
                default:       //specific filter
                    $scope.viewAll = false;
                    $scope.viewGeneral = false;
                    $scope.viewSpecific = true;
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

        // sort the [list] array alphabetically by date
        function sortCustom (list) {
            list.sort(function(a, b){
                var x = a.date.toLowerCase();
                var y = b.date.toLowerCase();
                if (x < y) {return -1;}
                if (x > y) {return 1;}
                return 0;
            });
        }

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
