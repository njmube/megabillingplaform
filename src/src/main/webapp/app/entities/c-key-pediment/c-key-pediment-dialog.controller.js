(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_key_pedimentDialogController', C_key_pedimentDialogController);

    C_key_pedimentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_key_pediment'];

    function C_key_pedimentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_key_pediment) {
        var vm = this;

        vm.c_key_pediment = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.c_key_pediment.id !== null) {
                C_key_pediment.update(vm.c_key_pediment, onSaveSuccess, onSaveError);
            } else {
                C_key_pediment.save(vm.c_key_pediment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_key_pedimentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
