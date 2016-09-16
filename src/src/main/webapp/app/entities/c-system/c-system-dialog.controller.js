(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_systemDialogController', C_systemDialogController);

    C_systemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_system'];

    function C_systemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_system) {
        var vm = this;

        vm.c_system = entity;
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
            if (vm.c_system.id !== null) {
                C_system.update(vm.c_system, onSaveSuccess, onSaveError);
            } else {
                C_system.save(vm.c_system, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_systemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
