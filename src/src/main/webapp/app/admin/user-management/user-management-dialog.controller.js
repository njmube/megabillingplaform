(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, JhiLanguageService) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_ADMIN_CF', 'ROLE_AFILITATED', 'ROLE_OPERADOR', 'ROLE_USER_CF'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.doNotMatch = null;
        vm.onChangeUser = onChangeUser;


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

        function onSaveError () {
            vm.isSaving = false;
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
    }
})();
