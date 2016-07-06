(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_retentionsDialogController', Tax_retentionsDialogController);

    Tax_retentionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_retentions', 'Tax_types', 'Free_concept'];

    function Tax_retentionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tax_retentions, Tax_types, Free_concept) {
        var vm = this;
        vm.tax_retentions = entity;
        vm.tax_types = Tax_types.query();
        vm.free_concepts = Free_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_retentionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_retentions.id !== null) {
                Tax_retentions.update(vm.tax_retentions, onSaveSuccess, onSaveError);
            } else {
                Tax_retentions.save(vm.tax_retentions, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
