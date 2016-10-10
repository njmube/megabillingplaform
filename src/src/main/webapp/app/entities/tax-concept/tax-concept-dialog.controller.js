(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDialogController', Tax_conceptDialogController);

    Tax_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Tax_concept', 'Concept', 'Tax_types'];

    function Tax_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, taxpayer_account_entity, Tax_concept, Concept, Tax_types) {
        var vm = this;
        vm.tax_concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;
        vm.concepts = Concept.query({taxpayeraccount: vm.taxpayer_account.id, no_identification: " ", description: " ", measure_unit: " ", unit_value: -1});
        vm.tax_types = Tax_types.query({filtername: " "});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:tax_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tax_concept.id !== null) {
                Tax_concept.update(vm.tax_concept, onSaveSuccess, onSaveError);
            } else {
                Tax_concept.save(vm.tax_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
