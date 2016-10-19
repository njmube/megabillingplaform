(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Price_conceptDialogController', Price_conceptDialogController);

    Price_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Price_concept', 'Taxpayer_concept'];

    function Price_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Price_concept, Taxpayer_concept) {
        var vm = this;
        vm.price_concept = entity;
        vm.taxpayer_concepts = Taxpayer_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:price_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.price_concept.id !== null) {
                Price_concept.update(vm.price_concept, onSaveSuccess, onSaveError);
            } else {
                Price_concept.save(vm.price_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
