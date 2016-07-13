(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('LegendDialogController', LegendDialogController);

    LegendDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Legend', 'Freecom_taxlegends'];

    function LegendDialogController ($scope, $stateParams, $uibModalInstance, entity, Legend, Freecom_taxlegends) {
        var vm = this;
        vm.legend = entity;
        vm.freecom_taxlegendss = Freecom_taxlegends.query();
        vm.load = function(id) {
            Legend.get({id : id}, function(result) {
                vm.legend = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:legendUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.legend.id !== null) {
                Legend.update(vm.legend, onSaveSuccess, onSaveError);
            } else {
                Legend.save(vm.legend, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
