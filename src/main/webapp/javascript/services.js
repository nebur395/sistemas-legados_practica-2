angular.module('musicPsApp')

// 'auth' service manage the authentication function of the page with the server
    .factory('auth', function ($state, $http) {

        var _authenticated = true;

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
                $http({
                    method: 'GET',
                    url: 'logout'
                }).success(function (data) {
                    auth.authenticate(false);
                    $state.go('starter');
                }).error(function () {
                });
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
    })

    // 'exerciseService' service manage the exercise home functions of the page with the server
    .factory('taskService', function ($state, $http) {

        return {
            //get the general list
            getGeneralList: function (callback) {
                var list = [
                    {date:"1001",description:"asa",assignee:""},
                    {date:"0202",description:"bsb",assignee:""},
                    {date:"3003",description:"csc",assignee:""}
                ];
                callback(list);

                /*$http({
                    method: 'GET',
                    url: 'viewTasks',
                    headers: {
                        "type": "general"
                    }
                }).success(function (data) {
                    callback(data);
                }).error(function () {
                });*/
            },

            //get the general list
            getSpecificList: function (callback) {
                var list = [
                    {date:"0101",description:"aaa",assignee:"a"},
                    {date:"2002",description:"bbb",assignee:"b"},
                    {date:"0303",description:"ccc",assignee:"c"}
                ];
                callback(list);

                /*$http({
                    method: 'GET',
                    url: 'viewTasks',
                    headers: {
                        "type": "specific"
                    }
                }).success(function (data) {
                    callback(data);
                }).error(function () {
                });*/
            },

            // add task
            addTask: function (object) {
                $http({
                    method: 'POST',
                    url: 'newTask',
                    data: JSON.stringify(object),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                 }).success(function () {
                 }).error(function () {
                 });
            }
        };
    });
