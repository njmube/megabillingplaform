(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Discount_conceptDialogController', Discount_conceptDialogController);

    Discount_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Discount_concept', 'Taxpayer_concept'];

    function Discount_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Discount_concept, Taxpayer_concept) {
        var vm = this;
        vm.discount_concept = entity;
        vm.taxpayer_concepts = Taxpayer_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:discount_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.discount_concept.id !== null) {
                Discount_concept.update(vm.discount_concept, onSaveSuccess, onSaveError);
            } else {
                Discount_concept.save(vm.discount_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
