(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', '$uibModalInstance'];

    function LoginController ($rootScope, $state, $timeout, Auth, $uibModalInstance) {
        var vm = this;

        vm.authenticationError = false;
        vm.messFailLogin = null;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;
        vm.countFail = 0;

        $timeout(function (){angular.element('[ng-model="vm.username"]').focus();});

        function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
            $uibModalInstance.dismiss('cancel');
        }

        function login (event) {
            var delay = 0;
            vm.messFailLogin = null;
            if($rootScope.countfaillogin != undefined){
                if($rootScope.countfaillogin >= 3){
                    var now = new Date();
                    var minutes = now.valueOf()-$rootScope.datelastfail.valueOf();
                    minutes = minutes / 60000;
                    if(minutes < 30){
                        delay = 1;
                    }
                    else{
                        $rootScope.countfaillogin = 0;
                    }
                }
            }

            if(delay == 0) {
                event.preventDefault();
                Auth.login({
                    username: vm.username,
                    password: vm.password,
                    rememberMe: vm.rememberMe
                }).then(function () {
                    vm.authenticationError = false;
                    $rootScope.countfaillogin = 0;
                    $uibModalInstance.close();

                    $rootScope.$broadcast('authenticationSuccess');
                    $state.go('home');

                    // If we're redirected to login, our
                    // previousState is already set in the authExpiredInterceptor. When login succesful go to stored state
                    /*if ($rootScope.redirected && $rootScope.previousStateName) {
                     $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                     $rootScope.redirected = false;
                     } else {
                     $rootScope.$broadcast('authenticationSuccess');
                     }*/
                }).catch(function () {
                    vm.authenticationError = true;
                    if($rootScope.countfaillogin != undefined){
                        $rootScope.countfaillogin++;
                        $rootScope.datelastfail = new Date();
                    }else{
                        $rootScope.countfaillogin = 1;
                        $rootScope.datelastfail = new Date();
                    }

                });
            }else{
                vm.messFailLogin = 'OK';
                vm.authenticationError = false;
            }
        }

        function register () {
            $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword () {
            $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
