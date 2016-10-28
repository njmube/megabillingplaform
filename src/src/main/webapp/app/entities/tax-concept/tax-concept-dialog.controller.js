(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDialogController', Tax_conceptDialogController);

    Tax_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Tax_concept', 'Tax_types', 'Taxpayer_concept'];

    function Tax_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, taxpayer_account_entity, Tax_concept, Tax_types, Taxpayer_concept) {
        var vm = this;
        vm.tax_concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;

        vm.taxpayer_concepts = Taxpayer_concept.query({taxpayeraccount: vm.taxpayer_account.id, no_identification: " ", description: " ", measure_unit: " ", unit_value: -1});
        vm.tax_types = [];
        Tax_types.query({filtername: " "}, function(data){
            var tax_types = data;
            var i;
            for(i = 0; i < tax_types.length; i++){
                if(tax_types[i].id != 2){
                    vm.tax_types.push(tax_types[i]);
                }
            }
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.onTaxTypesChange = onTaxTypesChange;

        function onTaxTypesChange() {
            if (vm.tax_concept.tax_types != null && vm.tax_concept.tax_types != undefined && vm.tax_concept.tax_types.id == 1) {
                vm.tax_concept.rate = (16).toFixed(2);
            }
            else {
                vm.tax_concept.rate = (1).toFixed(2);
            }
        }

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
