(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDialogController', ConceptDialogController);

    ConceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Concept', 'Measure_unit', 'Cfdi'];

    function ConceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Concept, Measure_unit, Cfdi) {
        var vm = this;
        vm.concept = entity;
        vm.measure_units = Measure_unit.query();
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.concept.id !== null) {
                Concept.update(vm.concept, onSaveSuccess, onSaveError);
            } else {
                Concept.save(vm.concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
