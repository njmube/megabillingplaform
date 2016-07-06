(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_transferedDialogController', Tax_transferedDialogController);

    Tax_transferedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_transfered', 'Tax_types', 'Free_concept'];

    function Tax_transferedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tax_transfered, Tax_types, Free_concept) {
        var vm = this;
        vm.tax_transfered = entity;
        vm.tax_types = Tax_types.query();
        vm.free_concepts = Free_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_transferedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_transfered.id !== null) {
                Tax_transfered.update(vm.tax_transfered, onSaveSuccess, onSaveError);
            } else {
                Tax_transfered.save(vm.tax_transfered, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
