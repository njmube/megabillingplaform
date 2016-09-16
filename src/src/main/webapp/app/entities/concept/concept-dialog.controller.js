(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDialogController', ConceptDialogController);

    ConceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Concept', 'Taxpayer_account', 'Measure_unit'];

    function ConceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Concept, Taxpayer_account, Measure_unit) {
        var vm = this;

        vm.concept = entity;
        vm.clear = clear;
        vm.save = save;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.measure_units = Measure_unit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.concept.id !== null) {
                Concept.update(vm.concept, onSaveSuccess, onSaveError);
            } else {
                Concept.save(vm.concept, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
