(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$scope','$translate', '$timeout','DataUtils', 'Auth', 'LoginService', 'Register'];

    function RegisterController ($scope,$translate, $timeout, DataUtils, Auth, LoginService, Register) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.errorRfcExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.suges = suges;
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

        function suges(){
            if(vm.registerAccount.login != null && vm.registerAccount.login != ""){
                vm.registerAccount.login = Register.query({login:vm.registerAccount.login});
            }
            else{
                vm.registerAccount.login = Register.query({login:"user"});
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
                            vm.registerAccount.login = vm.registerAccount.name.substring(0,1).toLowerCase()+ vm.registerAccount.firtsurname.toLowerCase()+ "0"+cont;
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

        vm.setPhoto = function ($file, registerAccount) {
            if ($file) {
                vm.photo_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {

                        if($file.size <= 10485760 && ($file.type == "image/png" || $file.type == "image/jpeg") ){
                            vm.registerAccount.path_photo = $file.name;
                            registerAccount.filephoto = base64Data;
                            registerAccount.filephotoContentType = $file.type;
                            vm.messphoto = null;
                        }
                        else{
                            vm.messphoto = true;
                        }
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
