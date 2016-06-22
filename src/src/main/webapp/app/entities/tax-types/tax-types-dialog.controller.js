(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_typesDialogController', Tax_typesDialogController);

    Tax_typesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_types'];

    function Tax_typesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tax_types) {
        var vm = this;

        vm.tax_types = entity;
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
            if (vm.tax_types.id !== null) {
                Tax_types.update(vm.tax_types, onSaveSuccess, onSaveError);
            } else {
                Tax_types.save(vm.tax_types, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:tax_typesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
