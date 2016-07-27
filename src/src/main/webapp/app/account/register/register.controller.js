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
        vm.onChangeUser = onChangeUser;

        var cont = 1;

        $timeout(function (){angular.element('[ng-model="vm.registerAccount.login"]').focus();});

        function onChangeUser(){
            if(vm.registerAccount.name != null){
                vm.registerAccount.login = vm.registerAccount.name.substring(0,1).toLowerCase();
                if(vm.registerAccount.firtsurname != null)
                    vm.registerAccount.login += vm.registerAccount.firtsurname.toLowerCase();
            }

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
                vm.registerAccount.creator = 'System';
                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                        if(cont < 10)
                        {
                            vm.registerAccount.login = vm.registerAccount.name.substring(0,1)+ vm.registerAccount.firtsurname+ "0"+cont;
                            cont++;
                        }
                        else
                        {
                            vm.registerAccount.login += cont;
                            cont++;
                        }
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
