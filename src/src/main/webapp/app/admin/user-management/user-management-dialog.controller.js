(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$scope', '$stateParams', 'DataUtils','$uibModalInstance', 'entity', 'User', 'JhiLanguageService'];

    function UserManagementDialogController ($scope, $stateParams, DataUtils, $uibModalInstance, entity, User, JhiLanguageService) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_ADMIN_CF', 'ROLE_AFILITATED', 'ROLE_OPERADOR', 'ROLE_USER_CF'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.doNotMatch = null;
        vm.onChangeUser = onChangeUser;
        var cont = 1;


        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError (response) {
            vm.isSaving = false;
            vm.messages = response.data;
            if (response.status === 400 && response.data === 'login already in use') {
                vm.errorUserExists = 'ERROR';
                if(cont < 10)
                {
                    vm.user.login = vm.user.name.substring(0,1)+ vm.user.firtsurname+ "0"+cont;
                    cont++;
                }
                else
                {
                    vm.user.login += cont;
                    cont++;
                }
            }
        }

        function onChangeUser(){
            if(vm.user.name != null){
                vm.user.login = vm.user.name.substring(0,1).toLowerCase();
                if(vm.user.firtsurname != null)
                    vm.user.login += vm.user.firtsurname.toLowerCase();
            }
        }

        function save () {
                vm.isSaving = true;
                if (vm.user.id !== null) {
                    User.update(vm.user, onSaveSuccess, onSaveError);
                } else {

                    User.save(vm.user, onSaveSuccess, onSaveError);
                }
        }

        vm.setPhoto = function ($file, user) {
            if ($file) {
                vm.photo_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {

                        if($file.size <= 10485760 && ($file.type == "image/png" || $file.type == "image/jpeg") ){
                            vm.user.path_photo = $file.name;
                            user.filephoto = base64Data;
                            user.filephotoContentType = $file.type;
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
