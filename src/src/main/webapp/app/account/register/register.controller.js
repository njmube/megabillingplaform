(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$translate', '$timeout', 'Auth', 'LoginService'];

    function RegisterController ($translate, $timeout, Auth, LoginService) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.errorRfcExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;
        vm.usersuges = null;
        vm.suges = suges;

        $timeout(function (){angular.element('[ng-model="vm.registerAccount.login"]').focus();});

        function suges(){
            vm.usersuges = "OK";
            var name_f_s = "";
            if(vm.registerAccount.name == null)
                name_f_s += "vacio";
            else
                name_f_s += vm.registerAccount.name;
            if(vm.registerAccount.firtsurname == null)
                name_f_s += "_vacio";
            else
                name_f_s += "_" + vm.registerAccount.firtsurname;
            if(vm.registerAccount.secondsurname == null)
                name_f_s += "_vacio";
            else
                name_f_s += "_" + vm.registerAccount.secondsurname;
            /*Auth.sugesuser(name_f_s).then(function (response) {
                vm.usersuges = "OK";
            }).catch(function (response) {
                vm.error = 'ERROR';
            });*/

        }

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;
                vm.errorRfcExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'rfc already in use') {
                        vm.errorRfcExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
