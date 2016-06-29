(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('PasswordController', PasswordController);

    PasswordController.$inject = ['Auth', 'Principal'];

    function PasswordController (Auth, Principal) {
        var vm = this;

        vm.changePassword = changePassword;
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorpassword = null;
        vm.success = null;

        Principal.identity().then(function(account) {
            vm.account = account;
        });

        function changePassword () {

            if (vm.password !== vm.confirmPassword) {
                vm.error = null;
                vm.success = null;
                vm.errorpassword = null;
                vm.doNotMatch = 'ERROR';
            } else {
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorpassword = null;
                var password1 = vm.oldpassword + "   " + vm.password;
                Auth.changePassword(password1).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'Incorrect old password') {
                        vm.errorpassword = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
