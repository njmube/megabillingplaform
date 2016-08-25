(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_complementDialogController', C_complementDialogController);

    C_complementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_complement'];

    function C_complementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, C_complement) {
        var vm = this;

        vm.c_complement = entity;
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
            if (vm.c_complement.id !== null) {
                C_complement.update(vm.c_complement, onSaveSuccess, onSaveError);
            } else {
                C_complement.save(vm.c_complement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:c_complementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
