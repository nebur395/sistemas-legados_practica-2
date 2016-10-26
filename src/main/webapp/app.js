angular.module('musicPsApp', ['ui.router'])

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider

        //starter screen
            .state('starter', {
                url: "/starter",
                templateUrl: "templates/starter.html",
                controller: "starterCtrl",
                onEnter: function ($state, auth) {
                    if (auth.isAuthenticated()) {
                        $state.go('home');
                    }
                }
            })
            //home screen
            .state('home', {
                url: "/home",
                templateUrl: "templates/home.html",
                controller: "homeCtrl",
                onEnter: function ($state, auth) {
                    if (!auth.isAuthenticated()) {
                        $state.go('starter');
                    }
                }
            });

        $urlRouterProvider.otherwise('starter');
    });
